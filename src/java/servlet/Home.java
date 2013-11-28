/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paolo
 */
public class Home extends HttpServlet {
    
    private static final String CONTENT_HTML =
            "<ul data-role=\"listview\" data-inset=\"true\">"
            + "<li><a href=\"forum/groups\">My groups</a></li>"
            + "<li><a href=\"forum/create\">Create group</a></li>"
            + "<li><a href=\"forum/invites\">Invites</a></li>"
            + "<li><a href=\"forum/avatar\">Avatar Upload</a></li>"
            + "<li><a href=\"/logout\" data-ajax=\"false\">Logout</a></li>"
            + "</ul>";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        Cookie[] cookies = request.getCookies();
        Date loginTime = null;
        for (Cookie cookie : cookies) {
            if ("loginTime".equals(cookie.getName())) {
                loginTime = new Date(Long.parseLong(cookie.getValue()));
                break;
            }
        }
        String welcomeMessage =
                "<ul data-role=\"listview\" data-inset=\"true\">"
                + "<li><h1>"
                + "Welcome " + user.getName()
                + "</h1>";
        if(loginTime != null) {
            welcomeMessage +=
                    "<p>"
                    + "You logged in " + loginTime.toString()
                    + "</p>";
        }
        welcomeMessage += "</li></ul>";
        HTML.printPage(out, "Forum home",  welcomeMessage + CONTENT_HTML);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Home page";
    }// </editor-fold>

}
