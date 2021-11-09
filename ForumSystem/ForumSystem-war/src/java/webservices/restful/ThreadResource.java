/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Forum;
import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
import enumeration.ThreadOpenClose;
import exception.ThreadDeletionException;
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
import session.ThreadSessionBeanLocal;
import session.UserSessionBeanLocal;

/**
 *
 * @author jiajun
 */
@Path("threads")
public class ThreadResource {

    @EJB
    private ThreadSessionBeanLocal threadSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThread(@PathParam("id") Long tLongId) {
        try {
            ThreadEntity t = threadSessionBeanLocal.getThread(tLongId);
            return Response.status(200).entity(t).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Thread Not Found!")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    //how do I then link the thread to forum and user?
    @POST
    @Path("/{userId}/{forumId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createThread(ThreadEntity t, @PathParam("userId") Long uId, @PathParam("forumId") Long fId) {
        try {
            UserEntity user = userSessionBeanLocal.getUser(uId);
            t.setUserEntity(user);
            threadSessionBeanLocal.createThread(t, fId);
            return Response.status(200).entity(t).type(MediaType.APPLICATION_JSON).build();
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
    public Response deleteThread(@PathParam("id") Long tId) throws ThreadDeletionException {
        try {
            threadSessionBeanLocal.deleteThread(tId);
            return Response.status(200).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Thread not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editThread(@PathParam("id") Long tId, ThreadEntity t) {
        t.setId(tId);
        try {
            threadSessionBeanLocal.updateThread(t);
            return Response.status(200).entity(t).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Thread Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/{thread_id}/posts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPostToThread(@PathParam("thread_id") Long tId, Post p) {
        try {
            threadSessionBeanLocal.addPostToThread(tId, p);
            ThreadEntity t = threadSessionBeanLocal.getThread(tId);
            return Response.status(200).entity(t).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Thread Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/close")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response closeThread(ThreadEntity threadEntity) {
        threadEntity.setThreadOpenClose(ThreadOpenClose.CLOSED);
        try {
            threadSessionBeanLocal.updateThread(threadEntity);
            return Response.status(200).entity(threadEntity).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/open")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response openThread(ThreadEntity threadEntity) {
        //UserEntity user = userSessionBeanLocal.getUser(uId);
        threadEntity.setThreadOpenClose(ThreadOpenClose.OPENED);
        try {
            threadSessionBeanLocal.updateThread(threadEntity);
            return Response.status(200).entity(threadEntity).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("{id}/forums")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getForum(@PathParam("id") Long tId) {
        try {
            Forum f = threadSessionBeanLocal.getForum(tId);
            return Response.status(200).entity(f).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }
}
