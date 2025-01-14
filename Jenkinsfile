pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'slimzrk/gestionevents' // Your Docker image name
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub') // Docker Hub credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out the code from GitHub..."
                script {
                    git branch: 'main',
                        url: 'git@github.com:slimzrrk/gestionevents.git' // Your repository URL
                }
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot application..."
                script {
                    sh './mvnw clean package -DskipTests' // Build the project, skipping tests
                }
            }
        }

        stage('Docker Login') {
            steps {
                echo "Logging into Docker Hub..."
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Scan Docker Image with Trivy') {
            steps {
                echo "Scanning Docker image for vulnerabilities..."
                script {
                    def scanResult = sh(script: "trivy image ${DOCKER_IMAGE}", returnStatus: true)
                    if (scanResult != 0) {
                        echo "Trivy scan detected vulnerabilities. Please review the vulnerabilities."
                        sh "trivy image --format table ${DOCKER_IMAGE}" // Print vulnerabilities
                        error "Pipeline failed due to security vulnerabilities."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                script {
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up Docker images..."
            script {
                sh "docker rmi ${DOCKER_IMAGE} || true"
            }
        }
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed. Check the logs for more details."
        }
    }
}