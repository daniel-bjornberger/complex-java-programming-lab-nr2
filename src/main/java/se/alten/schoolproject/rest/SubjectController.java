package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.exceptions.DuplicateTitleException;
import se.alten.schoolproject.exceptions.EmailNotFoundException;
import se.alten.schoolproject.exceptions.MissingTitleValueException;
import se.alten.schoolproject.exceptions.TitleNotFoundException;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return Response.status(404).build();
        }*/

        try {
            SubjectModel subjectModel = schoolAccessLocal.addSubject(subjectJsonString);
            return Response.ok(subjectModel).build();
        } catch (MissingTitleValueException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (DuplicateTitleException e) {
            return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    // Använd inte NOT_ACCEPTABLE!


    @DELETE
    @Path("deletesubject/{title}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces({"text/plain"})
    public Response deleteSubject(@PathParam("title") String title) {

        try {
            schoolAccessLocal.removeSubject(title);
            return Response.ok().type(MediaType.TEXT_PLAIN)
                    .entity("The subject was deleted from the database.").build();
        } catch (TitleNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage()).build();
        }

    }

}


// Lade till deleteSubject.
