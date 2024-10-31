FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

ARG APPLICATION_NAME1
ARG SERVER_PORT1
ARG CLOUD_CONFIG_SERVER_GIT_URI
ARG CLOUD_CONFIG_SERVER_GIT_DEFAULT_LABEL
ARG CLOUD_CONFIG_SERVER_GIT_PATHS
ARG CLOUD_CONFIG_SERVER_GIT_USERNAME
ARG CLOUD_CONFIG_SERVER_GIT_PASSWORD

ENV APPLICATION_NAME1=${APPLICATION_NAME1}
ENV SERVER_PORT1=${SERVER_PORT1}
ENV CLOUD_CONFIG_SERVER_GIT_URI=${CLOUD_CONFIG_SERVER_GIT_URI}
ENV CLOUD_CONFIG_SERVER_GIT_DEFAULT_LABEL=${CLOUD_CONFIG_SERVER_GIT_DEFAULT_LABEL}
ENV CLOUD_CONFIG_SERVER_GIT_PATHS=${CLOUD_CONFIG_SERVER_GIT_PATHS}
ENV CLOUD_CONFIG_SERVER_GIT_USERNAME=${CLOUD_CONFIG_SERVER_GIT_USERNAME}
ENV CLOUD_CONFIG_SERVER_GIT_PASSWORD=${CLOUD_CONFIG_SERVER_GIT_PASSWORD}

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar exalt-microservices-configuration-server.jar extract

FROM maven:3.9.8-eclipse-temurin-21-alpine AS final
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE ${SERVER_PORT1}
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]