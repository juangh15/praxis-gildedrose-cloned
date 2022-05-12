node{
    stage('Build image'){
      sh 'docker build -t juangh15/gildedrose-backend .'
    }
    stage('Push image'){
        withCredentials([string(credentialsId: 'dockerHubPassword', variable: 'dockerHubPassword')]) {
            sh "docker login -u juangh15 -p ${dockerHubPassword}"
        }
      
      sh 'docker push juangh15/gildedrose-backend:2.0.0'
    }
 }
 
 
