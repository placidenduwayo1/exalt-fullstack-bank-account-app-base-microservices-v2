FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
ARG APPLICATION_NAME4
ARG SERVER_PORT4
ARG CONFIGURATION_SERVER_HOST
ARG CONFIGURATION_SERVER_PORT
ARG DATABASE_HOST
ARG DATABASE_PORT
ARG DATABASE_NAME
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG PUBLIC_KEY
ARG PRIVATE_KEY

ENV APPLICATION_NAME4=${APPLICATION_NAME4}
ENV SERVER_PORT4=${SERVER_PORT4}
ENV CONFIGURATION_SERVER_HOST=${CONFIGURATION_SERVER_HOST}
ENV CONFIGURATION_SERVER_PORT=${CONFIGURATION_SERVER_PORT}
ENV DATABASE_HOST=${DATABASE_HOST}
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_NAME=${DATABASE_NAME}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV PRIVATE_KEY=${PRIVATE_KEY}
ENV PUBLIC_KEY=${PUBLIC_KEY}

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar exalt-business-ms-spring-security-aouth2-res-server.jar extract

FROM maven:3.9.8-eclipse-temurin-21-alpine AS final
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE ${SERVER_PORT4}
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]