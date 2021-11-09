/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
import enumeration.UserStatus;
import exception.InvalidLoginException;
import exception.UserNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserSessionBeanLocal;

/**
 * REST Web Service
 *
 * @author jiajun
 */
@Path("users")
public class UserResource {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAllUsers() throws NoResultException {
        List<UserEntity> listOfUsers = userSessionBeanLocal.getAllUsers();
//        for (UserEntity user : listOfUsers) {
//            nullifyUser(user);
//        }

        return listOfUsers;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long uLongId) {
        try {
            UserEntity user = userSessionBeanLocal.getUser(uLongId);
            //nullifyUser(user);
            return Response.status(200).entity(user).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getUser

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserEntity u) {
        try {
            userSessionBeanLocal.createUser(u);
            return Response.status(200).entity(u).type(MediaType.APPLICATION_JSON).build();
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
    public Response deleteUser(@PathParam("id") Long uId) {
        try {
            userSessionBeanLocal.deleteUser(uId);
            return Response.status(200).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@PathParam("id") Long uId, UserEntity u) {
        u.setId(uId);
        try {
            userSessionBeanLocal.updateUser(u);
            return Response.status(200).entity(u).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editUser

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUserByName(@QueryParam("name") String name) throws UserNotFoundException {
        if (name != null) {
            UserEntity user = userSessionBeanLocal.retrieveUserByName(name);
            //nullifyUser(user);
            return Response.status(200).entity(user).type(MediaType.APPLICATION_JSON).build();
        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    //getUserEntityFromPostId
    //getUserEntityFromThreadId
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(UserEntity userEntity) {
        String password = userEntity.getPassword();
        String username = userEntity.getName();
        System.out.println(password + " " + username);
        try {
            userEntity = userSessionBeanLocal.loginUser(username, password);
            return Response.status(200).entity(userEntity).build();

        } catch (InvalidLoginException | UserNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Login failed")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/block")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response blockUser(UserEntity userEntity) {
//        UserEntity user = userSessionBeanLocal.getUser(uId);
        userEntity.setUserStatus(UserStatus.BLOCKED);
        try {
            userSessionBeanLocal.updateUser(userEntity);
            return Response.status(200).entity(userEntity).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @PUT
    @Path("/unblock")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unblockUser(UserEntity userEntity) {
        //UserEntity user = userSessionBeanLocal.getUser(uId);
        userEntity.setUserStatus(UserStatus.UNBLOCKED);
        try {
            userSessionBeanLocal.updateUser(userEntity);
            return Response.status(200).entity(userEntity).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

//    private void nullifyUser(UserEntity user) {
//        for (Post post : user.getPosts()) {
//            post.setUser(null);
//        }
//
//        for (ThreadEntity thread : user.getThreads()) {
//            thread.setUserEntity(null);
//        }
//    }
}
