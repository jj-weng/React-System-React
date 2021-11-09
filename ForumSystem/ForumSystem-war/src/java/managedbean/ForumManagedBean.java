/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Forum;
import entity.ThreadEntity;
import entity.UserEntity;
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
import sun.misc.VM;

/**
 *
 * @author jiajun
 */
@Named(value = "forumManagedBean")
@ViewScoped
public class ForumManagedBean implements Serializable {

    @EJB
    private ForumSessionBeanLocal forumSessionBeanLocal;

    String title;
    String body;
    String description;

    Long fId;
    List<Forum> forums;

    private boolean isAdmin;

    /**
     * Creates a new instance of ForumManagedBean
     */
    public ForumManagedBean() {
    }

    @PostConstruct
    public void init() {
        forums = forumSessionBeanLocal.getAllForums();
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("isAdmin");
        if (obj == null) {
            isAdmin = false;
        } else {
            isAdmin = (boolean) obj;
        }
    }

    public void onRowEdit(RowEditEvent<Forum> event) throws NoResultException {
        FacesMessage msg = new FacesMessage("Forum Edited", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        Forum f = event.getObject();

        forumSessionBeanLocal.updateForum(f);
        this.init();
    }

    public void onRowCancel(RowEditEvent<Forum> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public List<ThreadEntity> viewThreads(Long fId) {
//
//        List<ThreadEntity> threads = null;
//
//        try {
//            threads = forumSessionBeanLocal.viewThreadsInForum(fId);
//        } catch (NoResultException ex) {
//            System.err.println("No threads");
//        }
//        return threads;
//    }
//    public void deleteForum(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("deleteForum", fId);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("deleteForum.xhtml");
//    }
//    public void updateForum(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("editForum", fId);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("editForum.xhtml");
//    }
    public void addForum(ActionEvent evt) {
        Forum forum = new Forum();
        forum.setTitle(title);
        forum.setDescription(description);
        forum.setBody(body);
        forumSessionBeanLocal.createForum(forum);
    }
//
//    public void addThread(ActionEvent evt) throws NoResultException {
//        ThreadEntity thread = new ThreadEntity();
//        thread.setTitle(title);
//        thread.setBody(body);
//        forumSessionBeanLocal.addThreadToForum(fId,thread);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
