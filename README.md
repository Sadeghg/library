# Start The Library Project

first you need to run build the executable jar file
`mvn clean package`

then build the backend application using the docker build command
`docker build -t library:starter .`

you can edit the volumes under the library backend service by replacing the default volume path to desired one

with docker compose run the containers for library backend and Mysql Database

`docker compose up`

with  the startup of the application flyway scripts will run to create tables and populate the data, now you can test the endpoints