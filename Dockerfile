FROM openjdk:17-alpine
EXPOSE 80
ADD car-manager-core/target/car-manager-core-0.0.1-SNAPSHOT.jar car-manager-core.jar
ADD car-manager-repository/target/car-manager-repository-0.0.1-SNAPSHOT.jar car-manager.jar
ADD car-manager-storage/target/car-manager-storage-0.0.1-SNAPSHOT.jar car-manager-storage.jar
ADD car-manager-api/target/car-manager-api-0.0.1-SNAPSHOT.jar car-manager-api.jar

ENTRYPOINT ["java", "-jar", "/car-manager-api.jar"]
