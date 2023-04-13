FROM openjdk:12
VOLUME /tmp
EXPOSE 8761
ADD ./target/com.formacionbdi.springboot.app.eureka-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]