#!/bin/bash

# set java home

# set app variables
APP_CONFIG=@app.conf.dir@
APP_LOG_CONFIG=$APP_CONFIG/logback-spring.xml
APP_JAR=@app.lib.dir@/@app.java.name@-@project.version@.jar

java -cp $APP_CONFIG -jar $APP_JAR --spring.config.location=$APP_CONFIG/ --logging.config=$APP_LOG_CONFIG "$@"