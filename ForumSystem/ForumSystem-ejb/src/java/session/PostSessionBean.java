/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Forum;
import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
import exception.PostDeletionException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class PostSessionBean implements PostSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ThreadSessionBeanLocal threadSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    @Override
    public Post getPost(Long pId) throws NoResultException {
        Post post = em.find(Post.class, pId);

        if (post != null) {
            return post;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public void createPost(Post p) {
        em.persist(p);
    }

    @Override
    public void createPost(Post post, Long tId) throws NoResultException {
        ThreadEntity thread = threadSessionBeanLocal.getThread(tId);
//        UserEntity user = userSessionBeanLocal.getUser(uId);
        em.persist(post);
        em.flush();
        thread.getPosts().add(post);
        //user.getPosts().add(post);
    }

    @Override
    public void deletePost(Long pId) throws NoResultException {
        Post post = getPost(pId);
        ThreadEntity thread = threadSessionBeanLocal.getThreadFromPostId(pId);
        //UserEntity user = userSessionBeanLocal.getUserEntityFromPostId(pId);
        thread.getPosts().remove(post);
        //user.getPosts().remove(post);
        em.remove(post);
    }

    @Override
    public void updatePost(Post p) throws NoResultException {
        Post oldP = getPost(p.getId());
        oldP.setTitle(p.getTitle());
        oldP.setBody(p.getBody());
    }

    @Override
    public ThreadEntity getThread(Long pId) {
        Post p = getPost(pId);
        Query q = em.createQuery("SELECT t FROM ThreadEntity t WHERE :post MEMBER OF t.posts");
        q.setParameter("post", p);

        return (ThreadEntity) q.getSingleResult();
    }
}
