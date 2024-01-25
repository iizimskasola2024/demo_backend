# Quarkus Measurements Project 📏

## Running the application in dev mode (API + h2) 👷‍♂️

You can run your application in dev mode that enables live coding using - [Dev UI](http://localhost:8080/q/dev/):

```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application (Production: API + PostgreSQL) 🌐

The application can be packaged using:

```shell script
./mvnw package
```

Then run (build and up):
```
docker-compose build
docker-compose up
```

## Running container 📦

### Classic 🏛️:

Before building the container image run:

```
./mvnw package
```

Then, build the image with:

```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/measurements-jvm .
```

Then run the container using:

```
docker run -i --rm -p 8080:8080 quarkus/measurements-jvm
```

### Native 🏞️:

Before building the container image run:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative --define quarkus.native.container-build=true
```

Then, build the image with:

```
docker build -f src/main/docker/Dockerfile.native -t quarkus/measurements .
```

Then run the container using:

```
docker run -i --rm -p 8080:8080 quarkus/measurements
```
