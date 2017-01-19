# MobileTrackerJ
Web application for storing and displaying GPS coordinates. Location data is sent via POST by Palantir GPS mobile application. Uses Google Maps javascript API so an API key is needed to operate. 
# Usage
Make sure project is setup to use maven build. Build war file with maven and deploy to tomcat web servlet container (I.E. $CATALINA_HOME/webapps/*.war). Ensure unpackwar=true in server.xml (this should be default). 
Startup tomcat and navigate to the default context http://{hostname}:{8080}/MobileTracker to gain access to the GPS query interface. 