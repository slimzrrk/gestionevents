FROM maven:3.6.3-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim AS prod
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java" , "-jar" , "app.jar"]
