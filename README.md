# spring-microservice - spring boot example
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badge/)    

[![Build Status](https://travis-ci.org/Iurii-Dziuban/spring-microservice.svg?branch=master)](https://travis-ci.org/Iurii-Dziuban/spring-microservice)
[![Coverage Status](https://coveralls.io/repos/github/Iurii-Dziuban/spring-microservice/badge.svg?branch=master)](https://coveralls.io/github/Iurii-Dziuban/spring-microservice?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/58e33daa26a5bb002b54c0c6/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58e33daa26a5bb002b54c0c6)
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/Iurii-Dziuban/spring-microservice/issues)


Project shows capabilities of spring boot micro service along with `spring mvc`, `rest`, `spring security`, `jpa`, `hibernate` and `h2` db.

# Checks

Jacoco code coverage, pmd, checkstyle, enforcer, findbugs

# Project structure
- `checkstyles` - contains checkstyle that is checked during build and fail if rules are violated. In addition findbugs and PMD (with duplication check) is checked and test coverage should be 100% otherwise build is failed.
   This ensures code quality. From checkstyle perspective imports are validated: 
   `hamcrest` and `junit.Assert` -> `AssertJ`, `Mockito` -> `BDDMockito`, etc. Code coverage can be checked on the web page under    `target/site/jacoco/index.html` 
- `database` - db scripts for h2 flyway eligible
- `db` - local h2 db
- `user-service` - spring boot entry point and main configuration. Integration tests also located there `@IntegrationTest` is used to determine if it is integration test.
- `user-service-client-api` - java client api. Can be used in integration tests and by real clients. Under `src/test/resources/__files` examples of requests can be found.
- `user-service-model` - business model objects (POJOs)
- `user-service-persistence` - jpa persistence (Entitiy objects)
- `user-service-provider` - service classes with bussiness logic
- `user-service-resource` - hateaos and UI model (Hateos classes)
- `user-service-webapp` - rest controllers rest controllers (Entry point to application)
- `user-service-webapp-interfaces` - rest interfaces (interfaces for rest controllers). Client API uses these interfaces.
- `specification` - updated, latest swagger specification for the service.

*Note* Rest interfaces should have same methods as rest controllers, however spring does not support rest controllers to implement interfaces. Consistency should be handled manually. 

`user-codegen` - experimental project to generate java from swagger specification.
Could be handy for quick look.
`mvn clean generate-sources`

# Running application locally with in memory h2 db with flyway migrations and swagger turned on :

## IDEA:

run/debug `UserServiceStarter.java` as Spring boot application (set `Active profiles` to `dev`)

Packaged jar:

`java -jar user-service/target/user-service.jar`

with profile

`java -Dspring.profiles.active=dev -jar user-service/target/user-service.jar`

with remote debuging

`java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar user-service/target/user-service.jar`

with profile

`java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -Dspring.profiles.active=dev -jar user-service/target/user-service.jar`

## Maven goal:
`cd user-service`
`mvn spring-boot:run`
         
with profile

`mvn spring-boot:run -Drun.profiles=dev`  

Or

`cd user-service`
`mvn spring-boot:run -Dspring.profiles.active=dev`

# Running integration tests.
  ## Maven
Integration tests can be easily executed from IDEA as a normal junit test. By default it will use local h2 db and mocks and start spring boot service.

By default Integration tests are skipped. Just to accelerate the build on dev machines.

In order to include them, run the build with profile `-PrunIntegrationTests`, 
which is by default enables `spring.profiles.active=dev`

`mvn clean install -PrunIntegrationTests`

With `dev` profile integration Tests will use local environment configuration (different http port), db2.
In order to use default environment instead of local configuration use:

`mvn clean test -PrunIntegrationTests -Dspring.profiles.active=default`

  ## Intellij Idea
Run tests normally. Go to `Edit configurations` add property to `VM options` : `-Dspring.profiles.active=dev`
and run. Otherwise tests with `@IfProfileValue(name ="spring.profiles.active", value ="dev")` will be skipped.

# Swagger on local env:
URLs:
- `http://localhost:9000/` ping page
- `http://localhost:9000/swagger` - ui.html - UI representation of services.
- `http://localhost:9000/v2/api-docs` - json swagger schema

# Logs
Log configuration is under `user-service\src\main\resources\logback-spring.xml`
Look for `springProfile` for `default`, `dev` profiles: Console appender, File appender, etc.
Log folder: `/logs`

# Troubleshooting.
If userService can not acquire connection / request hangs -> database lock on a record
- Check if the record is locked in db (in case request for particular record hangs)
- Release the lock.
Example
```
select * from WEBSERVICE.USER where ID = 'USERID' for update skip locked;
 = will be empty if record is locked.

select * from SYS.DBA_OBJECTS where object_name like '%LOCK%'
 = check which table has Lock info.

select * from V$SESSION where machine like '%user%'
 = check locks related to particular machine. ('user' is a part of the name for our server)
```
 
 # DB lock mechanism
  Locking mechanism is done via suggestion here. http://stackoverflow.com/questions/16159396/how-to-enable-lockmodetype-pessimistic-write-when-looking-up-entities-with-sprin
  Because other solutions do not work, like annotating any service method with `@Lock(LockModeType.PESSIMISTIC_WRITE)`. Spring wont understand that.
 The workaround with injecting entity manager and handling locks explicitly is a bit verbose.
 So current solution looks that way (so that normal findOne method wont lock the record)
 ```
 private final EntityManager entityManager;
 
 @Override
 public User findOneAndLock(String id){
     return entityManager.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
 }
```
 Lock will be automatically released after transaction is completed. 
 *Note* check that service methods/class have annotation `@Transactional`
 
 # Performance optimizations
 
In order not to check if the record exists in the db - we can have one call to get record and try to update/delete it. In case error occurs just rollback the transaction and everything will be Ok.

This will help to focus on normal flow and reduce number of calls to the db as they are expensive.
 
Also properties connectionTimeout and readTimeout are important and can be set. (default values are 60 seconds for both).
 
In multi-threaded environment they are important, cause configure how long to wait to acquire connection before timeout and how long to wait for the response after request was send.
 ```
 public abstract class Timeoutable {
 ...
 public void setConnectionTimeout(int connectionTimeout) {
     SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) getRestTemplate().getRequestFactory();
     factory.setConnectTimeout(connectionTimeout);
 }
 
 public void setReadTimeout(int readTimeout) {
     SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) getRestTemplate().getRequestFactory();
     factory.setReadTimeout(readTimeout);
 }
 ```
