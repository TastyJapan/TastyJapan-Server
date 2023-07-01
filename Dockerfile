FROM openjdk:17.0.2-slim

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} tastyjapan.jar

ARG PROFILE
ENV PROFILE_ENV=$PROFILE
ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE_ENV -Duser.timezone=Asia/Seoul tastyjapan.jar