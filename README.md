# reactive-spring-k8s
Test tcp, websocket and http reactive traffic other k8s network...

## build, run and test locally

```bash
./mvnw clean package
java -jar ./r-socket-r2dbc/target/*.jar &
java -jar ./r-socket-websocket/target/*.jar &
java -jar ./r-socket-webflux/target/*.jar &
# wait for ports: 8001 8002 8080
http --stream :8080/stream-messages &
http :8080/find-messages
http :8080/save-message sender=Max body=ololo
http :8080/save-message sender=Max body=trololo
# stop any ports: 8001 8002 8080
```

## jib

```bash
./mvnw package jib:dockerBuild
docker run --rm -d --name r2dbc daggerok/reactive-spring-k8s-r-socket-r2dbc
docker run --rm -d --name websocket -e R2DBC_HOST=r2dbc daggerok/reactive-spring-k8s-r-socket-websocket
docker run --rm -d --name webflux -e WEBSOCKET_HOST=websocket -p 8080:8080 daggerok/reactive-spring-k8s-r-socket-webflux
# wait a little bit..
http --stream :8080/stream-messages &
http :8080/find-messages
http :8080/save-message sender=Max body=ololo
http :8080/save-message sender=Max body=trololo
# stop
docker rm -f -v r2dbc websocket webflux
```

## skaffold

```bash
kubectl apply -f k8s/
skaffold dev --cache-artifacts=false
# wait...
http --stream :30080/stream-messages &
http :30080/find-messages
http :30080/save-message sender=Max body=ololo
http :30080/save-message sender=Max body=trololo
# ctrl+c
```

## finally update versions

```bash
./mvnw build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} -DgenerateBackupPoms=false
```

<!--
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Data R2DBC [Experimental]](https://docs.spring.io/spring-data/r2dbc/docs/1.0.x/reference/html/#reference)
* [R2DBC example](https://github.com/spring-projects-experimental/spring-boot-r2dbc/tree/master/spring-boot-example-h2)
* [R2DBC Homepage](https://r2dbc.io)
-->
