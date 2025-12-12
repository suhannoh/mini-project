# ---- build stage ----
FROM gradle:8-jdk17 AS build
WORKDIR /app

# 캐시 최적화 (의존성 먼저)
COPY gradle gradle
COPY gradlew gradlew
COPY build.gradle* settings.gradle* gradle.properties* ./
RUN chmod +x gradlew
RUN ./gradlew --no-daemon dependencies || true

# 소스 복사 후 빌드
COPY . .
RUN ./gradlew clean bootJar -x test --no-daemon

# ---- run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드 산출물 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Render가 PORT 환경변수를 주는 경우가 많음
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
