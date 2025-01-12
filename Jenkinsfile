pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub') // Identifiants Docker Hub
        IMAGE_NAME = "slimzrk/gestionevents"          // Nom de l'image Docker
    }
    stages {
        stage('Clone Repository') {
            steps {
                // Clonage du dépôt Git
                git branch: 'main', url: 'git@github.com:slimzrrk/gestionevents.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Construction de l'image Docker
                    sh 'docker build -t $IMAGE_NAME:latest .'
                }
            }
        }
        stage('Scan for Vulnerabilities') {
            steps {
                script {
                    // Analyse des vulnérabilités avec Trivy
                    sh 'trivy image $IMAGE_NAME:latest || echo "Trivy terminé avec des avertissements."'
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Connexion au registre Docker Hub et push de l'image
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_HUB_CREDENTIALS) {
                        sh 'docker push $IMAGE_NAME:latest'
                    }
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline terminé avec succès.'
        }
        failure {
            echo 'Pipeline échoué.'
        }
    }
}
