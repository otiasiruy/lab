# Use a Java 17 base image
FROM openjdk:17-jdk-slim

# Install curl and Tomcat
RUN apt-get update && apt-get install -y curl \
    && mkdir -p /usr/local/tomcat \
    && curl -fSL https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.11/bin/apache-tomcat-10.1.11.tar.gz \
       | tar -xzC /usr/local/tomcat --strip-components=1 \
    && rm -rf /var/lib/apt/lists/*

# (Optional) Remove default webapps for a clean deployment
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR to Tomcat webapps folder
COPY build/libs/my-spring6-app.war /usr/local/tomcat/webapps/ROOT.war

# Copy the OpenTelemetry agent
COPY build/otel/opentelemetry-javaagent.jar /otel/otel-javaagent.jar

ENV JAVA_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED \
    -javaagent:/otel/otel-javaagent.jar \
    -Dotel.exporter.otlp.endpoint=http://otel-collector:4317"

EXPOSE 8080

CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]