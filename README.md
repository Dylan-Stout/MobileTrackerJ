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



Make sure project is setup to use maven build. Build war file with maven and deploy to tomcat web servlet container (I.E. $CATALINA_HOME/webapps/*.war). Ensure unpackwar=true in server.xml (this should be default). 
Startup tomcat and navigate to the default context http://{hostname}:{8080}/MobileTracker to gain access Login Screen: 

![alt tag](http://i63.tinypic.com/2s6oqr4.jpg)


