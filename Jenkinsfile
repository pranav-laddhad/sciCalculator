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
        ANSIBLE_PLAYBOOK_CMD = '/Users/prana/Desktop/SEM7/SPE/sciCalculator/ansible_venv/bin/ansible-playbook'
        DOCKER_CMD = '/Applications/Docker.app/Contents/Resources/bin/docker'
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
                sh "${DOCKER_CMD} build -t $IMAGE_NAME ."
            }
        }

        stage('DockerHub Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh "echo $DOCKER_PASSWORD | ${DOCKER_CMD} login -u $DOCKER_USERNAME --password-stdin"
                    sh "${DOCKER_CMD} push $IMAGE_NAME"
                }
            }
        }

        stage('Deploy via Ansible') {
            steps {
                // The absolute path to the virtual environment's activation script
                sh '''
                    # NOTE: Replace 'prana' with your actual macOS username
                    VENV_PATH="/Users/prana/Desktop/SEM7/SPE/sciCalculator/ansible_venv"
                    
                    # Source the activation script to set the PATH and run Ansible
                    source "${VENV_PATH}/bin/activate"
                    
                    # Run the standard command which is now available in the PATH
                    ansible-playbook deploy.yml
                    
                    # Deactivate (good practice)
                    deactivate
                '''
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
