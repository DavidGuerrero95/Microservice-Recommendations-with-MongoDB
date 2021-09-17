FROM openjdk:15
VOLUME /tmp
ADD ./target/springboot-recomendaciones-0.0.1-SNAPSHOT.jar recomendaciones.jar
ENTRYPOINT ["java","-jar","/recomendaciones.jar"]