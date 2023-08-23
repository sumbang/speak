FROM openjdk:11
VOLUME /uploads
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/speak
ENV DB_USER=ENC(zZZICjfF9hacgpUQTR0l/F0C405wlGFV5CTqcPKyVqMDVVUSy7SKHs6IRSCijP8N)
ENV DB_PASS=ENC(Ixogtgq0mXDk/FQ1rhD/Fg5RayHE33DM1eUq8gSjeRFNudq4HkrPbQ1S9alvcH3x)
MAINTAINER glieunou.com
COPY target/*.jar speak.jar
ENTRYPOINT ["java", "-Djasypt.encryptor.password=Wouri@2022#", "-jar", "-Dspring.profiles.active=docker","-Dapp.database.url=${DB_URL}","-Dapp.database.user=${DB_USER}","-Dapp.database.pass=${DB_PASS}","/speak.jar"]