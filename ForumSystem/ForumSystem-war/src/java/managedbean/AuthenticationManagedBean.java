package managedbean;

import entity.Forum;
import entity.Post;
import entity.ThreadEntity;
import entity.UserEntity;
import exception.IncorrectLoginParticularsException;
import exception.InvalidLoginException;
import exception.UserNotFoundException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import session.ForumSessionBeanLocal;
import session.PostSessionBeanLocal;
import session.ThreadSessionBean;
import session.ThreadSessionBeanLocal;
import session.UserSessionBean;
import session.UserSessionBeanLocal;

@Named(value = "authenticationManagedBean")
@RequestScoped
public class AuthenticationManagedBean {

    private String name;
    private String password;
    long userId = -1;
    private Forum forum;
    private ThreadEntity thread;
    private Post post;

    public AuthenticationManagedBean() {
    }

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;
    @EJB
    private ForumSessionBeanLocal forumSessionBeanLocal;
    @EJB
    private ThreadSessionBeanLocal threadSessionBeanLocal;
    @EJB
    private PostSessionBeanLocal postSessionBeanLocal;

    public void login(ActionEvent event) throws IOException, InvalidLoginException, UserNotFoundException {
        try {
            UserEntity currentUser = userSessionBeanLocal.loginUser(name, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if(currentUser.isIsAdmin()) {
                 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isAdmin", true);
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UserEntity", currentUser);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/homepage.xhtml");
        } catch (InvalidLoginException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void logout() throws IOException {
        name = null;
        password = null;
        userId = -1;
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public void viewThread(ActionEvent Event) throws IOException, NoResultException {

        Long fId = (Long) Event.getComponent().getAttributes().get("fId");
        forum = forumSessionBeanLocal.getForum(fId);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("forum", forum);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/thread.xhtml");
    }

    public void viewPost(ActionEvent Event) throws IOException, NoResultException {

        Long tId = (Long) Event.getComponent().getAttributes().get("tId");
        thread = threadSessionBeanLocal.getThread(tId);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("thread", thread);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/post.xhtml");
    }

    public void deleteForum(ActionEvent Event) throws IOException, NoResultException {

        Long fId = (Long) Event.getComponent().getAttributes().get("fId");
        forumSessionBeanLocal.deleteForum(fId);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("forum", forum);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/forum.xhtml");
    }

    public void currentPost(ActionEvent Event) throws IOException, NoResultException {
        Long pId = (Long) Event.getComponent().getAttributes().get("pId");
        postSessionBeanLocal.getPost(pId);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("post", post);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/editPost.xhtml");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
