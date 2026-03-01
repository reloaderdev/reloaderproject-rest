FROM payara/server-full:6.2024.3-jdk17

COPY target/reloaderproject.war $DEPLOY_DIR

EXPOSE 8080
