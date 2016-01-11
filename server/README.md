# e-Wall backend

E-Wall backend is a Spring Boot application. We use [Gradle](https://gradle.org/) to build the archive


## Develop UI

To launch the webapp you can use `../gradlew bootRun`

## Hack e-wall

If you want to know the API go to this URL

`http:\\localhost:8080\api`

## QrCode

To generate the QRCode we use the OSS library provided by Google https://github.com/zxing/zxing

## Parameters for production

If you want to install this project, you can clone this repository and used a terminal to launch
```./gradlew clean build```

When the project is build see [how to install database here](common/README.md)

The build task creates a jar archive in the directory ```server/build/libs```. This jar is named ```server.jar```

You can see all the app parameters in the file [server/src/main/resources/application.yml](server/src/main/resources/application.yml). You can override all the values in a file put in a directory named `conf` . This directory has to be in the same directory that the `server.jar` used when you launch the app

If you don't like file you can specify theses parameters like args when you execute the jar

For example

```java -jar server.jar --server.port=8090```