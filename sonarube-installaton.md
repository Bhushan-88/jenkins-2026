# PART 1: SonarQube Configuration (Hands-On)

You will:
Install SonarQube server
Configure SonarQube in Jenkins
Run code quality analysis from Jenkins pipeline
See results in SonarQube dashboard

## 2. SonarQube Architecture (Simple)
Developer → GitHub → Jenkins → SonarQube
                              ↓
                        Quality Gate


Jenkins sends source code to SonarQube
SonarQube analyzes:

Bugs
Code smells
Vulnerabilities
Duplications
Quality Gate decides PASS / FAIL

## 3. Install SonarQube Server (Community Edition)
create new instance/server
Prerequisites
Java 17 (SonarQube requires Java 17)
Minimum 2 GB RAM (4 GB recommended)
SonarQube server Java version is independent of your project Java 11

```bash
# STEP 1: CHECK JAVA VERSION (CRITICAL)
Install Java 17
apt install -y java-17-openjdk
java -version
sudo wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.9.3.79811.zip
cd /opt
unzip ~/sonarqube-9.9.3.79811.zip
mv sonarqube-9.9.3.79811 sonarube

# STEP 2: DO NOT RUN SONARQUBE AS ROOT (VERY IMPORTANT)
sudo useradd sonar
sudo passwd sonar
add sudo sonar
sudo chown -R sonar:sonar /opt/sonarqube
su - sonar

# STEP 3: Step 1: System Preparation (Kernel Tuning)
SonarQube uses Elasticsearch, which requires specific kernel limits. If you skip this, SonarQube will fail to start.
#1. Open the sysctl configuration file:
sudo nano /etc/sysctl.conf
#2.Add these lines to the bottom of the file:
vm.max_map_count=524288
fs.file-max=131072
#3.apply changes
sudo sysctl -p
#4.Update resource limits:
sudo nano /etc/security/limits.conf
#5.Add these lines before the # End of file:
sonarqube   -   nofile   65536
sonarqube   -   nproc    4096

# STEP 4: Configure SonarQube: Edit the properties file:
sudo nano /opt/sonarqube/conf/sonar.properties  #Find and uncomment/update these lines:
# Database Configuration
sonar.jdbc.username=sonar
sonar.jdbc.password=Redhat@123
sonar.jdbc.url=jdbc:postgresql://localhost/sonarqube

# Java Options (Optional but recommended for performance)
sonar.web.javaOpts=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError
sonar.ce.javaOpts=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError


#Step 5: Create Systemd Service
This ensures SonarQube starts automatically on boot. Create the service file:

sudo nano /etc/systemd/system/sonarqube.service
Paste the following content:
[Unit]
Description=SonarQube service
After=syslog.target network.target postgresql.service

[Service]
Type=forking
ExecStart=/opt/sonarqube/bin/linux-x86-64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86-64/sonar.sh stop
User=sonar
Group=sonar
Restart=always
LimitNOFILE=65536
LimitNPROC=4096

[Install]
WantedBy=multi-user.target

Start the service:

sudo systemctl daemon-reload
sudo systemctl enable sonarqube
sudo systemctl start sonarqube
sudo systemctl status sonarqube
tail -f /opt/sonarqube/logs/sonar.log

# STEP 5: START SONARQUBE
cd /opt/sonarqube-9.9.3.79811/bin/linux-x86-64
./sonar.sh start
./sonar.sh status

#standard SonarQube troubleshooting
tail -f /opt/sonar/logs/sonar.log
tail -50 /opt/sonar/logs/es.log

# STEP 5: VERIFY PORT 9000
ss -lntp | grep 9000

#STEP 6: ACCESS UI
http://<server-ip>:9000
Login:
admin / admin


# Step 7: Install PostgreSQL 15 (Compatible Version)

#Since Ubuntu 24.04 defaults to Postgres 16 (unsupported by SQ 9.9), we will add the official PostgreSQL repository to install version 15.
Add the PostgreSQL repository:

sudo apt install -y curl ca-certificates
sudo install -d /usr/share/postgresql-common/pgdg
sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://www.postgresql.org/media/keys/ACCC4CF8.asc
echo "deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt noble-pgdg main" | sudo tee /etc/apt/sources.list.d/pgdg.list

#Install Prerequisites Make sure you have the tools to download the key:
sudo apt update
sudo apt install -y curl gnupg ca-certificates lsb-release

#Add the PostgreSQL Signing Key This ensures Ubuntu trusts the software from PostgreSQL.
sudo install -d /usr/share/postgresql-common/pgdg
sudo curl -o /usr/share/postgresql-common/pgdg/apt.postgresql.org.asc --fail https://www.postgresql.org/media/keys/ACCC4CF8.asc

#Add the Repository Configuration This explicitly tells Ubuntu to look at the PostgreSQL repo for your version ("noble").
echo "deb [signed-by=/usr/share/postgresql-common/pgdg/apt.postgresql.org.asc] https://apt.postgresql.org/pub/repos/apt noble-pgdg main" | sudo tee /etc/apt/sources.list.d/pgdg.list

#Update Package Lists (Critical Step) Now you must refresh apt so it sees the new packages.
sudo apt update

#Install PostgreSQL 15 Now the install command will work:
sudo apt install -y postgresql-15

#Create the Database & User:
sudo -u postgres psql

CREATE USER sonar WITH PASSWORD 'Redhat@123';
CREATE DATABASE sonarqube OWNER sonar;
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO sonar;
\q


```
