FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

ARG APPLICATION_NAME6
ARG CONFIGURATION_SERVER_HOST
ARG CONFIGURATION_SERVER_PORT
ARG DATABASE_HOST
ARG DATABASE_PORT
ARG DATABASE_NAME3
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG KAFKA_KEY_SERIALIZER
ARG KAFKA_VALUE_SERIALIZER
ARG KAFKA_KEY_DESERIALIZER
ARG KAFKA_VALUE_DESERIALIZER
ARG KAFKA_APPLICATION_TOPIC_NAME010
ARG KAFKA_APPLICATION_TOPIC_RETENTION
ARG PUBLIC_KEY
ARG SERVER_PORT6

ENV APPLICATION_NAME6=${APPLICATION_NAME6}
ENV CONFIGURATION_SERVER_HOST=${CONFIGURATION_SERVER_HOST}
ENV CONFIGURATION_SERVER_PORT=${CONFIGURATION_SERVER_PORT}
ENV DATABASE_HOST=${DATABASE_HOST}
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_NAME3=${DATABASE_NAME3}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV KAFKA_KEY_SERIALIZER=${KAFKA_KEY_SERIALIZER}
ENV KAFKA_VALUE_SERIALIZER=${KAFKA_VALUE_SERIALIZER}
ENV KAFKA_KEY_DESERIALIZER=${KAFKA_KEY_DESERIALIZER}
ENV KAFKA_VALUE_DESERIALIZER=${KAFKA_VALUE_DESERIALIZER}
ENV KAFKA_APPLICATION_TOPIC_NAME010=${KAFKA_APPLICATION_TOPIC_NAME010}
ENV KAFKA_APPLICATION_TOPIC_RETENTION=${KAFKA_APPLICATION_TOPIC_RETENTION}
ENV PUBLIC_KEY=${PUBLIC_KEY}
ENV SERVER_PORT6=${SERVER_PORT6}

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src
RUN mvn package -DskipTest
WORKDIR /build/target/
RUN java -Djarmode=layertools -jar exalt-business-microservice-customer.jar extract

FROM maven:3.9.8-eclipse-temurin-21-alpine AS final
COPY --from=builder /build/target/dependencies/ ./
COPY --from=builder /build/target/spring-boot-loader/ ./
COPY --from=builder /build/target/snapshot-dependencies/ ./
COPY --from=builder /build/target/application/ ./

EXPOSE ${SERVER_PORT6}
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
