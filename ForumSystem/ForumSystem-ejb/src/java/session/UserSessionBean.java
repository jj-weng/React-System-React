/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Post;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.UserEntity;
import exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import entity.ThreadEntity;
import enumeration.UserStatus;
import static enumeration.UserStatus.UNBLOCKED;
import exception.InvalidLoginException;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Stateless
public class UserSessionBean implements UserSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserEntity getUser(Long uId) throws NoResultException {
        UserEntity user = em.find(UserEntity.class, uId);
        return user;
    }

    @Override
    public void createUser(UserEntity u) {
        em.persist(u);
    }

    @Override
    public void deleteUser(Long uId) throws NoResultException {
        UserEntity user = getUser(uId);

        /*
        List<Field> fields = cust.getFields();
        cust.setFields(null);

        for (Field f : fields) {
            //if no other association between field and customer, we are safe to delete this field
            Query q = em.createQuery("SELECT count(c) FROM Customer c WHERE :field MEMBER OF c.fields");
            q.setParameter("field", f);

            long count = (Long) q.getSingleResult();

            if (count == 0) {
                em.remove(f);
            }
        }
         */
        em.remove(user);
    }

    @Override
    public void updateUser(UserEntity user) throws NoResultException {
        UserEntity oldU = getUser(user.getId());

        oldU.setName(user.getName());
        oldU.setPassword(user.getPassword());
        oldU.setEmail(user.getEmail());
        oldU.setGender(user.getGender());
        oldU.setDob(user.getDob());
        oldU.setUserStatus(user.getUserStatus());
    }

    @Override
    public UserEntity retrieveUserByName(String name) throws UserNotFoundException {
        try {
            Query query = em.createQuery("SELECT s FROM UserEntity s WHERE s.name = :inName");
            query.setParameter("inName", name);
            return (UserEntity) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new UserNotFoundException("No user found!");
        }
    }

//    @Override
//    public UserEntity getUserEntityFromPostId(Long pId) {
//        Query query = em.createQuery("SELECT u FROM UserEntity u, IN(u.posts) p WHERE p.id =:pId").setParameter("pId", pId);
//        UserEntity user = (UserEntity) query.getSingleResult();
//        return user;
//    }
//
//    @Override
//    public UserEntity getUserEntityFromThreadId(Long tId) {
//        Query query = em.createQuery("SELECT u FROM UserEntity u, IN(u.threads) t WHERE t.id =:tId").setParameter("tId", tId);
//        UserEntity user = (UserEntity) query.getSingleResult();
//        return user;
//    }

    @Override
    public UserEntity loginUser(String name, String password) throws InvalidLoginException, UserNotFoundException {
        UserEntity user = retrieveUserByName(name);
        if (user.getPassword().equals(password) && user.getUserStatus().equals(UNBLOCKED)) {
            return user;
        } else {
            throw new InvalidLoginException("Invalid Login!");
        }
    }

//    @Override
//    public List<ThreadEntity> viewThreadsOfUser(String name) throws UserNotFoundException {
//        UserEntity user = retrieveUserByName(name);
//        List<ThreadEntity> threads = user.getThreads();
//        return threads;
//    }
//
//    @Override
//    public List<Post> viewPostsOfUser(String name) throws UserNotFoundException {
//        UserEntity user = retrieveUserByName(name);
//        List<Post> posts = user.getPosts();
//        return posts;
//    }

    @Override
    public List<UserEntity> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM UserEntity u");
        return query.getResultList();
    }
}
