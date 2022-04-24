# Pru-Test-BitBucket

This is a sample Spring Boot App to which opens a Swagger page with the service name Hello World.
This repo also contains the configurations to run the App without any hassles. Below are the steps carried out to get the App up.

**NOTE** : Skip to part C directly to bootstrap the Application in no time via Jenkinsfile.

A. **MANUAL RUNNING**
Dockerfile has the step by step process to build and run the app.
The steps:

1. Clone the repo in the local agent
2. Running maven command : **mvn clean install -Dmaven.test.skip=true** - compiles the code and outputs the jar file spring-boot-helloworld-0.0.1-SNAPSHOT.jar
3. Running the App with command and created jar file: **java -jar spring-boot-helloworld-0.0.1-SNAPSHOT.jar**

B. **CONTAINERISATION VIA DOCKERFILE**
Executing the dockerfile to create the image:

**docker build --build-arg url=https://github.com/nitish2291/Pru-Test-BitBucket.git --build-arg project=Pru-Test-BitBucket --build-arg artifactid=spring-boot-helloworld --build-arg version=0.0.1-SNAPSHOT -t helloworld/spring-boot - < Dockerfile**

Once the command is executed and the image is created, Running the image as the container on port 8080 HTTP port:

**docker run -ti -p8080:8080 helloworld/spring-boot**


C. **SEAMLESS AUTOMATION VIA PIPELINE**
All of the above steps are automated by Jenkins via the **jenkinsfile** in this repo. This can be imported and run from any Jenkins instance to run this app on the respective machines.
