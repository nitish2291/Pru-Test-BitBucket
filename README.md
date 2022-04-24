# Pru-Test-BitBucket

This is a sample Spring Boot App to which opens a Swagger page with the service name Hello World.

Dockerfile has the step by step process to build and run the app.
The steps:

1. Clone the repo in the local agent
2. Running maven command : **mvn clean install -Dmaven.test.skip=true** - compiles the code and outputs the jar file spring-boot-helloworld-0.0.1-SNAPSHOT.jar
3. Running the App with command and created jar file: **java -jar spring-boot-helloworld-0.0.1-SNAPSHOT.jar**

Executing the dockerfile to create the image:
**docker build --build-arg url=https://github.com/nitish2291/Pru-Test-BitBucket.git --build-arg project=Pru-Test-BitBucket --build-arg artifactid=spring-boot-helloworld --build-arg version=0.0.1-SNAPSHOT -t helloworld/spring-boot - < Dockerfile**

Once the command is executed and the image is created, Running the image as the container on port 8080 HTTP port:
**docker run -ti -p8080:8080 helloworld/spring-boot**
