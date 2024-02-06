FROM tomcat:latest
LABEL authors="Nikita"

COPY target/Weather-tracker-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war