# Deploy ARSLab Services with Heroku.

The application is live at https://arslab-services.herokuapp.com

As there's no homepage, try https://arslab-services.herokuapp.com/Accounts to see something useful.

The Procfile, pom.xml, and system.properties in the root of this repository have been configured so that Heroku is able to build and deploy the sim.services package.

The repository is configured to automatically deploy when new changes are merged into the master branch. 

**Next steps:**

Need to configure Heroku Postgres DB to be used with these services. Currently, they're still connected with Bruno's AWS db.

Shouldn't be too difficult, as Heroku will pre-configure environment variables for us. We just need to modify the code so that it uses those environment variables as the DB host/port/user.
