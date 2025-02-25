FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

COPY ./gradlew .
COPY ./gradle ./gradle
COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle
COPY ./src ./src

RUN chmod +x gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

# 도커 실행할 때 .env 파일을 참조하여 환경변수 설정
ENV SPRING_PROFILES_ACTIVE=docker
ENV BASE_UPLOAD_DIR=/app/uploads

RUN mkdir -p /app/uploads

CMD ["java", "-jar", "app.jar"]