# jenkins
#What is Jenkins ?

Jenkins is an open source continuous integration/continuous delivery and deployment (CI/CD) automation software DevOps tool written in the Java programming language. It is used to implement CI/CD workflows, called pipelines.it is used for automating the various stages of software development such as build, test, and deployment.

# Jenkins Installation
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version

sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update
sudo apt install jenkins

root@ubuntu:~# systemctl status jenkins.service
root@ubuntu:~# cat /var/lib/jenkins/secrets/initialAdminPassword
```
# Install Maven tool for Build
```bash
#1. Verify Java 11 Installation (Required for Maven 3.9.1)
sudo apt install openjdk-11-jdk
java -version
# 2. Ensure Java 11 Is the Default JVM
sudo alternatives --config java
# 3. Install Apache Maven 3.9.1 (Manual – Recommended)
cd /opt
sudo wget https://downloads.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.tar.gz
Extract
sudo tar -xzf apache-maven-3.9.1-bin.tar.gz
sudo mv apache-maven-3.9.1 maven

# 4. Configure Maven Environment Variables
sudo vi /etc/profile 
Add:
export PATH=$PATH:'/opt/apache-maven-3.9.1/bin'
Apply:
source /etc/profile
mvn -version

# configure maven Manage Jenkins → Tools → Maven installations
Scroll to Maven installations
Click Add Maven
Fill exactly:
Name: maven-3.9.1 ← must match Jenkinsfile
MAVEN_HOME: /opt/apache-maven-3.9.1
Install automatically: ❌ unchecked
Save
```

## jenkins plugin 
SSH Build Agents plugin -- Allows to launch agents over SSH, using a Java implementation of the SSH protocol.

Git plugin - This plugin integrates Git with Jenkins.

Email Extension Plugin
--------------------------------------------------------------
## Triggers
Trigger builds remotely (e.g., from scripts)

Build Authorization Token Root -->Lets build and related REST build triggers be accessed even when anonymous users cannot see Jenkins.

after pulgin installation
http://10.224.82.79:8080/buildByToken/build?job=node01&token=mytoken

pipeline plugin
blue ocean
-----------------------------------------------------------------------------

## Pipeline
A Jenkins Pipeline is a Pipeline-as-Code implementation that defines the entire CI/CD workflow in code (Groovy DSL) instead of UI clicks.

## What Is Declarative Pipeline ?

Declarative Pipeline is a structured, opinionated syntax introduced to:

Reduce complexity
Enforce best practices
Make pipelines readable and predictable

## What Is Scripted Pipeline ?

Scripted Pipeline is pure Groovy scripting.Full programming control with no safety rails

Characteristics : 

Flexible
Powerful
Complex
Harder to maintain
Used in legacy pipelines or very complex logic

-------------------------------------------------------------------------------------
## Q: Difference between Declarative and Scripted pipeline?

Answer:
Declarative pipeline is a structured, opinionated syntax designed for readability, validation, and best practices, whereas Scripted pipeline is a flexible Groovy-based approach offering full control but higher complexity. In modern CI/CD, Declarative is preferred, with Scripted used only for advanced logic.

--------------------------------------------------------------------------------------

# Create Sonarube server

# SonarQube integation ,Jenkins Side Requirement (MANDATORY) plugin
sonarQube scanner 
Sonar Quality gates

Goto Sonarube project -->create manualy project -->genrate token -->copy token
```bash
#paste /opt/studentapp-ui for manual testing then add pipeline
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=student-app \
  -Dsonar.host.url=http://10.123.249.62:9000 \
  -Dsonar.login=sqp_c9d3a245c54ddda139cd7864b873de82c66db991
