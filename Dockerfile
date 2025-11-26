# 1. Etapa de Construcción (Build)
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos el proyecto saltando los tests para que sea más rápido en la nube
RUN gradle bootJar --no-daemon -x test

# 2. Etapa de Ejecución (Run)
FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]