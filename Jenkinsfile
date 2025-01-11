pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials-id' // Remplacez par l'ID de vos credentials
        DOCKER_IMAGE = 'username/gestionevents' // Remplacez 'username' par votre Docker Hub username
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh './mvnw clean package' // Compile et package l'application
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:latest .'
            }
        }
        stage('Security Scan') {
            steps {
                sh 'trivy image $DOCKER_IMAGE:latest'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKER_IMAGE:latest
                    '''
                }
            }
        }
    }
}
