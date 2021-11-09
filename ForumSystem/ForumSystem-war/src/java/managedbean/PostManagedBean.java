/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Forum;
import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
import exception.PostDeletionException;
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
import session.PostSessionBeanLocal;
import session.ThreadSessionBeanLocal;
import session.UserSessionBeanLocal;

/**
 *
 * @author jiajun
 */
@Named(value = "postManagedBean")
@ViewScoped
public class PostManagedBean implements Serializable {

    @EJB
    private PostSessionBeanLocal postSessionBeanLocal;
    @EJB
    private ThreadSessionBeanLocal threadSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    String title;
    String body;

    Long pId;
    List<Post> posts;
    ThreadEntity thread;
    UserEntity user;
    Post post;

    private boolean isAdmin;

    /**
     * Creates a new instance of PostManagedBean
     */
    public PostManagedBean() {
    }

    @PostConstruct
    public void init() {
        thread = (ThreadEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("thread");
        user = (UserEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserEntity");
        posts = thread.getPosts();
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("isAdmin");
        if (obj == null) {
            isAdmin = false;
        } else {
            isAdmin = (boolean) obj;
        }
    }

    public void addPost(ActionEvent evt) throws IOException, NoResultException {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
//        postSessionBeanLocal.createPost(post, thread.getId(), user.getId());
        threadSessionBeanLocal.updateThread(thread);
        userSessionBeanLocal.updateUser(user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("forum.xhtml?faces-redirect=true");
    }

    public void deletePost(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Long pId = (Long) event.getComponent().getAttributes().get("pId");
        try {
            postSessionBeanLocal.deletePost(pId);
            context.addMessage(null, new FacesMessage("Success", "Successfully deleted post"));
            init();
        } catch (NoResultException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete post"));
            return;
        }
    }

    public void onRowEdit(RowEditEvent<Post> event) throws NoResultException {
        FacesMessage msg = new FacesMessage("Post Edited", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        Post p = event.getObject();

        postSessionBeanLocal.updatePost(p);
        this.init();
    }

    public void onRowCancel(RowEditEvent<Post> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

//    public void updatePost(ActionEvent evt) throws IOException, NoResultException {
//        Long pId = (Long) evt.getComponent().getAttributes().get("pId");
//        post = postSessionBeanLocal.getPost(pId);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pId", pId);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("editPost.xhtml");
//    }
    public void editPost(ActionEvent evt) {
        try {
            post = (Post) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("post");
            post.setTitle(title);
            post.setBody(body);
            postSessionBeanLocal.updatePost(post);
            FacesContext.getCurrentInstance().getExternalContext().redirect("post.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update post"));
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ThreadEntity getThread() {
        return thread;
    }

    public void setThread(ThreadEntity thread) {
        this.thread = thread;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
