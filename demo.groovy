@Library("Shared") _
pipeline {
    
    agent { label "dev"};

    stages{
        stage("code clone"){
            steps{
                script{
                    clone("https://github.com/Bhushan-88/two-tier-flask-app.git","master")
                }
            }
        }
        stage("Trivy File System Scan"){
            steps{
                script{
                    trivy_fs()
                }
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
                    docker_push("dockerHubCreds","two-tier-flask-app")
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
    post{
        success{
            script{
                emailext from: 'bhushandurgawli1@gmail.com',
                to: 'bhushandurgawli1@gmail.com',
                body: 'Build success for Demo CICD App',
                subject: 'Build success for Demo CICD App'
            }
        }
        failure{
            script{
                emailext from: 'bhushandurgawli1@gmail.com',
                to: 'bhushandurgawli1@gmail.com',
                body: 'Build Failed for Demo CICD App',
                subject: 'Build Failed for Demo CICD App'
            }
        }
    }
}


# Demo.groovy with shared library

@Library("Shared") _
pipeline{
    
    agent { label "dev"};
    
    stages{
        stage("Code Clone"){
            steps{
               script{
                   clone("https://github.com/Bhushan-88/two-tier-flask-app.git", "master")
               }
            }
        }
        stage("Trivy File System Scan"){
            steps{
                script{
                    trivy_fs()
                }
            }
        }
        stage("Build"){
            steps{
                sh "docker build -t two-tier-flask-app ."
            }
            
        }
        stage("Test"){
            steps{
                echo "Developer / Tester tests likh ke dega..."
            }
            
        }
        stage("Push to Docker Hub"){
            steps{
                script{
                    docker_push("dockerHubCreds","two-tier-flask-app")
                }  
            }
        }
        stage("Deploy"){
            steps{
                sh "docker compose up -d --build flask-app"
            }
        }
    }

post{
        success{
            script{
                emailext from: 'bhushandurgawli1@gmail.com',
                to: 'bhushandurgawli1@gmail.com',
                body: 'Build success for Demo CICD App',
                subject: 'Build success for Demo CICD App'
            }
        }
        failure{
            script{
                emailext from: 'bhushandurgawli1@gmail.com',
                to: 'bhushandurgawli1@gmail.com',
                body: 'Build Failed for Demo CICD App',
                subject: 'Build Failed for Demo CICD App'
            }
        }
    }
}



# Shared Library code 
# 
def call(String url, String branch) {
    git url: "${url}", branch: "${branch}"
}
def call() {
    sh "trivy fs . -o results.json"
}
def call(String credId, String imageName) {
  withCredentials([usernamePassword(
                        credentialsId: "${credId}"
                        , passwordVariable: "dockerHubPass"
                        , usernameVariable: "dockerHubUser"
                        )]) {
                        sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
                        sh "docker image tag ${imageName} ${env.dockerHubUser}/${imageName}"
                        sh "docker push ${env.dockerHubUser}/${imageName}:latest"
                    }
}






## anathore project used shared library

    @Library("Shared") _
    pipeline{
        agent { label 'dev-server' }
        
        stages{
            stage("Code Clone"){
                steps{
                    script{
                    clone("https://github.com/Bhushan-88/node-todo-cicd.git", "master")
                    }
                }
            }
        }
    }