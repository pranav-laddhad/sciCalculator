// pipeline {
//     // agent any
//     agent any
    
//     // Define the tools Jenkins should use globally in this pipeline
//     tools {
//         // NOTE: Names MUST match the 'Name' you defined in Global Tool Configuration
//         // (e.g., 'Maven_3.9.4' and 'JDK_17')
//         maven 'Maven_3.9.4' 
//         jdk 'JDK_17'      
//     }


//     environment {
//         DOCKERHUB_CREDENTIALS = 'dockerhub-cred'
//         IMAGE_NAME = 'pranavladdhad/sci-calculator:0.1'
//         ANSIBLE_PLAYBOOK_CMD = '/Users/prana/Desktop/SEM7/SPE/sciCalculator/ansible_venv/bin/ansible-playbook'
//         DOCKER_CMD = '/Applications/Docker.app/Contents/Resources/bin/docker'
//     }

//     stages {
//         // Stage 'Checkout' is still implicit via SCM configuration.
        
//         stage('Test') {
//             steps {
//                 // mvn is now available because it was defined in the 'tools' block
//                 sh 'mvn clean test' 
//             }
//         }

//         stage('Build JAR') {
//             steps {
//                 sh 'mvn clean package'
//             }
//         }

//         stage('Build Docker Image') {
//             steps {
//                 sh "${DOCKER_CMD} build -t $IMAGE_NAME ."
//             }
//         }

//         stage('DockerHub Push') {
//             steps {
//                 withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
//                     sh "echo $DOCKER_PASSWORD | ${DOCKER_CMD} login -u $DOCKER_USERNAME --password-stdin"
//                     sh "${DOCKER_CMD} push $IMAGE_NAME"
//                 }
//             }
//         }

//         stage('Deploy via Ansible') {
//             // NEW: Run this stage inside a Docker container with Ansible installed.
//             agent {
//                 docker {
//                     // Use a standard Docker image that includes Ansible
//                     image 'cytopia/ansible:latest'
//                     // Mount the Docker socket so Ansible can execute the Docker module commands
//                     args '-v /var/run/docker.sock:/var/run/docker.sock'
//                 }
//             }
//             steps {
//                 // Ansible is now available in the container's path, and deploy.yml is in the workspace.
//                 sh 'ansible-playbook deploy.yml'
//             }
//         }
//     }

//     post {
//         success {
//             echo 'Pipeline succeeded!'
//         }
//         failure {
//             echo 'Pipeline failed!'
//         }
//     }
// }




pipeline {
    agent any 
    tools {
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
        // Stage 'Checkout' is redundant; removed explicit checkout step
        
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
                sh "${DOCKER_CMD} build -t $IMAGE_NAME ."
            }
        }

        // stage('DockerHub Push') {
        //     steps {
        //         withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
        //             sh "echo $DOCKER_PASSWORD | ${DOCKER_CMD} login -u $DOCKER_USERNAME --password-stdin"
        //             sh "${DOCKER_CMD} push $IMAGE_NAME"
        //         }
        //     }
        // }

        stage('DockerHub Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh '''
                        mkdir -p /tmp/docker-config
                        echo '{ "credsStore": "" }' > /tmp/docker-config/config.json
                        echo "$DOCKER_PASSWORD" | ${DOCKER_CMD} --config /tmp/docker-config login -u "$DOCKER_USERNAME" --password-stdin
                        ${DOCKER_CMD} --config /tmp/docker-config push $IMAGE_NAME
                    '''
                }
            }
        }

        stage('Deploy via Ansible') {
            steps {
                sh '''
                    source /Users/prana/Desktop/SEM7/SPE/sciCalculator/ansible_venv/bin/activate
                    ansible-playbook deploy.yml
                '''
            }
        }

    }

    post { // stage-7
        success {
            echo 'Pipeline succeeded!'
            // emailext (
            //     subject: "SUCCESS: Jenkins Pipeline - sciCalculator",
            //     body: """
            //     <h2>Jenkins Pipeline Success </h2>
            //     <p>The pipeline for <b>sciCalculator</b> completed successfully.</p>
            //     <ul>
            //       <li><b>GitHub Repo:</b> <a href="https://github.com/pranav-laddhad/sciCalculator">sciCalculator</a></li>
            //       <li><b>Docker Image:</b> <a href="https://hub.docker.com/repository/docker/pranavladdhad/sci-calculator">pranavladdhad/sci-calculator:0.1</a></li>
            //     </ul>
            //     <p>Build, test, and deployment all passed successfully.</p>
            //     """,
            //     recipientProviders: [[$class: 'DevelopersRecipientProvider']],
            //     to: "${EMAIL_RECIPIENT}",
            //     mimeType: 'text/html'
            // )
        }

        failure {
            // echo 'Pipeline failed!'
            // emailext (
            //     subject: "FAILURE: Jenkins Pipeline - sciCalculator",
            //     body: """
            //     <h2>Pipeline Failed</h2>
            //     <p>The Jenkins pipeline for <b>sciCalculator</b> has failed.</p>
            //     <p>Please check the Jenkins console logs for details: 
            //     <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
            //     """,
            //     to: "${EMAIL_RECIPIENT}",
            //     mimeType: 'text/html'
            // )
        }
    }

}
