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
import enumeration.UserStatus;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jiajun
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;
    
    @PersistenceContext
    private EntityManager em;

    
   

    @PostConstruct
    public void postConstruct() {
        if (em.find(UserEntity.class, 1L) == null) {
            initializeData();
        }
    }

    public DataInitSessionBean() {
    }

    private void initializeData() {
        userSessionBeanLocal.createUser(new UserEntity("admin", "admin@gmail.com", "password", true, UserStatus.UNBLOCKED));
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
