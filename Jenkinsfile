pipeline {
    agent any

    triggers {
        pollSCM('* * * * *') // Vérifie les changements toutes les minutes
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub') // Credentials pour Docker Hub
        IMAGE_NAME = "slimzrk/gestionevents" // Nom de l'image Docker
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'git@github.com:slimzrrk/gestionevents.git',
                    credentialsId: 'github' // Remplace 'github' par l'ID correct
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Construire l'image Docker pour l'application complète (backend + frontend)
                    dockerImage = docker.build("${IMAGE_NAME}")
                }
            }
        }

        stage('Scan Docker Image') {
            steps {
                script {
                    // Scan de l'image Docker avec Trivy pour détecter les vulnérabilités
                    sh 'trivy clean'
                    sh """
                    docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \\
                    -e TRIVY_DB_REPO=ghcr.io/aquasecurity/trivy-db \\
                    aquasec/trivy:latest image --scanners vuln --timeout 30m --exit-code 0 --severity LOW,MEDIUM,HIGH,CRITICAL \\
                    ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Push de l'image Docker vers Docker Hub
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                        dockerImage.push()
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo 'Cleanup phase!'
                // Supprimer les images Docker pour libérer de l'espace
                if (sh(script: "docker images -q aquasec/trivy", returnStdout: true).trim()) {
                    sh 'docker rmi aquasec/trivy'
                }
                if (sh(script: "docker images -q ${IMAGE_NAME}", returnStdout: true).trim()) {
                    sh "docker rmi ${IMAGE_NAME}"
                }
                echo 'Cleanup Successfully done!'
            }
        }
    }
}