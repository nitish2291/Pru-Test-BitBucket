FROM alpine/git as clone
ARG url
WORKDIR /app
RUN git clone ${url}

FROM maven:3.5-jdk-8-alpine as build
ARG project
WORKDIR /app
COPY --from=clone /app/${project} /app
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine
ARG artifactid
ARG version
ENV jarname ${artifactid}-${version}.jar
WORKDIR /app
COPY --from=build /app/target/${jarname} /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar ${jarname}"] 