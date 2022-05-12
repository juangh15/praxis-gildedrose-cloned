pipeline{
    agent any
    stages{
        stage('Build image'){
            steps{
                 sh 'docker build --no-cache --build-arg POSTGRES_IP_ARG=35.173.31.212 --build-arg POSTGRES_PORT_ARG=5432 --build-arg POSTGRES_USER_ARG=postgres --build-arg POSTGRES_PASSWORD_ARG=secret -t gildedrose-api ./Dockerfile'
            }
        }
        stage('Push image'){
            steps{
                withCredentials([string(credentialsId: 'dockerHubPassword', variable: 'dockerHubPassword')]) {
                sh "docker login -u juangh15 -p ${dockerHubPassword}"
                    sh 'docker push juangh15/gildedrose-backend:2.0.0'
            }

          
            }
            
        }
    }
    
 }
 
 
