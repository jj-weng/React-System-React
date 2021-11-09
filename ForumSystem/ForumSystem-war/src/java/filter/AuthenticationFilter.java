/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jiajun
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
    
   FilterConfig filterConfig;

    private static final String CONTEXT_ROOT = "/ForumSystem-war";

    public AuthenticationFilter() {
    
    }
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();

        if (httpSession.getAttribute("isLogin") == null) {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");
        
        System.out.println("Check request path: " + requestServletPath);
        
        if (!excludeLoginCheck(requestServletPath)) {
            if (isLogin == true) {
//                StaffEntity currentStaffEntity = (StaffEntity) httpSession.getAttribute("currentStaffEntity");
                chain.doFilter(request, response);
            } else {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/index.xhtml");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private Boolean excludeLoginCheck(String path) {
        if (path.equals("/index.xhtml")
                || path.equals("/registerUser.xhtml")
                || path.equals("/login.xhtml")
                || path.equals("/webresources")
                || path.startsWith("/javax.faces.resource")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
}
