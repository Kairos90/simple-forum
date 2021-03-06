/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import db.DBManager;
import db.Group;
import db.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author halfblood
 */
public class GroupFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        DBManager manager = (DBManager) request.getServletContext().getAttribute("dbmanager");
        String id = request.getParameter("id");
        if (id != null && !"0".equals(id)) {
            int idParam = Integer.parseInt(id);
            if (manager.checkIfUserCanAccessGroup(user.getId(), idParam)) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect("/forum");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
