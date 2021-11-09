/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Administrator;
import entity.Forum;
import entity.ThreadEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jiajun
 */
@Stateless
public class ForumSessionBean implements ForumSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Forum getForum(Long fId) throws NoResultException {
        Forum forum = em.find(Forum.class, fId);

        if (forum != null) {
            return forum;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public void createForum(Forum f) {
        em.persist(f);
    }

    @Override
    public List<Forum> getAllForums() {
        Query query = em.createQuery("SELECT f FROM Forum f");
        return query.getResultList();
    }

    @Override
    public void deleteForum(Long fId) throws NoResultException {
        Forum forum = getForum(fId);

//        List<ThreadEntity> threads = forum.getThreads();
//        forum.setThreads(null);
//
//        for (ThreadEntity t : threads) {
//            em.remove(t);
//        }
        em.remove(forum);
    }

    @Override
    public void updateForum(Forum forum) throws NoResultException {
        Forum oldF = getForum(forum.getId());

        oldF.setTitle(forum.getTitle());
        oldF.setBody(forum.getBody());
        oldF.setDescription(forum.getDescription());
    }

//    @Override
//    public Forum retrieveForumById(Long id) throws NoResultException {
//        Forum forum = em.find(Forum.class, id);
//
//        if (forum != null) {
//            return forum;
//        } else {
//            throw new NoResultException("Not found");
//        }
//    }

    @Override
    public Forum getForumFromThreadId(Long tId) {
        Query query = em.createQuery("SELECT f FROM Forum f, IN(f.threads) t WHERE t.id =:tId").setParameter("tId", tId);
        Forum forum = (Forum) query.getSingleResult();
        for (ThreadEntity thread : forum.getThreads()) {
            thread.getPosts().size();
        }
        return forum;
    }

    @Override
    public void addThreadToForum(Long fId, ThreadEntity t) throws NoResultException {
        Forum forum = getForum(fId);
        em.persist(t);

        if (t.getTitle() != null) {
            t.setTitle(t.getTitle().trim());
        }

        if (t.getBody() != null) {
            t.setBody(t.getBody().trim());
        }

        forum.getThreads().add(t);
    }

    @Override
    public void deleteThreadFromForum(Long tId) throws NoResultException {
        ThreadEntity thread = em.find(ThreadEntity.class,
                 tId);

        if (thread != null) {
            Query q = em.createQuery("SELECT f FROM Forum f WHERE :thread MEMBER OF f.threads");
            q.setParameter("thread", thread);

            for (Object forum : q.getResultList()) {
                Forum forum1 = (Forum) forum;
                forum1.getThreads().remove(thread);
            }

            em.remove(thread);
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public List<ThreadEntity> viewThreadsInForum(Long fId) throws NoResultException {
        Forum forum = em.find(Forum.class,
                 fId);
        List<ThreadEntity> threads = forum.getThreads();
        return threads;
    }

    @Override
    public ThreadEntity getThreadInForum(Long fId, Long tId) throws NoResultException {
        List<ThreadEntity> threads = viewThreadsInForum(fId);
        ThreadEntity newThread = null;

        for (ThreadEntity thread : threads) {
            if (thread.getId().equals(tId)) {
                newThread = thread;
            }
        }
        if (newThread != null) {
            return newThread;
        } else {
            throw new NoResultException("Thread not found!");
        }
    }
}
