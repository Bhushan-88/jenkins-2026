pipeline {
    agent any
    stages {
        stage('pull') {
            steps {
                git 'https://github.com/shubhamkalsait/studentapp-ui.git'
            }
        }
        stage('build') {
            steps {
                echo 'congrates ! build is done'
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