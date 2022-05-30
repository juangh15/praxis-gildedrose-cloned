FROM maven:3.8.5-openjdk-17-slim as maven_image
COPY . .
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

# Run Maven
RUN mvn -B package

# Set the entrypoint
FROM openjdk:17-jdk-alpine
COPY --from=maven_image target/gildedrose-0.0.1-SNAPSHOT.jar gildedrose-api.jar
ENTRYPOINT ["java","-jar","gildedrose-api.jar"]
EXPOSE 8080
