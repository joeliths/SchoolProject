package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CONFLICT;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response showStudents() {
        try {
            return sal.listAllStudents();

        } catch ( Exception e ) {
            System.out.println("Enter catch ShowStudents Controller");
            return Response.status(CONFLICT).build();
        }
    }

    @GET
    @Path("/find/name")
    @Produces({"application/JSON"})
    public Response findStudentByName(@QueryParam("forename") String forename) {
        try {
            return sal.findStudentByName(forename);

        } catch ( Exception e ) {
            return Response.status(CONFLICT).build();
        }
    }

    @GET
    @Path("/find/email")
    @Produces({"application/JSON"})
    public Response findStudentByEmail(@QueryParam("email") String email) {
        try {
            return sal.findStudentByEmail(email);

        } catch ( Exception e ) {
            return Response.status(422).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    /**
     * JavaDoc
     */
    public Response addStudent(String studentModel) {
        try {
            return sal.addStudent(studentModel);

        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/delete")
    @Produces({"application/JSON"})
    public Response deleteUser( @QueryParam("email") String email) {
        try {
            return sal.removeStudent(email);
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Produces({"application/JSON"})
    public Response updateStudent( @QueryParam("forename") String forename, @QueryParam("lastname") String lastname, @QueryParam("email") String email) {
        try {
             return sal.updateStudent(forename, lastname, email);
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        }

    @PATCH
    @Produces({"application/JSON"})
    public void updatePartialAStudent(String studentModel) {
        sal.updateStudentPartial(studentModel);
    }
}
