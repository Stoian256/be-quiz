FROM gradle:8.4-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/be-quiz-1.0.0.jar /app/

ENTRYPOINT ["java","-jar","/app/be-quiz-1.0.0.jar"]