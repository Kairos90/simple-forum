/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author paolo
 */
@WebServlet(name = "Post", urlPatterns = {"/forum/post"})
public class Post extends HttpServlet {
    
    private static final String FORM_HTML =
            "<form>\n" +
"            <ul data-role=\"listview\" data-inset=\"true\">\n" +
"                <li data-role=\"fieldcontain\">\n" +
"                    <label for=\"text\">Post text:</label>\n" +
"                    <textarea id=\"text\" placeholder=\"Bla bal bla\" name=\"text\"></textarea>\n" +
"                </li>\n" +
"                <li data-role=\"fieldcontain\">\n" +
"                    <label for=\"text\">Add more files:</label>\n" +
"                    <button type=\"button\" onclick=\"duplicate('#file-li')\">Add one more file</button>\n" +
"                </li>\n" +
"                <li data-role=\"fieldcontain\" id=\"file-li\" style=\"display: none\">\n" +
"                    <label for=\"file\">File:</label>\n" +
"                    <input type=\"file\" id=\"file\" name=\"file\">\n" +
"                </li>\n" +
"            </ul>\n" +
"            <button type=\"submit\">Post it</button>\n" +
"        </form>";

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
        PrintWriter out = response.getWriter();
        int groupId = Integer.parseInt(request.getParameter("id"));
        HTML.printPage(out, "Write your post", "forum/group?id=" + groupId, FORM_HTML);
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
