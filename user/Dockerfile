FROM eclipse-temurin:21-jdk
WORKDIR /app

ARG FILE_JAR=target/*.jar
COPY ${FILE_JAR} user-service.jar

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "user-service.jar"]
