/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

import db.DBManager;
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
 * @author paolo
 */
public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        DBManager dbmanager = (DBManager) session.getServletContext().getAttribute("dbmanager");
        if(user == null) {
            ((HttpServletResponse) response).sendRedirect("/login");
        } else {
            //salva l'ultima data nel databse
            request.setAttribute("lastTimeLogged", dbmanager.getLastQickDisplayTime(user.getId()));
            //aggiorna la data nel database
            dbmanager.updateQuickDisplayTime(user.getId());
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
    
}
