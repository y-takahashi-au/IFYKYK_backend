FROM amazoncorretto:21
RUN yum update
ADD ./ifykyk-0.0.1-SNAPSHOT.jar /usr/src/app/ifykyk.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/usr/src/app/ifykyk.jar"]
