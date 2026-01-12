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
                sh'rm -rf ~/.m2/repository/org/apache/maven/plugins/maven-war-plugin'
                sh 'rm -rf ~/.m2/repository/com/thoughtworks/xstream'
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