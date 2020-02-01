FROM openjdk:8-jdk-alpine
ADD target/gymapp.jar gymapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","gymapp.jar"]