/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paolo
 */
public class Login extends HttpServlet {
    
    private static final String PAGE_HTML =
            "<form>"
            + "<ul data-role=\"listview\" data-inset=\"true\">"
            + "<li data-role=\"fieldcontain\">"
            + "<label for=\"login_name\">Username:</label>"
            + "<input type=\"text\" id=\"login_name\" placeholder=\"username\" name=\"name\">"
            + "</li>"
            + "<li data-role=\"fieldcontain\">"
            + "<label for=\"login_password\">Password:</label>"
            + "<input type=\"text\" id=\"login_password\" placeholder=\"password\" name=\"password\">"
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
        HTML.printPage(response.getWriter(), "Login", PAGE_HTML);
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
