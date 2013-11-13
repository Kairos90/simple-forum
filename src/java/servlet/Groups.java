/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pier DAgostino
 */
public class Groups extends HttpServlet {

    // TITLE OF GROUPS PAGE & CONTENT OF GROUPS PAGE
    private static String GROUPS_TITLE = "My Groups";
    private static String GROUPS_CONTENT = "<table data-role=\"table\" id=\"table-custom-2\" data-mode=\"columntoggle\" class=\"ui-body-d ui-shadow table-stripe ui-responsive\" data-column-btn-theme=\"b\" data-column-btn-text=\"Columns to display...\" data-column-popup-theme=\"a\">\n" +
"         <thead>\n" +
"           <tr class=\"ui-bar-d\">\n" +
"             <th data-priority=\"2\">Group Name</th>\n" +
"             <th>News</th>\n" +
"             <th data-priority=\"3\">Latest Post</th>\n" +
"             <th data-priority=\"1\"><abbr title=\"Rotten Tomato Rating\">Modifica</abbr></th>\n" +
"             <th data-priority=\"5\">Resoconto</th>\n" +
"           </tr>\n" +
"         </thead>\n" +
"         <tbody>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <th></th>\n" +
"             <td class=\"title\"><a href=\"\" data-rel=\"external\"></a></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"             <td></td>\n" +
"           </tr>\n" +
"         </tbody>\n" +
"       </table>";

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
        //Getting user informations
        //User requesting = (User) request.getSession().getAttribute("user");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            HTML.printPage(out, GROUPS_TITLE, GROUPS_CONTENT);
        }
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
        return "Short description";
    }// </editor-fold>

}
