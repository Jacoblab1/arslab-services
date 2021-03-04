# Deploy ARSLab Services with Heroku.

The application is live at https://arslab-services.herokuapp.com

The Procfile, pom.xml, and system.properties in the root of this repository have been configured so that Heroku is able to build and deploy the sim.services package.

The repository is configured to automatically deploy when new changes are merged into the master branch. 


# Code base setup
1.	Go to marketplace to download the EGit for Eclispe
2.	Install JRE
3.	Go to Apache Tomcat and download it for windows/mac
4.	In the apache tomcat folder, go to bin and grant access for all the .bat files. Such as the Catalina.bat, ciphers.bat, configtest.bat, digest.bat, makebase.bat, setclasspath.bat, shutdown.bat, startup.bat, tool-wrapper.bat and version.bat


5.	Execute tomcat start script
6.	Import the project from git url. Setup username and password.
File -> open Projects from File System
7.	select Windows Preferences Maven and enable the Download repository index updates on startup option.
8.	Restart eclipse
9.	Setup maven dependencies

