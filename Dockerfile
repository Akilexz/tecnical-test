FROM openjdk:17
EXPOSE 8081

#WORKDIR /api

ADD tt-services/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]