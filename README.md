
# Numeric Wall

The project lifecycle is managed with [gradle](https://gradle.org/). You don't need to install Gradle on your host because this project use the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).  

## Project organization 

* [common](common/README.md) Common module with database management
* [client](client/README.md) UI Angular 1.4.x
* [server](server/README.md) Backend written in Java 8 with SpringFramework and SpringBoot

## Launch the webapp

This project uses [Spring Boot](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/). To launch the webapp you can use this command
```
./gradlew bootRun
```
This command starts a web browser and you can after visit [http://localhost:8080](http://localhost:8080).  

## Setup DB 

Go in the module [common](common/README.md) 


## Licence

The project is open source on the MIT License (MIT)

    Copyright (c) <year> <copyright holders>

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
    
## Working with the code

You have to clone the main project

```
git clone https://github.com/Dev-Mind/numeric-wall.git
```

After you just have to build the project `./gradlew build`. By default we don't commit any metadata linked to an IDE but all of us use IntelliJ Idea. You can contribute by [Pull requests](https://help.github.com/articles/using-pull-requests/)
