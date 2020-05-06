# complex-java-programming-lab-nr2
Lab nr 2 in the course Complex Java-programming, ITHS  
MySQL Connector/J version: 8.0.18

## Insomnia json file
Included in the project is a json file called 'insomnia_school_project.json'. This file can be imported into Insomnia to get all endpoints. 

## To deploy
Command line: wildfly:undeploy clean:clean wildfly:deploy

## Endpoints
### add student
Address: http://localhost:8080/school/student/addstudent  
HTTP method: POST  
Body: Json, in the format:
<pre>
{
	"firstName": "anders",
	"lastName": "andersson",
	"email": "anders@gmail.com"
}
</pre>
A Json object representing the student will be returned, if all three Json attribute–value pairs are present and not blank/empty.

### add teacher
Address: http://localhost:8080/school/teacher/addteacher  
HTTP method: POST  
Body: Json, in the format:
<pre>
{
	"firstName": "bertil",
	"lastName": "bengtsson",
	"email": "bertil@gmail.com"
}
</pre>
A Json object representing the teacher will be returned, if all three Json attribute–value pairs are present and not blank/empty.

### get all students
Address: http://localhost:8080/school/student/getallstudents  
HTTP method: GET  
Body: None  
An array of Json objects representing the students will be returned.

### get all teachers
Address: http://localhost:8080/school/teacher/getallteachers  
HTTP method: GET  
Body: None  
An array of Json objects representing the teachers will be returned.

### delete student
Address: http://localhost:8080/school/student/deletestudent/{email}  
HTTP method: DELETE  
Body: None  
Example:
<pre>
http://localhost:8080/school/student/deletestudent/anders@gmail.com
</pre>
A message (mediatype: text/plain) indicating success or failure will be returned.

### delete teacher
Address: http://localhost:8080/school/teacher/deleteteacher/{email}  
HTTP method: DELETE  
Body: None  
Example:
<pre>
http://localhost:8080/school/teacher/deleteteacher/bertil@gmail.com
</pre>
A message (mediatype: text/plain) indicating success or failure will be returned.

### update student
Address: http://localhost:8080/school/student/updatestudent?firstname={firstName}&lastname={lastName}&email={email}  
HTTP method: PUT  
Body: None  
Example:
<pre>
http://localhost:8080/school/student/updatestudent?firstname=peter&lastname=karlsson&email=anders@gmail.com
</pre>
A Json object representing the updated student will be returned, if all three query parameter attribute–value pairs are present and not blank/empty.

### update teacher
Address: http://localhost:8080/school/teacher/updateteacher?firstname={firstName}&lastname={lastName}&email={email}  
HTTP method: PUT  
Body: None  
Example:
<pre>
http://localhost:8080/school/teacher/updateteacher?firstname=johan&lastname=gustavsson&email=bertil@gmail.com
</pre>
A Json object representing the updated teacher will be returned, if all three query parameter attribute–value pairs are present and not blank/empty.

### update student first name
Address: http://localhost:8080/school/student/updatestudentfirstname  
HTTP method: PATCH  
Body: Json, in the format:
<pre>
{
	"firstName": "daniel",
	"lastName": "andersson",
	"email": "anders@gmail.com"
}
</pre>
A Json object representing the updated student will be returned, if all three Json attribute–value pairs are present and not blank/empty.

### update teacher first name
Address: http://localhost:8080/school/teacher/updateteacherfirstname  
HTTP method: PATCH  
Body: Json, in the format:
<pre>
{
	"firstName": "erik",
	"lastName": "bengtsson",
	"email": "bertil@gmail.com"
}
</pre>
A Json object representing the updated teacher will be returned, if all three Json attribute–value pairs are present and not blank/empty.

### find students by last name
Address: http://localhost:8080/school/student/findstudentsbylastname/{lastName}  
HTTP method: GET  
Body: None  
Example:
<pre>
http://localhost:8080/school/student/findstudentsbylastname/andersson
</pre>
An array of Json objects representing the students with the requested last name will be returned. If no students have the requested last name, an empty array will be returned.

### find teachers by last name
Address: http://localhost:8080/school/teacher/findteachersbylastname/{lastName}  
HTTP method: GET  
Body: None  
Example:
<pre>
http://localhost:8080/school/teacher/findteachersbylastname/bengtsson
</pre>
An array of Json objects representing the teachers with the requested last name will be returned. If no teachers have the requested last name, an empty array will be returned.

### find student by email
Address: http://localhost:8080/school/student/findstudentbyemail/{email}  
HTTP method: GET  
Body: None  
Example:
<pre>
http://localhost:8080/school/student/findstudentbyemail/anders@gmail.com
</pre>
A Json object representing the student with the requested email will be returned, or an error message if no student has the requested email.

### find teacher by email
Address: http://localhost:8080/school/teacher/findteacherbyemail/{email}  
HTTP method: GET  
Body: None  
Example:
<pre>
http://localhost:8080/school/teacher/findteacherbyemail/bertil@gmail.com
</pre>
A Json object representing the teacher with the requested email will be returned, or an error message if no teacher has the requested email.

### add subject
Address: http://localhost:8080/school/subject/addsubject  
HTTP method: POST  
Body: Json, in the format:
<pre>
{
	"title": "matematik"
}
</pre>
A Json object representing the subject will be returned, if the title attribute–value pair is present and not blank/empty.

### get all subjects
Address: http://localhost:8080/school/subject/getallsubjects  
HTTP method: GET  
Body: None  
An array of Json objects representing the subjects will be returned.

### delete subject
Address: http://localhost:8080/school/subject/deletesubject/{title}  
HTTP method: DELETE  
Body: None  
Example:
<pre>
http://localhost:8080/school/subject/deletesubject/matematik
</pre>
A message (mediatype: text/plain) indicating success or failure will be returned.

### find subject by title
Address: http://localhost:8080/school/subject/findsubjectbytitle/{title}  
HTTP method: GET  
Body: None  
Example:
<pre>
http://localhost:8080/school/subject/findsubjectbytitle/matematik
</pre>
A Json object representing the subject with the requested title will be returned, or an error message if no subject has the requested title.

### add student to subject
Address: http://localhost:8080/school/subject/addstudenttosubject?studentemail={email}&title={title}  
HTTP method: PATCH  
Body: None   
Example:
<pre>
http://localhost:8080/school/subject/addstudenttosubject?studentemail=anders@gmail.com&title=matematik
</pre>
A Json object representing the subject with the student added, if both the student and the subject is present in the database.  

### add teacher to subject
Address: http://localhost:8080/school/subject/addteachertosubject?teacheremail={email}&title={title}  
HTTP method: PATCH  
Body: None   
Example:
<pre>
http://localhost:8080/school/subject/addteachertosubject?teacheremail=bertil@gmail.com&title=matematik
</pre>
A Json object representing the subject with the teacher added, if both the teacher and the subject is present in the database.

### remove student from subject
Address: http://localhost:8080/school/subject/removestudentfromsubject?studentemail={email}&title={title}  
HTTP method: DELETE  
Body: None  
Example:
<pre>
http://localhost:8080/school/subject/removestudentfromsubject?studentemail=anders@gmail.com&title=matematik
</pre>
A message (mediatype: text/plain) indicating if the student was successfully removed from the subject or not will be returned.

### remove teacher from subject
Address: http://localhost:8080/school/subject/removeteacherfromsubject?title={title}  
HTTP method: DELETE  
Body: None  
Example:
<pre>
http://localhost:8080/school/subject/removeteacherfromsubject?title=matematik
</pre>
A message (mediatype: text/plain) indicating if the teacher was successfully removed from the subject or not will be returned.