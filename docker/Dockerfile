FROM openjdk:11
RUN mkdir app
ARG SOURCE=build/libs/MovieManager-0.0.1-SNAPSHOT.jar
COPY ${SOURCE} app/MovieManager-0.0.1-SNAPSHOT.jar
RUN chmod -R 775 app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app/MovieManager-0.0.1-SNAPSHOT.jar"]