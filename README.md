# Schoolproject

Small project for teaching purposes.

* Wildfly
* JEE 8 
* Java 11
* Git
* Maven
* MySQL

**End-Points**
*Get
-Find all entitys
URL:http://localhost:8080/school/student

-Find an entity by email.
URL:http://localhost:8080/school/student/find/email
-QueryParams: email

-Find an entity be name
URL:http://localhost:8080/school/student/find/name
-QueryParams: forename

*PUT
-Send a new forename and lastname as queryparams together with email, email
will identify the entity.
-URL: http://localhost:8080/school/student
-QueryParams: forname,lastname, email

*Delete
-Send email as payload to delete a user. 
-URL: http://localhost:8080/school/student
-QueryParams: email

*POST
-Add a new entity
-Header: Content-Type : application/json
URL: http://localhost:8080/school/student/add
BODY: 
{
	"forename": "Lars",
	"lastname": "Gunnarsson",
	"email": "joel.p.gunnarsson"
}


## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.



