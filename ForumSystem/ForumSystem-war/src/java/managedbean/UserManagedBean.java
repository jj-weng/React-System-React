/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.UserEntity;
import enumeration.UserStatus;
import static enumeration.UserStatus.UNBLOCKED;
import exception.UserNotFoundException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import session.UserSessionBeanLocal;
import javax.faces.event.ActionEvent;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.persistence.NoResultException;

/**
 *
 * @author jiajun
 */
@Named(value = "userManagedBean")
@ViewScoped
public class UserManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    String name;
    String email;
    String password;
    Byte gender;
    Date dob;
    Boolean isAdmin;
    UserStatus userStatus;

    Long uId;
    UserEntity user;
    List<UserEntity> userList;

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
    }

    @PostConstruct
    public void init() {
        //try {
        this.user = (UserEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserEntity");
        if (user == null) {
            //FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
            user = new UserEntity();
        } else {
            this.name = user.getName();
            this.email = user.getEmail();
            this.password = user.getPassword();
            //this.gender = user.getGender();
            this.dob = user.getDob();
            this.userStatus = user.getUserStatus();
            this.uId = this.user.getId();
        }

        userList = userSessionBeanLocal.getAllUsers();
        userList.remove(0);
    }

    public void registerUser(ActionEvent evt) {
        UserEntity u = new UserEntity();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        //u.setGender(gender);
        u.setDob(dob);
        u.setIsAdmin(false);
        u.setUserStatus(UNBLOCKED);
        userSessionBeanLocal.createUser(u);
    }

    public void updateUser(ActionEvent evt) {
        FacesContext context = FacesContext.getCurrentInstance();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        //user.setGender(gender);
        user.setDob(dob);
        try {
            userSessionBeanLocal.updateUser(user);
        } catch (Exception e) {
            //show with an error icon
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update user"));
            return;
        }
        //need to make sure reinitialize the customers collection
        init();
        context.addMessage(null, new FacesMessage("Success", "Successfully updated user"));
    }

    public void lockUser(ActionEvent evt) throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();
        Long uId = (Long) evt.getComponent().getAttributes().get("uId");
        UserEntity newUser = userSessionBeanLocal.getUser(uId);
        newUser.setUserStatus(UserStatus.BLOCKED);
        userSessionBeanLocal.updateUser(newUser);
    }

    public void unlockUser(ActionEvent evt) throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();
        Long uId = (Long) evt.getComponent().getAttributes().get("uId");
        UserEntity newUser = userSessionBeanLocal.getUser(uId);
        newUser.setUserStatus(UserStatus.UNBLOCKED);
        userSessionBeanLocal.updateUser(newUser);
    }

    public void validateName(FacesContext context, UIComponent comp,
            Object value) throws ValidatorException {
        String currentName = (String) value;
        try {
            UserEntity currUserEntity = userSessionBeanLocal.retrieveUserByName(currentName);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
                    "Name is being used!"));
        } catch (UserNotFoundException ex) {

        }
    }

//    public void loadSelectedUser() {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        try {
//            this.user = userSessionBeanLocal.getUser(uId);
//            name = this.user.getName();
//            email = this.user.getEmail();
//            password = this.user.getPassword();
//            gender = this.user.getGender();
//            dob = this.user.getDob().toString();
//        } catch (Exception e) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load user."));
//        }
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

}
