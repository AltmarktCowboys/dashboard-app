FROM openjdk:jre-alpine

ADD dashboard-app-swarm.jar /opt/dashboard-app-swarm.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/dashboard-app-swarm.jar"]