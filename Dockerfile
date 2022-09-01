FROM openjdk
EXPOSE 8083
ADD target/REST-Api-0.0.1-SNAPSHOT.jar REST-Api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/REST-Api-0.0.1-SNAPSHOT.jar"]
