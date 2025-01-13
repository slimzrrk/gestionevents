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
                    try {
                        git branch: 'main', 
                            url: 'git@github.com:slimzrrk/gestionevents.git' // Your repository URL
                    } catch (Exception e) {
                        error "Failed to checkout code: ${e.message}"
                    }
                }
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot application..."
                script {
                    try {
                        sh './mvnw clean package -DskipTests' // Build the project, skipping tests
                    } catch (Exception e) {
                        error "Failed to build the JAR: ${e.message}"
                    }
                }
            }
        }

        stage('Docker Login') {
            steps {
                echo "Logging into Docker Hub..."
                script {
                    try {
                        sh 'echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin'
                    } catch (Exception e) {
                        error "Docker login failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                script {
                    try {
                        sh "docker build -t ${DOCKER_IMAGE} ."
                    } catch (Exception e) {
                        error "Failed to build Docker image: ${e.message}"
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                script {
                    try {
                        sh "docker push ${DOCKER_IMAGE}"
                    } catch (Exception e) {
                        error "Failed to push Docker image: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up Docker images..."
            script {
                try {
                    sh "docker rmi ${DOCKER_IMAGE} || true"
                } catch (Exception e) {
                    echo "Failed to clean up Docker images: ${e.message}"
                }
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
