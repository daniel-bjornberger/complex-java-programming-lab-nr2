package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;
import se.alten.schoolproject.exceptions.MissingPersonValueException;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Path("/getallstudents")
    @Produces({"application/JSON"})
    public Response getAllStudents() {
        try {
            List<?> studentModelList = schoolAccessLocal.listAllStudents();
            return Response.ok(studentModelList).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


    @POST
    @Path("/addstudent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response addStudent(String studentJsonString) {
        try {
            StudentModel studentModel = schoolAccessLocal.addStudent(studentJsonString);
            return Response.ok(studentModel).build();
        }
        catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (DuplicateEmailException e) {
            return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @DELETE
    @Path("deletestudent/{email}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/plain"})
    public Response deleteStudent(@PathParam("email") String email) {
        try {
            schoolAccessLocal.deleteStudent(email);
            return Response.ok().type(MediaType.TEXT_PLAIN)
                    .entity("The student was deleted from the database.").build();
        }
        catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("updatestudent")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response updateStudent(@QueryParam("firstname") String firstName, @QueryParam("lastname") String lastName, @QueryParam("email") String email) {
        try {
            StudentModel studentModel = schoolAccessLocal.updateStudent(firstName, lastName, email);
            return Response.ok(studentModel).build();
        }
        catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
    }


    @PATCH
    @Path("updatestudentfirstname")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response updateStudentFirstName(String studentJsonString) {
        try {
            StudentModel studentModel = schoolAccessLocal.updateStudentFirstName(studentJsonString);
            return Response.ok(studentModel).build();
        }
        catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (LastNameAndEmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("findstudentsbylastname/{lastname}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response findStudentsByLastName(@PathParam("lastname") String lastName) {
        try {
            List<?> studentModelList = schoolAccessLocal.findStudentsByLastName(lastName);
            return Response.ok(studentModelList).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

}
