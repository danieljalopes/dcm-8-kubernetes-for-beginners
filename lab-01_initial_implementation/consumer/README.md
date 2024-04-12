# Producer
This is a Spring Boot 3 microservice intent to show how to receive and send data in a Kubernetes environment.

## How to set up
Make sure that you have Docker installed.
Edit gradle.properties file to put your Docker Hub credentials:
```
docker.username=username
docker.password=password
docker.email=email
```
## Compile project
Compile project by opening a command line window inside de project folder and run: 
````
./gradlew bootJar
````

## How to build image

Assuming you are in the project's folder:

1. Compile `./gradlew bootJar`
2. Build docker image locally `./gradlew task createImage`
3. Push image to docker repository `./gradlew task pushToDockerHub`