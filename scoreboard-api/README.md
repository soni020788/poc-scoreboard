## POC

## Scoreboard rest API
This provides rest service for Scoreboard

## Development requirements
1. Java 8
2. Maven 3
3. git
4. heroku

## How to run scoreboard-api using embed tomcat

1. import maven project form IDE (IntelliJ Idea)
2. run file "StartTomcat.java"
3. Application will run on port 9090

## How to run scoreboard-api using heroku

#Deployment with the Heroku CLI

1. build a project uring maven command "mvn clean install" or "mvn package"
2. run "heroku plugins:install java"
3. run "heroku war:deploy target\scoreboard-api-1.0.war  --app scoreboard-rest-api"
4. run "heroku ps:scale web=1"
