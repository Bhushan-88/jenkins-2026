node {
    stage('pull') {
        echo 'pull is successful'
    }
    stage('build') {
        echo 'congrates ! build is done'
    }
    stage('test') {
        echo 'test is success'
    }
    stage('deploy') {
        echo 'deploy is successful'
    }
}