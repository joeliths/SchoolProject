# End-points

## STUDENT CONTROLLER

### Add new student
##### Method: POST
##### url: http://localhost:8080/school/student/add
##### Body: 
{
	"forename": "Katten",
	"lastname": "Gunnarsson",
	"email": "katt@email.com"
}


### Get all students
##### Method: GET
##### url:http://localhost:8080/school/student

### Find student by name
##### Method: GET
##### url: http://localhost:8080/school/student/find/nam
##### @QueryParam: forename

### Find student by email
##### Method: GET
##### url: http://localhost:8080/school/student/find/email
##### @QueryParam: email

### Update student by email
##### Method: PUT
##### url: http://localhost:8080/school/student
##### @QueryParam: forename, lastname, email

### Delete student
##### Method: DELETE
##### url: http://localhost:8080/school/student/delete/{email}
##### @QueryParam: email

-----------------------------------------------------------------------------------------------
## TEACHER CONTROLLER

### Add new teacher
##### Method: POST
##### url: http://localhost:8080/school/student
##### Body: 
{

	"forename": "Erik",
	"lastname": "Gunnarsson",
	"email": "Erik.gunnarsson@email.com"
}

### Get all teachers
##### Method: GET
##### url: http://localhost:8080/school/teacher

### Find teacher by email
##### Method: GET
##### url: http://localhost:8080/school/teacher/find
##### @QueryParam: email



### Delete teacher
##### Method: DELETE
##### url: http://localhost:8080/school/student/delete/{email}
##### @QueryParam: email


-----------------------------------------------------------------------------------------
##SUbJECT CONTROLLER

### Add new subject
##### Method: POST
##### url: http://localhost:8080/school/subject
##### Body: 
{
	"subject": "Java"
}
### Add student to subject
##### Method: POST
##### url: http://localhost:8080/school/subject/add/student
##### @QueryParam: student (email), subject (title)

### Add teacher to subject
##### Method: POST
##### url: http://localhost:8080/school/subject/add/teacher
##### @QueryParam: teacher (email), subject (title)

### Get all subjects
##### Method: GET
##### url: http://localhost:8080/school/subject

### Find subject by name
##### Method: GET
##### url: http://localhost:8080/school/subject/find
##### @QueryParam: title

### Delete subject
##### Method: DELETE
##### url: http://localhost:8080/school/subject
##### @QueryParam: title


## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.



