pipeline {
    agent any

    tools {
        maven 'maven-3.9.1'
    }

    stages {
        stage('Pull') {
            steps {
                git 'https://github.com/Bhushan-88/studentapp-ui.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Test is successful'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy is successful'
            }
        }
    }
}
