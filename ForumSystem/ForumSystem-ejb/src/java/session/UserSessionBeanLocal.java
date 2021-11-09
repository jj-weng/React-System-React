/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Post;
import entity.UserEntity;
import entity.ThreadEntity;
import exception.InvalidLoginException;
import exception.UserNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Local
public interface UserSessionBeanLocal {

    public UserEntity getUser(Long uId) throws NoResultException;

    public void createUser(UserEntity u);

    public void deleteUser(Long uId) throws NoResultException;

    public void updateUser(UserEntity user) throws NoResultException;

    public UserEntity retrieveUserByName(String name) throws UserNotFoundException;

    public UserEntity loginUser(String name, String password) throws InvalidLoginException, UserNotFoundException;

//    public List<Post> viewPostsOfUser(String name) throws UserNotFoundException;
//
//    public List<ThreadEntity> viewThreadsOfUser(String name) throws UserNotFoundException;

    public List<UserEntity> getAllUsers();

//    public UserEntity getUserEntityFromPostId(Long pId);
//
//    public UserEntity getUserEntityFromThreadId(Long tId);
}
