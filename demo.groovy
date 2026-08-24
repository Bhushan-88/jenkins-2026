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
                sh "docker build -t two-tier-flask-app ."
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
                    withCredentials([usernamePassword(
                        credentialsId: "dockerHubCreds"
                        , passwordVariable: "dockerHubPass"
                        , usernameVariable: "dockerHubUser"
                        )]) {
                        sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                        sh "docker tag two-tire-flask-app ${env.dockerHubUser}/two-tire-flask-app:latest"
                        sh "docker push ${env.dockerHubUser}/two-tire-flask-app:latest"
                    
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