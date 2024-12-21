pipeline {
    agent any

    tools {
        maven 'maven_3.8.1'
    }

    stages {
        stage('Compile') {
            steps {
                sh 'mvn clean compile -B -ntp'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -DskipTests -B -ntp'
            }
        }
    }
    post {
        always {
            echo 'Post always'
        }
    }
}
