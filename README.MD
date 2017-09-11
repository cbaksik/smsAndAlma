To run a debug mode in Jar file

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8080 -jar ./TextSMS-0.0.1-SNAPSHOT.jar


To run application in background mode on production AWS

/etc/alternatives/java_sdk_1.8.0/bin/java -jar ./TextSMS-0.0.1-SNAPSHOT.jar &


Make sure you have JSDK version 1.8.0 or higher


