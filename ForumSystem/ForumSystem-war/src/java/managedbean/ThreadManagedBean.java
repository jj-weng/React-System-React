/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Forum;
import entity.ThreadEntity;
import entity.UserEntity;
import enumeration.ThreadOpenClose;

import enumeration.UserStatus;
import exception.ThreadDeletionException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.NoResultException;
import org.primefaces.event.RowEditEvent;
import session.ForumSessionBeanLocal;
import session.ThreadSessionBeanLocal;
import session.UserSessionBeanLocal;

/**
 *
 * @author jiajun
 */
@Named(value = "threadManagedBean")
@ViewScoped
public class ThreadManagedBean implements Serializable {

    @EJB
    private ThreadSessionBeanLocal threadSessionBeanLocal;
    @EJB
    private ForumSessionBeanLocal forumSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    String title;
    String body;

    private Long tId;
    List<ThreadEntity> threads;
    private Forum forum;
    private ThreadEntity thread;
    private UserEntity user;
    
    private boolean isAdmin;

    @PostConstruct
    public void init() {
        forum = (Forum) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("forum");
        user = (UserEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserEntity");
        threads = forum.getThreads();
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("isAdmin");
        if (obj == null) {
            isAdmin = false;
        } else {
            isAdmin = (boolean) obj;
        }
    }

    /**
     * Creates a new instance of ThreadManagedBean
     */
    public ThreadManagedBean() {
    }

    public void addThread(ActionEvent evt) throws IOException, NoResultException {
        ThreadEntity thread = new ThreadEntity();
        thread.setTitle(title);
        thread.setBody(body);
       // thread.setThreadStatus(OPENED);
        thread.setUserEntity(user);
        threadSessionBeanLocal.createThread(thread, forum.getId());
        //user.getThreads().add(thread);
        forumSessionBeanLocal.updateForum(forum);
        userSessionBeanLocal.updateUser(user);

        FacesContext.getCurrentInstance().getExternalContext().redirect("forum.xhtml?faces-redirect=true");
    }

    public void deleteThread(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Long tId = (Long) event.getComponent().getAttributes().get("tId");
        try {
            threadSessionBeanLocal.deleteThread(tId);
            context.addMessage(null, new FacesMessage("Success", "Successfully deleted thread"));
            init();
        } catch (ThreadDeletionException | NoResultException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete thread"));
            return;
        }
    }

    public void onRowEdit(RowEditEvent<ThreadEntity> event) throws NoResultException {
        FacesMessage msg = new FacesMessage("Thread Edited", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        ThreadEntity t = event.getObject();

        threadSessionBeanLocal.updateThread(t);
        this.init();
    }

    public void onRowCancel(RowEditEvent<ThreadEntity> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void closeThread(ActionEvent evt) throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();
        Long tId = (Long) evt.getComponent().getAttributes().get("tId");
        ThreadEntity newThread = threadSessionBeanLocal.getThread(tId);
     //   newThread.setThreadStatus(ThreadOpenClose.THREADCLOSED);
        threadSessionBeanLocal.updateThread(newThread);
    }

    public void openThread(ActionEvent evt) throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();
        Long tId = (Long) evt.getComponent().getAttributes().get("tId");
        ThreadEntity newThread = threadSessionBeanLocal.getThread(tId);
     //   newThread.setThreadStatus(ThreadOpenClose.THREADOPENED);
        threadSessionBeanLocal.updateThread(newThread);
    }

//    public void deleteThread(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("deleteThread", tId);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteThread.xhtml");
//    }
//
//    public void editThread(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editThread", tId);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("editThread.xhtml");
//    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<ThreadEntity> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadEntity> threads) {
        this.threads = threads;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public ThreadEntity getThread() {
        return thread;
    }

    public void setThread(ThreadEntity thread) {
        this.thread = thread;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
