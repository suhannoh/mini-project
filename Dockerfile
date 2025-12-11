# 1단계: Gradle로 빌드
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# 현재 프로젝트 전체 복사
COPY . .

# gradlew 실행 권한 주고 bootJar 빌드
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행용 JRE 이미지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 빌드된 jar 하나를 app.jar로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 스프링부트 기본 포트
EXPOSE 8080

# 컨테이너 시작하면 스프링부트 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
