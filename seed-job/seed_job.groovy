job('my-first-job') {
    steps {
        shell('echo HELLO WORLD!')
    }
}
job ('mysecond-job') {
    steps {
        shell('echo THIS IS MY SECOND JOB!')
    }
}