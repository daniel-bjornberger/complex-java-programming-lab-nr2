package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.exceptions.DuplicateEmailException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.LastNameAndEmailNotFoundException;
import se.alten.schoolproject.exceptions.MissingPersonValueException;
import se.alten.schoolproject.model.PersonModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/teacher")
public class TeacherController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Path("/getallteachers")
    @Produces({"application/JSON"})
    public Response getAllTeachers() {

        try {
            List teacherModelList = schoolAccessLocal.listAllTeachers();
            return Response.ok(teacherModelList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }


    @POST
    @Path("/addteacher")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response addTeacher(String teacherJsonString) {

        try {
            PersonModel personModel = schoolAccessLocal.addTeacher(teacherJsonString);
            return Response.ok(personModel).build();
        } catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (DuplicateEmailException e) {
            return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @DELETE
    @Path("deleteteacher/{email}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/plain"})
    public Response deleteTeacher(@PathParam("email") String email) {

        try {
            schoolAccessLocal.deleteTeacher(email);
            return Response.ok().type(MediaType.TEXT_PLAIN)
                    .entity("The teacher was deleted from the database.").build();
        } catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @PUT
    @Path("updateteacher")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response updateTeacher(@QueryParam("firstname") String firstName, @QueryParam("lastname") String lastName, @QueryParam("email") String email) {

        try {
            PersonModel personModel = schoolAccessLocal.updateTeacher(firstName, lastName, email);
            return Response.ok(personModel).build();
        } catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @PATCH
    @Path("updatefirstname")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response updateFirstName(String teacherJsonString) {

        try {
            PersonModel personModel = schoolAccessLocal.updateTeacherFirstName(teacherJsonString);
            return Response.ok(personModel).build();
        } catch (MissingPersonValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (LastNameAndEmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @GET
    @Path("findteachersbylastname/{lastname}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response findTeachersByLastName(@PathParam("lastname") String lastName) {

        try {
            List teacherModelList = schoolAccessLocal.findTeachersByLastName(lastName);
            return Response.ok(teacherModelList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

}
