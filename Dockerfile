FROM openjdk:11
VOLUME /uploads
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/speak
ENV DB_USER=ENC(w3rY4gk8RbiyVjqmForWqmKfpSqWu1OpqIzhZhHqrmUrTuh4EKrBL7U1+9DStPH5)
ENV DB_PASS=ENC(ilD17qUU1gYoisvz/ViQ0IAh+9dcMEiSn2TJM6lEgFhdX1bnBC7LOgKGqPRaasYP)
MAINTAINER glieunou.com
COPY target/*.jar speak.jar
ENTRYPOINT ["java","-Djasypt.encryptor.password=Wouri@2022#", "-jar", "-Dspring.profiles.active=docker","-Dapp.database.url=${DB_URL}","-Dapp.database.user=${DB_USER}","-Dapp.database.pass=${DB_PASS}","/speak.jar"]