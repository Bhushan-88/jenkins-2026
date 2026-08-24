pipeline {
    agent any
     stages {
        stage('pull') {
            steps {
                git 'https://github.com/your-repo.git'
            }
        }
    }
}  

pipeline {
    agent any
    stages{
        stage("code"){
            steps{
                echo "code clone done"
            }
        }
        stage("build"){
            steps{
                echo "docker build stage success"
            }
            
        }
        stage("test"){
            steps{
                echo "test tester will give"
            }
            
        }
        stage("deploy"){
            stpes{
                echo "deploy by docker compose"
            }
            
        }
    }
}