# jenkins
#What is Jenkins ?

Jenkins is an open source continuous integration/continuous delivery and deployment (CI/CD) automation software DevOps tool written in the Java programming language. It is used to implement CI/CD workflows, called pipelines.it is used for automating the various stages of software development such as build, test, and deployment.

# Jenkins Installation
```bash
sudo apt update
sudo apt install openjdk-11-jdk
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

after pulgin installation
http://10.224.82.79:8080/buildByToken/build?job=node01&token=mytoken

pipeline plugin
blue ocean

sonarQube scanner 
Sonar Quality gates
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

-------------------------------------------------------------------------
# Q: Difference between Declarative and Scripted pipeline?

Answer:
Declarative pipeline is a structured, opinionated syntax designed for readability, validation, and best practices, whereas Scripted pipeline is a flexible Groovy-based approach offering full control but higher complexity. In modern CI/CD, Declarative is preferred, with Scripted used only for advanced logic.
