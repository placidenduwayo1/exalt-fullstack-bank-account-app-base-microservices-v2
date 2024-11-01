FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
ARG APPLICATION_NAME2
ARG SERVER_PORT2
ARG CONFIGURATION_SERVER_HOST
ARG CONFIGURATION_SERVER_PORT

ENV APPLICATION_NAME2=${APPLICATION_NAME2}
ENV SERVER_PORT2=${SERVER_PORT2}
ENV CONFIGURATION_SERVER_HOST=${CONFIGURATION_SERVER_HOST}
ENV CONFIGURATION_SERVER_PORT=${CONFIGURATION_SERVER_PORT}

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar exalt-microservices-registration-server.jar extract

FROM maven:3.9.8-eclipse-temurin-21-alpine AS final
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE 8761
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
