# 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

# Gradle Wrapper 관련 파일 복사
COPY build.gradle settings.gradle ./
COPY gradle gradle/
COPY gradlew .

# Gradle 종속성 다운로드 (캐싱 활용)
RUN chmod +x ./gradlew && ./gradlew --no-daemon dependencies

# 전체 소스 복사 및 빌드
COPY . .
RUN ./gradlew --no-daemon bootJar

# 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
