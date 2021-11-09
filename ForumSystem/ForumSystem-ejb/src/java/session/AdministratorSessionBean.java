/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Administrator;
import java.util.List;
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
public class AdministratorSessionBean implements AdministratorSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Administrator getAdministrator(Long aId) throws NoResultException {
        Administrator admin = em.find(Administrator.class, aId);

        if (admin != null) {
            return admin;
        } else {
            throw new NoResultException("Not found");
        }
    } //end getCustomer

    @Override
    public void deleteAdministrator(Long aId) throws NoResultException {
        Administrator admin = getAdministrator(aId);

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
        em.remove(admin);
    }

    public void updateAdministrator(Administrator admin) throws NoResultException {
        Administrator oldA = getAdministrator(admin.getId());

        oldA.setName(admin.getName());
        oldA.setPassword(admin.getPassword());
        oldA.setDescription(admin.getDescription());
        oldA.setEmail(admin.getEmail());
    }
    
    @Override
    public Administrator retrieveAdministratorByName(String name) {
        Query query = em.createQuery("SELECT s FROM Administrator s WHERE s.name = :inName");
        query.setParameter("inName", name);
        return (Administrator) query.getSingleResult();
    }
    
    
}