```
## if you want hide secrets from pipeline like sonar-server hostname secret token follow this steps:
## store sonarube cred in jenkins using sonarube token first 
manage jenkins ->Configure global settings and paths ->Add SonarQube servers

Use Pipeline Syntax generator -->withSonarQubeEnv: Prepare SonarQube Scanner environment -->select Server authentication token(secret sonar token) -->generate
output : 
withSonarQubeEnv(credentialsId: 'sonar-token') {
    // some block
}
EX:--withSonarQubeEnv(installationName: 'sonar-server', credentialsId: 'sonar-token') {
                    sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=student-app'
                }

## 1. What Is a SonarQube Webhook?

A SonarQube webhook is a mechanism that allows SonarQube to notify an external system (e.g., Jenkins) when a code analysis is completed.

In practice:
SonarQube finishes analysis
Quality Gate is evaluated
SonarQube sends an HTTP POST request to a configured URL
Jenkins (or another system) receives the result and acts on it

## 2. Why Webhooks Are Important (Real CI/CD Use)

Without webhook:
Jenkins must poll SonarQube repeatedly
Pipeline waits blindly
With webhook:
Jenkins is instantly notified
Pipeline can fail or pass automatically based on Quality Gate
This is the recommended production approach.

Step 1: Login to SonarQube
Administration → Configuration → Webhooks
Step 2: Navigate to Webhooks
name:-jenkins-quality-gate
url:-http://<jenkins-ip>:8080/sonarqube-webhook/

## if you want to run this pipeline on the bases of quality check if quality gate fail stop pipeline
Use Pipeline Syntax generator -->Select waitForQualityGate: Wait for SonarQube analysis to be completed and return quality gate status -->generate

## To deploy your artifact (WAR file) to a Tomcat server, you need to do three things:

Configure Tomcat to accept remote deployments.
Install the "Deploy to Container" plugin in Jenkins.
Update your Pipeline with the deploy stage.

```bash
# Step 1: Configure Tomcat User (On Tomcat Server)
cd /opt
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.113/bin/apache-tomcat-9.0.113.tar.gz
tar -xvf  apache-tomcat-9.0.113.tar.gz apache-tomcat-9.0.113/
mv apache-tomcat-9.0.113 tomcat
sudo vim /opt/tomcat/conf/tomcat-users.xml
# Add the following lines inside the <tomcat-users> tags:
 <role rolename="manager-gui"/>
 <role rolename="manager-script"/>
 <role rolename="manager-jmx"/>
 <role rolename="manager-status"/>
 <role rolename="admin-gui"/>
 <user username="deployer" password="deployer@123" roles="manager-gui,manager-script,manager-jmx,manager-status,admin-gui"/>

#find and comment this line 
vim /opt/tomcat/webapps/manager/META-INF/context.xml
<!--
  <Valve className="org.apache.catalina.valves.RemoteCIDRValve"
        allow="127.0.0.0/8,::1/128" />
-->

# start tomcat service 
cd /opt/tomcat/bin
./catlina.sh start
http://10.123.249.93:8080
login with credential for check

# Step 2: Install Jenkins Plugin
Go to Manage Jenkins > Plugins > Available Plugins.
Search for "Deploy to Container".
Install it and restart Jenkins if required.

# Step 3: Add Credentials in Jenkins
Go to Manage Jenkins > Credentials > System > Global credentials.
Click Add Credentials.
Kind: Username with password.
Username: deployer (from Step 1).
Password: deployer@123.
ID: tomcat-cred (We will use this ID in the pipeline).
Click Create.


# Step 4: Add Credentials in Jenkins
Manage Jenkins ->Credentials ->System-> Global credentials ->add cred
Use Pipeline Syntax generator -->find-->deploy: Deploy war/ear to a container -->add war= **/*.war -->add Context path= / --> add Containers ->select tomcat 9 ->select tomcat credentials and add tomcat URL http://10.123.249.93:8080/ -->Generate Pipeline Script

Output:- add in pipeline script (studentapp.groovy)

deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://10.123.249.93:8080/')], contextPath: '/', war: '**/*.war'

Update your Pipeline with the deploy stage.

```
------------------------------------------------------------------------------------------
## Install seed jenkins plugin

A Seed Job is a Jenkins job that automatically creates other Jenkins jobs using code (Groovy script). This is the "Infrastructure as Code" way to manage Jenkins, so you don't have to manually click "New Item" for every microservice.

```bash

To do this, we use the Seed jenkins (optional Job DSL Plugin.)

