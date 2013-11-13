/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author paolo
 */
public class Login extends HttpServlet {
    
    private static final String ERROR_HTML =
            "<p style=\"color: red\">Login failed, please check your data</p>";
    
    private static final String FORM_HTML =
            "<form action=\"login\" method=\"post\" data-ajax=\"false\">"
            + "<ul data-role=\"listview\" data-inset=\"true\">"
            + "<li data-role=\"fieldcontain\">"
            + "<label for=\"login_name\">Username:</label>"
            + "<input type=\"text\" id=\"login_name\" placeholder=\"username\" name=\"name\">"
            + "</li>"
            + "<li data-role=\"fieldcontain\">"
            + "<label for=\"login_password\">Password:</label>"
            + "<input type=\"password\" id=\"login_password\" placeholder=\"password\" name=\"password\">"
            + "</li>"
            + "</ul>"
            + "<button type=\"submit\">Login</button>"
            + "</form>"
            + "</form>";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HTML.printPage(response.getWriter(), "Login", FORM_HTML);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DBManager dbmanager = (DBManager) getServletContext().getAttribute("dbmanager");
        User user = dbmanager.authenticate(request.getParameter("name"), request.getParameter("password"));
        if(user == null) {
            HTML.printPage(response.getWriter(), "Login", ERROR_HTML + FORM_HTML);
        } else {
            HttpSession session = request.getSession();
            Cookie loginTime = new Cookie("loginTime", new Date().getTime() + "");
            loginTime.setMaxAge(-1);
            loginTime.setPath("/");
            response.addCookie(loginTime);
            session.setAttribute("user", user);
            response.sendRedirect("/forum");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
