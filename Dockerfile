FROM payara/server-full:6.2024.3-jdk17

COPY target/reloaderproject.war /opt/payara/appserver/glassfish/domains/domain1/autodeploy/

EXPOSE 8080