Step 1: Install the Plugin
Go to Manage Jenkins > Plugins > Available Plugins.
Search for "Seed jenkins".
Install it and restart Jenkins if necessary.

#Create the DSL Script
Step 2: Configure the Seed Job in Jenkins
Dashboard > New Item.
Name: My-Seed-Job.
Select Freestyle project (Seed jobs are usually freestyle) ->Build steps ->process job DSLs->use the provided DSL script and paste:
job('my-first-job') {
    steps {
        shell('echo HELLO WORLD!')
    }
}
## Install Authorize project plugin 

# If you don't configure the Authorize Project plugin, Jenkins doesn't know who is running the script, so it blocks it by default until an Admin approves it.
How to fix the "Script Approval" issue (Using the Plugin)

```bash
Go to Manage Jenkins > Security > Global Security.
Scroll down to Access Control for Builds.
Click Add and select Run as User who Triggered Build (or "Project default Build Authorization").
Click Save.

Now run seed-job afte that we can see it will created new job (my-first-job)

# instead of provided DSLs job we can pass DSL job file from git repo
create dir in repo 
seed-job/seed_job.groovy

steps :
add git url and credentials in job ->got build steps ->select Look on Filesystem and enter path <seed-job/seed_job.groovy>

```
## How to take backup of Jenkins Job with the help of Thinbackup plugin  
The "ThinBackup" Plugin (For Jenkins Settings)
If you want to backup everything (not just the pipeline logic, but also your Plugins, Global Tool Configurations, User accounts, and Build History), use the ThinBackup plugin.
Using ThinBackup Plugin (Recommended for Regular Backups)

1.Install: Go to Manage Jenkins > Plugins > Install "ThinBackup".
2.Configure: Go to Manage Jenkins > ThinBackup > Settings.
3.Set Path: Choose a folder (e.g., /backup/jenkins).
4.Schedule: Set it to run every night (e.g., 0 0 * * *).
What it does: It creates a lightweight backup of all your XML configuration files. You can restore this easily on a new server.

```bash
# Create backup directory with proper permissions
sudo mkdir -p /backup/jenkins-thinbackup
sudo chown -R jenkins:jenkins /backup/jenkins-thinbackup

# Configure via Jenkins UI:
Manage Jenkins → System Configuration → ThinBackup
Backup directory path: /backup/jenkins-thinbackup
Backup schedule: 0 2 * * * (daily at 2 AM)
Max # backups: 7
Backup build results: ✓ (if you want to backup build artifacts)

Manual backup with ThinBackup:
# Trigger backup via CLI
java -jar /tmp/jenkins-cli.jar -s http://localhost:8080/ \
  -auth admin:$(sudo cat /var/lib/jenkins/secrets/initialAdminPassword) \
  thinBackup backup
```


# Using the Jenkins CLI (jenkins-cli.jar) is a great way to "script" your backups without installing extra plugins. It allows you to export the configuration of your jobs as XML files from the command line.
```bash 
#Step 1: Download the CLI Tool
wget http://10.123.249.78:8080/jnlpJars/jenkins-cli.jar #(Make sure you have Java installed to run this .jar file).

#Step 2: Get Your API Token
To use the CLI safely, do not use your password. Generate an API Token:

Log in to Jenkins.
Click Your Name (top right) > Configure.
Find API Token > Click Add new Token.
Name it (e.g., cli-backup) and copy the token string (e.g., 11a2b3c...).
11c1b9d1dbd9e288e01edab4d7ab7338bf # YOUR_API_TOKEN

#Step 3: Backup a Single Job
java -jar jenkins-cli.jar -s http://10.123.249.78:8080/ -auth admin:YOUR_API_TOKEN get-job student-app > student-app.xml
# delete old job 
# To restore job
java -jar jenkins-cli.jar -s http://10.123.249.78:8080/ -auth admin:YOUR_API_TOKEN get-job student-app > student-app.xml

java -jar jenkins-cli.jar -s http://10.123.249.78:8080/ -auth admin:YOUR_API_TOKEN reload-configuration
```

# how u can assign ssl certificate to jenkins ?

