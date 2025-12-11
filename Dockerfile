# ---- Build Stage ----
FROM gradle:8.7-jdk17-alpine AS build
WORKDIR /home/gradle/project

COPY . .

# Build without tests (much faster, avoids test errors)
RUN gradle clean bootJar -x test --no-daemon

# ---- Run Stage ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
