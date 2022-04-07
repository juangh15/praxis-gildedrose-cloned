#!/bin/bash      

apt-get update
apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io
docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5433:5432 -d postgres

apt install git
wget "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz"
tar xvf openjdk-17.0.2_linux-x64_bin.tar.gz
mv jdk-17.0.2/ /opt/jdk-17/
echo 'export JAVA_HOME=/opt/jdk-17/' | sudo tee -a /etc/profile
echo 'export PATH=$PATH:$JAVA_HOME/bin' | sudo tee -a /etc/profile
source /etc/profile

wget "https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz" -P /tmp
tar xf /tmp/apache-maven-*.tar.gz -C /opt
ln -s /opt/apache-maven-3.8.5 /opt/maven

echo 'export M2_HOME=/opt/maven' | tee -a /etc/profile 
echo 'export MAVEN_HOME=/opt/maven' | tee -a /etc/profile 
echo 'export PATH=${M2_HOME}/bin:${PATH}' | tee -a /etc/profile
source /etc/profile

git clone https://github.com/juangh15/praxis-gildedrose-cloned.git
cd praxis-gildedrose-cloned
mvn install
java -jar target/gildedrose-0.0.1-SNAPSHOT.jar














