FROM openjdk:8-jre
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
ADD build/libs/spring-boot-mall-project-0.0.1-SNAPSHOT.jar /usr/src/app.jar
ENTRYPOINT ["java", "-server", "-jar", "/usr/src/app.jar"]
EXPOSE 8080