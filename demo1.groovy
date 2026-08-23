pipeline {
    agent any
    stages {
        stage (code) {
        steps {
            echo 'Hello World'
        }
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
    }
}
