pipeline{
    agent any
    stages{
        stage('Build image'){
            steps{
                withCredentials([string(credentialsId: 'POSTGRES_IP', variable: 'POSTGRES_IP')]) {
                 sh 'docker build --no-cache --build-arg POSTGRES_IP_ARG=$POSTGRES_IP --build-arg POSTGRES_PORT_ARG=5432 --build-arg POSTGRES_USER_ARG=postgres --build-arg POSTGRES_PASSWORD_ARG=group3secret -t juangh15/gildedrose-api /var/lib/jenkins/workspace/backend/'
            }
                 
            }
        }
        stage('Push image'){
            steps{
                withCredentials([string(credentialsId: 'dockerHubPassword', variable: 'dockerHubPassword')]) {
                sh 'docker login -u juangh15 -p $dockerHubPassword'
                    sh 'docker push juangh15/gildedrose-api'
            }

          
            }
            
        }
    }
    
 }
 
 
