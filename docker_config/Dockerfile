# Set the base image to Ubuntu
FROM ubuntu:18.04

# Set the ARGs for BUILD  runtime
ARG POSTGRES_IP_ARG
ARG POSTGRES_PORT_ARG
ARG POSTGRES_USER_ARG
ARG POSTGRES_PASSWORD_ARG

# Set the ENV variables for RUNtime
ENV POSTGRES_IP=$POSTGRES_IP_ARG
ENV POSTGRES_PORT=$POSTGRES_PORT_ARG
ENV POSTGRES_USER=$POSTGRES_USER_ARG
ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD_ARG

# Use the bash shell
SHELL ["/bin/bash", "-c"] 

# - Update and add the required packages as:
#   git, openjdk 17, maven 3.8.5, etc.
# - This also make a clone and maven build of the
#   gildedrose repository.
# - Finally, unnecessary libraries and installation 
#   files are removed.
RUN \
    apt-get update \
    && apt-get install -y wget \
    && apt-get install -y git \
    && wget "https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz" \
    && tar xvf openjdk-17.0.2_linux-x64_bin.tar.gz \
    && mv jdk-17.0.2/ /opt/jdk-17/ \
    && echo 'export JAVA_HOME=/opt/jdk-17/' | tee -a /etc/profile \
    && echo 'export PATH=$PATH:$JAVA_HOME/bin' | tee -a /etc/profile \
    && source /etc/profile \
    && wget "https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz" -P /tmp \
    && tar xf /tmp/apache-maven-*.tar.gz -C /opt \
    && ln -s /opt/apache-maven-3.8.5 /opt/maven \
    && echo 'export M2_HOME=/opt/maven' | tee -a /etc/profile \
    && echo 'export MAVEN_HOME=/opt/maven' | tee -a /etc/profile \
    && echo 'export PATH=${M2_HOME}/bin:${PATH}' | tee -a /etc/profile \
    && source /etc/profile \
    && git clone https://github.com/juangh15/praxis-gildedrose-cloned.git \
    && cd praxis-gildedrose-cloned \
    && mvn package \
    && rm -rf openjdk-17.0.2_linux-x64_bin.tar.gz \
    && rm -rf /tmp/apache-maven-3.8.5-bin.tar.gz \
    && rm -rf /opt/maven \
    && rm -rf /opt/apache-maven-3.8.5 \
    && rm -rf /root/.m2/ \
    && rm -rf /opt/jdk-17/jmods \
    && rm -rf /opt/jdk-17/lib/src.zip \
    && rm -rf /var/lib/apt/lists/ \
    && apt-get purge --auto-remove -y git \
    && apt-get purge --auto-remove -y wget

# Make the entrypoint in the builded jar
ENTRYPOINT ["/opt/jdk-17/bin/java","-jar", "/praxis-gildedrose-cloned/target/gildedrose-0.0.1-SNAPSHOT.jar"]

# Expose the default Gildedrose port
EXPOSE 8080