pipeline {
    agent any 

    tools {
        maven 'Maven_3.9.4'
        jdk 'JDK_17'
    }

    environment {
        DOCKERHUB_CREDENTIALS = 'dockerhub-cred'
        IMAGE_NAME = 'pranavladdhad/sci-calculator:0.1'
        ANSIBLE_VENV = '/Users/prana/Desktop/SEM7/SPE/sciCalculator/ansible_venv/bin/activate'
        DOCKER_CMD = '/Applications/Docker.app/Contents/Resources/bin/docker'
    }

    stages {

        stage('Test') {
            steps {
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
                sh """${DOCKER_CMD} build -t ${IMAGE_NAME} ."""
            }
        }

        stage('DockerHub Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, 
                                                 passwordVariable: 'DOCKER_PASSWORD', 
                                                 usernameVariable: 'DOCKER_USERNAME')]) {
                    sh """
                        mkdir -p /tmp/docker-config
                        echo '{ "credsStore": "" }' > /tmp/docker-config/config.json
                        echo "$DOCKER_PASSWORD" | ${DOCKER_CMD} --config /tmp/docker-config login -u "$DOCKER_USERNAME" --password-stdin
                        ${DOCKER_CMD} --config /tmp/docker-config push ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Deploy via Ansible') {
            steps {
                sh """
                    /bin/bash -c '
                    source ${ANSIBLE_VENV}
                    ansible-playbook deploy.yml
                    '
                """
            }
        }

    }

    post {
        success {
            echo 'Pipeline succeeded!'
            // You can uncomment and configure email notifications here
        }

        failure {
            echo 'Pipeline failed!'
            // You can uncomment and configure email notifications here
        }
    }
}
