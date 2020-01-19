package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.exceptions.*;
import se.alten.schoolproject.model.PersonModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/subject")
public class SubjectController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Path("/getallsubjects")
    @Produces({"application/JSON"})
    public Response getAllSubjects() {

        try {
            List subjectModelList = schoolAccessLocal.listAllSubjects();
            return Response.ok(subjectModelList).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }


    @POST
    @Path("/addsubject")
    @Produces({"application/JSON"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubject(String subjectJsonString) {

        /*try {
            SubjectModel subjectModel = sal.addSubject(subject);
            return Response.ok(subjectModel).build();
        }
        catch (Exception e) {
            return Response.status(404).build();
        }*/

        try {
            SubjectModel subjectModel = schoolAccessLocal.addSubject(subjectJsonString);
            return Response.ok(subjectModel).build();
        }
        catch (MissingTitleValueException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (DuplicateTitleException e) {
            return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @DELETE
    @Path("deletesubject/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/plain"})
    public Response deleteSubject(@PathParam("title") String title) {

        try {
            schoolAccessLocal.deleteSubject(title);
            return Response.ok().type(MediaType.TEXT_PLAIN)
                    .entity("The subject was deleted from the database.").build();
        }
        catch (TitleNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @GET
    @Path("findsubjectbytitle/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response findSubjectByTitle(@PathParam("title") String title) {

        try {
            //List studentModelList = schoolAccessLocal.findStudentsByLastName(lastName);
            SubjectModel subjectModel = schoolAccessLocal.findSubjectByTitle(title);
            return Response.ok(subjectModel).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @PATCH
    @Path("addstudenttosubject")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response addStudentToSubject(@QueryParam("studentemail") String studentEmail,
                                        @QueryParam("title") String title) {

        try {
            SubjectModel subjectModel = schoolAccessLocal.addStudentToSubject(studentEmail, title);
            return Response.ok(subjectModel).build();
        }
        catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (TitleNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }


    @PATCH
    @Path("addteachertosubject")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"application/JSON"})
    public Response addTeacherToSubject(@QueryParam("teacheremail") String teacherEmail,
                                        @QueryParam("title") String title) {

        try {
            SubjectModel subjectModel = schoolAccessLocal.addTeacherToSubject(teacherEmail, title);
            return Response.ok(subjectModel).build();
        }
        catch (EmailNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }
        catch (TitleNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }

}


// Lade till deleteSubject.
