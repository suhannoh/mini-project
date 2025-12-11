# 1단계: Gradle로 빌드
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# 현재 프로젝트 전체 복사
COPY . .

# gradle 명령으로 바로 빌드 (wrapper 사용 X)
RUN gradle --no-daemon clean bootJar -x test

# 2단계: 실행용 JRE 이미지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 빌드된 jar 하나를 app.jar로 복사
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
