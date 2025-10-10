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
        EMAIL_RECIPIENT = 'pranav.laddhad2@gmail.com'
    }

    stages {
        stage('Checkout SCM') { // stage-1
            steps {
                echo 'Checking out source code from SCM...'
                checkout scm
            }
        }
        stage('Test') { // stage-2
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build JAR') { // stage-3
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') { // stage-4
            steps {
                sh """${DOCKER_CMD} build -t ${IMAGE_NAME} ."""
            }
        }

        stage('DockerHub Push') { // stage-5
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

        stage('Deploy via Ansible') // stage-6
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

    post { //  stage-7 (post-actions)
        success {
            echo 'Pipeline succeeded!'
            emailext (
                subject: "SUCCESS: Jenkins Pipeline - sciCalculator",
                body: """
                    <h2>Jenkins Pipeline Success</h2>
                    <p>The pipeline for <b>sciCalculator</b> completed successfully.</p>
                    <ul>
                      <li><b>GitHub Repo:</b> <a href="https://github.com/pranav-laddhad/sciCalculator">sciCalculator</a></li>
                      <li><b>Docker Image:</b> <a href="https://hub.docker.com/repository/docker/pranavladdhad/sci-calculator">${IMAGE_NAME}</a></li>
                    </ul>
                    <p>Build, test, and deployment all passed successfully.</p>
                """,
                to: "${EMAIL_RECIPIENT}",
                mimeType: 'text/html'
            )
        }

        failure {
            echo 'Pipeline failed!'
            emailext (
                subject: "FAILURE: Jenkins Pipeline - sciCalculator",
                body: """
                    <h2>Jenkins Pipeline Failed</h2>
                    <p>The Jenkins pipeline for <b>sciCalculator</b> has failed.</p>
                    <p>Please check the Jenkins console logs for details: 
                    <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: "${EMAIL_RECIPIENT}",
                mimeType: 'text/html'
            )
        }
    }

}
