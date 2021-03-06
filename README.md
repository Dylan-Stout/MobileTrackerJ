# MobileTrackerJ
Java web application for storing and displaying GPS coordinates. Location data is sent over HTTPS by client application(s) - Palantir GPS poll is suggested. Uses Google Maps javascript API so an API key is needed to run the map engine.  

# Setup
### Create Databases
1.) Setup and create MySql Database named 'location' from SQL scripts in /res/ folder to be used by web application. This will be used for user login and data. 

### Create Users
2.) Since security is important use security helper to generate a single user hash with using two arguments [username] and [password]. It is recommended to have passwords be complex alphanumeric with symbols and whitespace Example:

> java com.mobile.core.SecurityHelper 'username' 'zs!f0dos9fa<*s++dwk3fnsep sdfasd(sdfqsd>>'

__OUTPUT:__
> Generated query: INSERT INTO USER(USERNAME, USERIP, USERHASH) VALUES ('username', '0.0.0.0', 'sha1:64000:18:BYGHYPBLoxgYO0xkG/bM9ajv6OesWvaa:ppEl8AvFHvfwZWZro4hMsnYA');

Run this query against the 'location' database. NOTE: This is done manually for security reasons until a secure user generation algorithm can be implemented (0.8.1b). Take note of this username and password as it will be used to login to the application. 

### Setup Server
3.) After creating the database, create a war file from source with Maven. Make sure project is setup to use maven build. Build war file with *maven clean install* and deploy to a tomcat web servlet container (I.E. $CATALINA_HOME/webapps/\*.war). Ensure unpackwar=true in tomcat's  $CATALINA_HOME/conf/server.xml (this should be default). 


### Configure DB Connection

4.) In the deployed .war file directory structure modify __$CATALINA_HOME/webapps/(web app name)/WEB-INF/classes/mobile_tracker.properties__ and supply the properties for db_url, db_user, db_pass, db_driver, and allowded_ip. This will allow the tomcat server to make JDBC connections to the MySql application database for read/write via hibernate ORM and it's connection pool. 

Example: 
```
db_url=jdbc:mysql://localhost:3306/location
db_user=user1
db_pass=thisisthepassword
db_driver=com.mysql.jdbc.Driver
allowed_ip=96.86.193.49,71.219.38.228,127.0.0.1 
```

### Startup Server

5.) Startup tomcat via $CATALINA_HOME/bin/startup.sh . Monitor logs in $CATALINA_HOME/logs/catalina.out\* to check for any errors. On startup log4j should begin appending, and there would be an indication as to the application's database connection status. 

EXAMPLE: 
```
2017-01-30 16:53:33 DEBUG MobileTrackerContextListener:31 - Logging with log4j settings started, see WEB-INF/classes/log4j.properties to modify.
2017-01-30 16:53:33 DEBUG MobileTrackerContextListener:34 - Loading properties file..
2017-01-30 16:53:33 DEBUG MobileTrackerContextListener:56 - CHECKING DB CONNECTION
Mon Jan 30 16:53:33 MST 2017 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
2017-01-30 16:53:33 DEBUG MobileTrackerContextListener:62 - Connection test success
```

Success! You now have a running server to log and display Gps Coordinates. See usages section for information on how to use the application. 

# USAGE

### POSTING / SAVING GPS COORDINATES TO SERVER

Navigate to the default context http://{hostname}:{8080}/MobileTracker to gain access Login Screen: 

![alt tag](http://i63.tinypic.com/2s6oqr4.jpg)

__NOTE:__ *If an error with required libraries is logged in /var/log/tomcat8/catalina.out copy libraries from /var/lib/tomcat8/webapps/MobileTracker/WEB-INF/lib/\* to $CATALINA_HOME/lib/* 

Login using the credentials provided in step 2 of the setup to gain access to the GPS Query page: 

![alt_tag](http://i68.tinypic.com/2yuka3l.jpg)


