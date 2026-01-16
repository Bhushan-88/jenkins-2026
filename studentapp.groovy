pipeline {
    agent any

    tools {
        maven 'Maven-3.9.1'
    }
    stages {
        stage('pull') {
            steps {
                git 'https://github.com/Bhushan-88/studentapp-ui.git'
            }
        }
        stage('Build') {
            steps {
        sh 'mvn clean package'
            }
        }

        stage('build') {
            steps {
                sh '/opt/apache-maven-3.9.1/bin/mvn package'
            }
        }
        stage('test') {
            steps{
                echo 'test is success'
            }
        }
        stage('deploy') {
            steps{
                echo 'deploy is successful'
            }
        }
    }
}