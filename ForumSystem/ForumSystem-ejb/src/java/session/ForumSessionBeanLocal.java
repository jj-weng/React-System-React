/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Forum;
import java.util.List;
import javax.ejb.Local;
import entity.ThreadEntity;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Local
public interface ForumSessionBeanLocal {

    public void deleteForum(Long fId) throws NoResultException;

    public Forum getForum(Long fId) throws NoResultException;

    public void updateForum(Forum forum) throws NoResultException;

    public void createForum(Forum f);

    public Forum getForumFromThreadId(Long tId);

//    public Forum retrieveForumById(Long id) throws NoResultException;

    public void addThreadToForum(Long fId, entity.ThreadEntity t) throws NoResultException;

    public void deleteThreadFromForum(Long tId) throws NoResultException;

    public List<entity.ThreadEntity> viewThreadsInForum(Long fId) throws NoResultException;

    public ThreadEntity getThreadInForum(Long fId, Long tId) throws NoResultException;

    public List<Forum> getAllForums();

}
