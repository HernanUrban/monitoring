FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
ADD target/*.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]