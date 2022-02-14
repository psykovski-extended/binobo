FROM openjdk:13-alpine
MAINTAINER binobo.io
ADD target/binobo-2.0-Alpha.jar binobo.jar
ENTRYPOINT ["java","-jar","binobo.jar"]