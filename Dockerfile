FROM gradle:8.4-jdk17-alpine AS build

WORKDIR /home/gradle/src

COPY . .

RUN gradle build --no-daemon
 
FROM openjdk:17-jdk-alpine

EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar/
 
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
