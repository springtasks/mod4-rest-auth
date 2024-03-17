#
# Build stage
#
FROM maven:3.6-openjdk-17-slim AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/mod4-app-auth2-0.0.1-SNAPSHOT.jar /usr/local/lib/mod4.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/mod4.jar"]
