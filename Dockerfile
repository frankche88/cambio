FROM eclipse-temurin:11
LABEL org.opencontainers.image.authors="fjonisllap"
COPY build/libs/cambio-0.0.1-SNAPSHOT.jar cambio-0.0.1.jar
ENTRYPOINT ["java","-jar","/cambio-0.0.1.jar"]

