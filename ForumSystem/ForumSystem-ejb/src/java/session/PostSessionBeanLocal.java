/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Post;
import entity.ThreadEntity;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Local
public interface PostSessionBeanLocal {

    public Post getPost(Long pId) throws NoResultException;

    public void createPost(Post p);

    public void createPost(Post post, Long tId) throws NoResultException;

    public void deletePost(Long pId) throws NoResultException;

    public void updatePost(Post post) throws NoResultException;

    public ThreadEntity getThread(Long pId);
}
