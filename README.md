
# Transfer it
Minimal CRUD services exposing a couple of endpoints over REST, that provide money transfer between accounts.

Under the hood this is using:
 - RESTEasy to expose the REST endpoints;
 - Simplified hibernate ORM with Panache;
 - A H2 database;
 - ArC, the CDI inspired dependency injection tool with zero overhead;
 - The high performance Agroal connection pool;
 - REST Assured for functional testing.

## Requirements

To compile and run this demo you will need:

- JDK 1.8+

### Configuring JDK 1.8+

Make sure that the `JAVA_HOME` environment variable has been set, and that a JDK 1.8+ `java` command is on the path.

## Building

Launch the Maven build on the checked out sources of this demo:

> ./mvnw package

### Live coding

The Maven Quarkus plugin provides a development mode that supports live coding:

> ./mvnw quarkus:dev

### Run in JVM mode

The application may run as a conventional jar file.

First compile it:

> ./mvnw package

Then run it:

> java -jar ./target/transferit-1.0-SNAPSHOT-runner.jar