pipeline {
    agent any
    
    // Define the tools Jenkins should use globally in this pipeline
    tools {
        // NOTE: Names MUST match the 'Name' you defined in Global Tool Configuration
        // (e.g., 'Maven_3.9.4' and 'JDK_17')
        maven 'Maven_3.9.4' 
        jdk 'JDK_17'      
    }

    environment {
        DOCKERHUB_CREDENTIALS = 'dockerhub-cred'
        IMAGE_NAME = 'pranavladdhad/sci-calculator:0.1'
    }

    stages {
        // Stage 'Checkout' is still implicit via SCM configuration.
        
        stage('Test') {
            steps {
                // mvn is now available because it was defined in the 'tools' block
                sh 'mvn clean test' 
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '/Applications/Docker.app/Contents/Resources/bin/docker build -t $IMAGE_NAME .'
            }
        }

        stage('DockerHub Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                    sh 'docker push $IMAGE_NAME'
                }
            }
        }

        stage('Deploy via Ansible') {
            steps {
                sh 'ansible-playbook deploy.yml'
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
