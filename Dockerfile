FROM azul/zulu-openjdk-alpine:17-latest

COPY target/ToDoApp-0.0.1-SNAPSHOT.war /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch ToDoApp-0.0.1-SNAPSHOT.war'

ARG JAR_FILE=target/ToDoApp-0.0.1-SNAPSHOT.war

ENTRYPOINT ["java","-jar","ToDoApp-0.0.1-SNAPSHOT.war"]

EXPOSE 9100