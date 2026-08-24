pipeline {
    agent { label "dev"};
    stages{
        stage("code"){
            steps{
                git url: "https://github.com/Bhushan-88/two-tier-flask-app.git", branch: "master"
                echo "code clone done"
            }
        }
        stage("build"){
            steps{
                sh "docker build -t two-tire-flask-app ."
                echo "docker build stage success"
            }
            
        }
        stage("test"){
            steps{
                echo "test tester will give"
            }
            
        }
        stage("Push to Docker Hub"){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: "dockerHubCreds")]) {}
                    
                    }
                }  
            }
        }
        stage("deploy"){
            steps{
                sh "docker compose up -d --build flask-app"
                echo "deploy by docker compose"
            }
            
        }
    }
}