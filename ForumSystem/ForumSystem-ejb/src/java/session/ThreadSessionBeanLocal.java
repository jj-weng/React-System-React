/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Forum;
import entity.Post;
import entity.ThreadEntity;
import exception.ThreadDeletionException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Local
public interface ThreadSessionBeanLocal {

    public entity.ThreadEntity getThread(Long tId) throws NoResultException;

    public void createThread(entity.ThreadEntity t);

    public void createThread(ThreadEntity t, Long fId) throws NoResultException;

    public void deleteThread(Long tId) throws NoResultException, ThreadDeletionException;

    public ThreadEntity getThreadFromPostId(Long pId);

    public Forum getForum(Long tId);

    public void updateThread(entity.ThreadEntity thread) throws NoResultException;

    //public entity.ThreadEntity retrieveThreadById(Long id) throws NoResultException;
    public void addPostToThread(Long tId, Post p) throws NoResultException;

    public void deletePostFromThread(Long pId) throws NoResultException;

    public List<Post> viewPostsInThread(Long tId) throws NoResultException;

    public Post getPostInThread(Long tId, Long pId) throws NoResultException;

    public List<ThreadEntity> getAllThreadsOfForum(Forum fId);

}
