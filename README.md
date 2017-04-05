#spring-microservice - spring boot example
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badge/)    

[![Build Status](https://travis-ci.org/Iurii-Dziuban/spring-microservice.svg?branch=master)](https://travis-ci.org/Iurii-Dziuban/spring-microservice)
[![Coverage Status](https://coveralls.io/repos/github/Iurii-Dziuban/spring-microservice/badge.svg?branch=master)](https://coveralls.io/github/Iurii-Dziuban/spring-microservice?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/58e33daa26a5bb002b54c0c6/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58e33daa26a5bb002b54c0c6)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/Iurii-Dziuban/spring-microservice/issues)

Project shows capabilities of spring boot micro service along with spring mvc, rest, spring security, jpa, hibernate and h2 db.

0) Project structure

database - db scripts for h2 flyway eligible and dev oracle
db - local h2 db
user-service - spring boot entry point and configuration
user-service-client-api - java client api
user-service-model - business model
user-service-persistence - jpa persistence
user-service-provider - services
user-service-resource - hateaos and UI model
user-service-webapp - rest controllers
user-service-webapp-interfaces = rest interfaces

user-codegen - experimental project to generate java from swagger specification.
                                      Could be handy for quick look.
  mvn clean generate-sources

1) Running application locally with in memory h2 db with flyway migrations and swagger turned on :

IDEA:

run/debug UserServiceStarter.java as Spring boot application (set "Active profiles" to "dev" without quotes.)

Packaged jar:

  java -jar user-service/target/user-service.jar
         with profile
  java -Dspring.profiles.active=dev -jar user-service/target/user-service.jar

    with remote debuging

  java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar user-service/target/user-service.jar
         with profile
  java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -Dspring.profiles.active=dev -jar user-service/target/user-service.jar

Maven goal:
  cd user-service
    mvn spring-boot:run
         with profile
    mvn spring-boot:run -Drun.profiles=dev

3) Swagger on local env:
URLs:
    http://localhost:9000/ ping page
    http://localhost:9000/swagger-ui.html - UI representation of services.
    http://localhost:9000/v2/api-docs - json swagger schema

4) Logs
Log configuration is under "user-service\src\main\resources\logback-spring.xml"
Look for "springProfile" for default, dev profiles: Console appender, File appender, etc.
Log folder: /logs
