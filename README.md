# jenkins-2026

# Jenkins Installation
```bash
sudo apt update
sudo apt install fontconfig openjdk-21-jre
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

## jenkins plugin 
SSH Build Agents plugin -- Allows to launch agents over SSH, using a Java implementation of the SSH protocol.
Git plugin - This plugin integrates Git with Jenkins.
Build Authorization Token Root -->Lets build and related REST build triggers be accessed even when anonymous users cannot see Jenkins.

http://10.224.82.79:8080/buildByToken/build?job=node01&token=mytoken