FROM amazoncorretto:21
RUN yum update -y
ADD ./build/libs/ifykyk-0.0.1-SNAPSHOT.jar /usr/src/app/ifykyk.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/usr/src/app/ifykyk.jar", "--spring.profiles.active=production" ]
