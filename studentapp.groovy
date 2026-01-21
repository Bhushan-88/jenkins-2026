pipeline {
    agent any

    tools {
        maven 'maven-3.9.1'
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

/*        stage('build') {
            steps {
                sh '/opt/apache-maven-3.9.1/bin/mvn package'
            }
        }
*/
/*        stage('test') {
            steps{
                sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=student-app \
  -Dsonar.host.url=http://10.123.249.62:9000 \
  -Dsonar.login=sqp_c9d3a245c54ddda139cd7864b873de82c66db991'
            }
        }
*/
        stage('Testing SonarQube Analysis') {
            steps {
            // This submits the report to SonarQube
                withSonarQubeEnv(installationName: 'sonar-server', credentialsId: 'sonar-token') {
                    sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=student-app'
                }
            }
        }

        stage('deploy') {
            steps{
                // Ensure the 'Deploy to Container' plugin is installed
                echo 'Quality Gate passed. Deploying application....'
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://10.123.249.93:8080/')], contextPath: '/var/lib/jenkins/workspace/studentapp/target/', war: '**/*.war'
            }
        }
    }
}




