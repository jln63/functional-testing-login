/* Requires the Docker Pipeline plugin */
pipeline {
    agent none  // Avoid using a global agent at the pipeline level
    stages {
        stage('Build') {
            agent { docker 'maven:3.9.3-eclipse-temurin-17' }
            steps {
                sh 'mvn --version'
            }
        }
}
