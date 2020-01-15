package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

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
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response listTeachers() {
        try {
            return sal.listAllTeachers();
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/find")
    @Produces({"application/JSON"})
    public Response findTeacherByEmail(@QueryParam("email") String teacherEmail){
        try {
            return sal.findTeacherByEmail(teacherEmail);
        } catch (Exception e){
            return Response.status(404).build();
        }

    }

    @POST
    @Produces({"application/JSON"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeacher(String teacher) {
        try {
           return sal.addTeacher(teacher);

        } catch (Exception e ) {
            System.out.println("Enter addTeacher controller catch "+e.getStackTrace());
            return Response.status(404).build();
        }
    }

    @DELETE
    @Produces({"application/JSON"})
    public Response deleteUser( @QueryParam("email") String email) {
        try {
            return sal.removeTeacher(email);
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
