#Command for generate jar = mvnw package
#Image docker with instruction : https://hub.docker.com/r/adoptopenjdk/openjdk11/
#$ docker build -t japp .
#$ docker run -p 8080:8080 -it --rm japp
FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/app
COPY target/superhero-0.0.1-SNAPSHOT.jar /opt/app/japp.jar
CMD ["java", "-jar", "/opt/app/japp.jar"]