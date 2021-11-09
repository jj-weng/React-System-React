/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Administrator;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Local
public interface AdministratorSessionBeanLocal {

    public Administrator getAdministrator(Long aId) throws NoResultException;

    public void deleteAdministrator(Long aId) throws NoResultException;

    public Administrator retrieveAdministratorByName(String name);

}
