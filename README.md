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
