FROM openjdk:17-jdk-alpine AS builder
WORKDIR /app
# COPY es para copiar archivos de la maquina local a la imagen de docker
COPY ./mvnw ./
COPY ./pom.xml  ./
COPY ./.mvn ./.mvn
# todas estás instrucciónes -Dmaven son para saltar los test y no ejecutarlos en la construcción de la imagen
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/target/restaurant-reservation-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "restaurant-reservation-api-0.0.1-SNAPSHOT.jar"]