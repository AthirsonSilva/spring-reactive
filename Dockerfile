#
# Build stage
#
FROM maven:3.9.1-eclipse-temurin-20 AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:20

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

ENV SPRING_ENV=prod
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]