FROM openjdk:22

ARG JAR_FILE=build/libs/hey-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./hey-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","-Dsun.net.inetaddr.ttl=0","./hey-0.0.1-SNAPSHOT.jar" ]