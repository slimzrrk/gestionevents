pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'slimzrrk/gestionevents' // Your Docker image name
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub') // Docker Hub credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out the code from GitHub..."
                git branch: 'main', 
                    url: 'git@github.com:slimzrrk/gestionevents.git' // Your repository URL
            }
        }

        stage('Build JAR') {
            steps {
                echo "Building Spring Boot application..."
                sh './mvnw clean package -DskipTests' // Build the project, skipping tests
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

  

        stage('Push Docker Image to Docker Hub') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                        sh "docker push ${DOCKER_IMAGE}"
                    }
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
