# spring-restful-microservices

#### Create a MySQL Container for Social Media Application
``$ docker run --name mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=app_user -e MYSQL_PASSWORD=admin -e MYSQL_DATABASE=social_media_db -p 3306:3306 -d mysql:latest``

Check running containers: ``$ docker container ls``

Access MySQL Container: ``$ docker exec -it mysql bash``

Access MySQL Shell: ``$ mysql -u root -p``

Access MySQL Container and Shell in one command: ``$ docker exec -it mysql mysql -u root -p``

Create new database and tables: ``mysql -u root -p``

In MySQL shell, run the following commands to create a database and a sample table for the application

#### Example:

``mysql> CREATE DATABASE social_media_db;`` 

``mysql> USE social_media_db;`` 

``mysql> CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(100));``

#### Useful Docker Commands

Stop MySQL Container: ``$ docker stop mysql``

Start MySQL Container: ``$ docker start mysql``

Show all containers (including stopped ones): ``$ docker container ls -a``