FROM openjdk:11
VOLUME /uploads
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/speak
ENV DB_USER=root
ENV DB_PASS=''
MAINTAINER glieunou.com
COPY target/*.jar speak.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker","-Dapp.database.url=${DB_URL}","-Dapp.database.user=${DB_USER}","-Dapp.database.pass=${DB_PASS}","/speak.jar"]