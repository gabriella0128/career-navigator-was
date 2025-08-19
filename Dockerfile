# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY gradlew ./
COPY gradle/ ./gradle/
COPY settings.gradle* build.gradle* ./
RUN chmod +x ./gradlew

COPY src/ ./src/

# 캐시 활용 + clean 보장
RUN --mount=type=cache,target=/root/.gradle/caches \
    ./gradlew --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-Dserver.port=8080","-jar","app.jar"]
