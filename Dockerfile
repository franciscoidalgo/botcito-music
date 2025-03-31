ARG DISC_TOKEN

FROM gradle:8-jdk-21-and-22-alpine AS BUILD
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
COPY src src
RUN chown -R gradle:gradle /app
RUN chmod +x gradlew
RUN ./gradlew clean shadowJar --no-daemon

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=BUILD /app/build/libs/*.jar /app/app.jar
ENV DISCORD_BOT_TOKEN=$DISC_TOKEN
CMD ["java", "-jar", "app.jar"]