/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
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
import session.PostSessionBeanLocal;
import session.ThreadSessionBeanLocal;
import session.UserSessionBeanLocal;

/**
 *
 * @author jiajun
 */
@Path("posts")
public class PostResource {

    @EJB
    private PostSessionBeanLocal postSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("id") Long pLongId) {
        try {
            Post post = postSessionBeanLocal.getPost(pLongId);
            return Response.status(200).entity(post).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Post Not Found!")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    //how do I then link the post to the user and thread? 
    @POST
    @Path("/{userId}/{threadId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post p, @PathParam("userId") Long uId, @PathParam("threadId") Long tId) {
        try {
            UserEntity user = userSessionBeanLocal.getUser(uId);
            p.setUser(user);
            postSessionBeanLocal.createPost(p, tId);
            return Response.status(200).entity(p).type(MediaType.APPLICATION_JSON).build();
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
    public Response deletePost(@PathParam("id") Long pId) {
        try {
            postSessionBeanLocal.deletePost(pId);
            return Response.status(200).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Post not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPost(@PathParam("id") Long pId, Post p) {
        p.setId(pId);
        try {
            postSessionBeanLocal.updatePost(p);
            return Response.status(200).entity(p).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Post Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("{id}/threads")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThread(@PathParam("id") Long pId) {
        try {
            ThreadEntity t = postSessionBeanLocal.getThread(pId);
            return Response.status(200).entity(t).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
}
