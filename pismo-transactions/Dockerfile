FROM maven:3-jdk-10-slim as maven-builder

WORKDIR /build
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

FROM openjdk:10-jre-slim

COPY --from=maven-builder /build/target/pismo-transactions-api-1.0.jar /app/pismo-transactions-api-1.0.jar

CMD exec java $JAVA_OPTS -jar /app/pismo-transactions-api-1.0.jar
