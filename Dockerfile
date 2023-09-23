FROM eclipse-temurin:17-jdk
MAINTAINER fotistsiskakis
COPY target/BetStrategist-1.0.jar BetStrategist-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/BetStrategist-1.0.jar"]