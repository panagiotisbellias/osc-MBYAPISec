FROM maven:3-eclipse-temurin-21-jammy
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]