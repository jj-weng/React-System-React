/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Post;
import entity.ThreadEntity;
import entity.Forum;
import entity.UserEntity;
import exception.ThreadDeletionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jiajun
 */
@Stateless
public class ThreadSessionBean implements ThreadSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ForumSessionBeanLocal forumSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @Override
    public ThreadEntity getThread(Long tId) throws NoResultException {
        ThreadEntity thread = em.find(ThreadEntity.class, tId);

        if (thread != null) {
            return thread;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public void createThread(ThreadEntity t) {
        em.persist(t);
    }

    @Override
    public void createThread(ThreadEntity t, Long fId) throws NoResultException {
        Forum forum = forumSessionBeanLocal.getForum(fId);
        //UserEntity user = userSessionBeanLocal.getUser(uId);
        em.persist(t);
        em.flush();
        forum.getThreads().add(t);
        //user.getThreads().add(t);
    }

    @Override
    public ThreadEntity getThreadFromPostId(Long pId) {
        Query query = em.createQuery("SELECT t FROM ThreadEntity t, IN(t.posts) p WHERE p.id =:pId").setParameter("pId", pId);
        ThreadEntity thread = (ThreadEntity) query.getSingleResult();
        return thread;
    }

    @Override
    public void deleteThread(Long tId) throws NoResultException, ThreadDeletionException {
        ThreadEntity thread = getThread(tId);
        Forum forum = forumSessionBeanLocal.getForumFromThreadId(tId);
        //UserEntity user = userSessionBeanLocal.getUserEntityFromThreadId(tId);
        List<Post> posts = thread.getPosts();
        int numOfPost = posts.size();

        if (numOfPost == 0) {
            forum.getThreads().remove(thread);
            //user.getThreads().remove(thread);
            em.remove(thread);
        } else { //still have posts associated with this thread, hence cannot be deleted
            throw new ThreadDeletionException("There are posts associated with this thread, cannot be deleted!");
        }
    }

    @Override
    public void updateThread(ThreadEntity thread) throws NoResultException {
        ThreadEntity oldT = getThread(thread.getId());

        oldT.setTitle(thread.getTitle());
        oldT.setBody(thread.getBody());
        oldT.setThreadOpenClose(thread.getThreadOpenClose());

        //do I need em.merge()?
    }

//    @Override
//    public ThreadEntity retrieveThreadById(Long id) throws NoResultException {
//        ThreadEntity thread = em.find(ThreadEntity.class,
//                 id);
//
//        if (thread != null) {
//            return thread;
//        } else {
//            throw new NoResultException("Not found");
//        }
//    }

    @Override
    public void addPostToThread(Long tId, Post p) throws NoResultException {
        ThreadEntity thread = getThread(tId);
        em.persist(p);

        if (p.getTitle() != null) {
            p.setTitle(p.getTitle().trim());
        }

        if (p.getBody() != null) {
            p.setBody(p.getBody().trim());
        }

        thread.getPosts().add(p);
    }

    @Override
    public void deletePostFromThread(Long pId) throws NoResultException {
        Post post = em.find(Post.class,
                 pId);

        if (post != null) {
            Query q = em.createQuery("SELECT t FROM Thread t WHERE :post MEMBER OF t.posts");
            q.setParameter("post", post);
            for (Object thread : q.getResultList()) {
                ThreadEntity thread1 = (ThreadEntity) thread;
                thread1.getPosts().remove(post);
            }

            em.remove(post);
        } else {
            throw new NoResultException("Not found");
        }
    }
    
    @Override
    public Forum getForum(Long tId) {
        ThreadEntity t = getThread(tId);
        Query q = em.createQuery("SELECT f FROM Forum f WHERE :thread MEMBER OF f.threads");
        q.setParameter("thread", t);
        
        return (Forum) q.getSingleResult();
    }

    @Override
    public List<Post> viewPostsInThread(Long tId) throws NoResultException {
        ThreadEntity thread = em.find(ThreadEntity.class,
                 tId);
        List<Post> posts = thread.getPosts();
        return posts;
    }

    @Override
    public Post getPostInThread(Long tId, Long pId) throws NoResultException {
        List<Post> posts = viewPostsInThread(tId);
        Post newPost = null;

        for (Post post : posts) {
            if (post.getId().equals(tId)) {
                newPost = post;
            }
        }
        if (newPost != null) {
            return newPost;
        } else {
            throw new NoResultException("Post not found!");
        }
    }

    @Override
    public List<ThreadEntity> getAllThreadsOfForum(Forum fId) {
        Forum forum = em.find(Forum.class,
                 fId);
        List<ThreadEntity> list = forum.getThreads();
        return list;
    }
}
