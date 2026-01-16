pipeline {
    agent any
    stages {
        stage('pull') {
            steps {
                git 'https://github.com/Bhushan-88/studentapp-ui.git'
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