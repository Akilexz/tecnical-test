# Base project for Spring Boot

## Style code

## Build

```bash
./gradlew clean build 
```

## Run

```bash
./gradlew tt-service:bootRun
```

## Docker
1) Build application
```bash
./gradlew clean build
```
2) Build Docker Image
```bash
docker build --no-cache -t technical-test:1.0.0 .
```
3) Docker Run
```bash
docker run -it --rm --name tt-services -p 8081:8081 dockerdev/tt-services:1.0.0
```