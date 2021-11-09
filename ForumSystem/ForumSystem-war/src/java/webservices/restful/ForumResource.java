/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Forum;
import entity.ThreadEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.ForumSessionBeanLocal;

/**
 *
 * @author jiajun
 */
@Path("forums")
public class ForumResource {

    @EJB
    private ForumSessionBeanLocal forumSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Forum> getAllForums() throws NoResultException {
        List<Forum> listOfForums = forumSessionBeanLocal.getAllForums();
        return listOfForums;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForum(@PathParam("id") Long fLongId) {
        try {
            Forum forum = forumSessionBeanLocal.getForum(fLongId);
            return Response.status(200).entity(forum).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Forum Not Found!")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createForum(Forum f) {
        try {
            forumSessionBeanLocal.createForum(f);
            return Response.status(200).entity(f).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForum(@PathParam("id") Long fId) {
        try {
            forumSessionBeanLocal.deleteForum(fId);
            return Response.status(200).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Forum not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editForum(@PathParam("id") Long fId, Forum f) {
        f.setId(fId);
        try {
            forumSessionBeanLocal.updateForum(f);
            System.out.println("edit forum ***********");
            return Response.status(200).entity(f).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Forum Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/{forum_id}/threads")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addThreadToForum(@PathParam("forum_id") Long fId, ThreadEntity t) {
        try {
            forumSessionBeanLocal.addThreadToForum(fId, t);
            Forum f = forumSessionBeanLocal.getForum(fId);
            return Response.status(200).entity(f).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Forum Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

}
