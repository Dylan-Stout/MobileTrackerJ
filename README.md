# MobileTrackerJ
Web application for storing and displaying GPS coordinates. Location data is sent via POST by Palantir GPS mobile application. Uses Google Maps javascript API so an API key is needed to operate. 
# Setup
### Create Databases
1.) Setup and create MySql Database named 'location' from SQL scripts in /res/ folder to be used by web application. This will be used for user login and data. 

### Create Users
2.) Manually create user hash by running main method in com.mobile.core.SecurtyHelper generate a user's insert statement by passing two arguments [username] and [password]. Example: java com.mobile.core.SecurityHelper user1 password1

__OUTPUT:__
> Generated query: INSERT INTO USER(USERNAME, USERIP, USERHASH) VALUES ('user1', '0.0.0.0', 'sha1:64000:18:BYGHYPBLoxgYO0xkG/bM9ajv6OesWvaa:ppEl8AvFHvfwZWZro4hMsnYA');

Run this query against the 'location' database. NOTE: This is done manually for security reasons until a secure user generation algorithm can be implemented (0.8.1b). 

### Setup Server
3.) After creating create a war file from source with Maven. Make sure project is setup to use maven build. Build war file with *maven clean install* and deploy to a tomcat web servlet container (I.E. $CATALINA_HOME/webapps/\*.war). Ensure unpackwar=true in tomcat's  $CATALINA_HOME/conf/server.xml (this should be default). 


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




Startup tomcat via $CATALINA_HOME/bin/startup.sh and navigate to the default context http://{hostname}:{8080}/MobileTracker to gain access Login Screen: 

![alt tag](http://i63.tinypic.com/2s6oqr4.jpg)

__NOTE:__ *If an error with required libraries is logged in /var/log/tomcat
