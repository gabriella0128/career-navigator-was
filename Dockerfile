# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Gradle Wrapper만 복사
COPY gradlew ./
COPY gradle/ ./gradle/
COPY settings.gradle* build.gradle* ./

# 실행 권한
RUN chmod +x ./gradlew

# 소스 복사 후 빌드
COPY src/ ./src/
RUN --mount=type=cache,target=/root/.gradle/caches \
    ./gradlew --no-daemon clean bootJar

# ---- 런타임 이미지 ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-Dserver.port=8080","-jar","app.jar"]
