package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@NoArgsConstructor
@Path("/subject")
public class SubjectController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response listSubjects() {
        try {
            return sal.listAllSubjects();
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/find")
    @Produces({"application/JSON"})
    public Response findSubjectByName(@QueryParam("title") String subjectTitle) {
        try {
            return sal.findSingelSubjectByname(subjectTitle);
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Produces({"application/JSON"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubject(String subject) {
        try {
            return sal.addSubject(subject);
        } catch (Exception e ) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("/add/student")
    @Produces({"application/JSON"})
    public Response addStudentToSubject(@QueryParam("student") String studentEmail,@QueryParam("subject") String subject) {
        try {
            return sal.addStudentToSubject(studentEmail,subject);
        } catch (Exception e ) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("/add/teacher")
    @Produces({"application/JSON"})
    public Response addTeachertToSubject(@QueryParam("email") String teacherEmail,@QueryParam("subject") String subjectTitle) {
        try {
            return sal.addTecherToSubject(teacherEmail,subjectTitle);
        } catch (Exception e ) {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Produces({"application/JSON"})
    public Response deleteUser( @QueryParam("title") String title) {
        try {
            return sal.removeSubject(title);
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
