/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import db.DBManager;
import db.Group;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author paolo
 */
@WebServlet(name = "Post", urlPatterns = {"/forum/post"})
public class Post extends HttpServlet {

    private static final String FORM_HTML
            = "<form action=\"/forum/post\" data-ajax=\"false\" method=\"post\" enctype=\"multipart/form-data\">\n"
            + "            <ul data-role=\"listview\" data-inset=\"true\">\n"
            + "                <li data-role=\"fieldcontain\">\n"
            + "                    <label for=\"text\">Post text:</label>\n"
            + "                    <textarea id=\"text\" placeholder=\"Bla bal bla\" name=\"text\"></textarea>\n"
            + "                </li>\n"
            + "                <li data-role=\"fieldcontain\">\n"
            + "                    <label for=\"button\">Add more files:</label>\n"
            + "                    <button type=\"button\" id=\"button\" data-inline=\"true\" data-mini=\"true\" onclick=\"duplicate('#file-li')\">Add one more file</button>\n"
            + "                </li>\n"
            + "                <li data-role=\"fieldcontain\" id=\"file-li\">\n"
            + "                    <label for=\"file\">File:</label>\n"
            + "                    <input type=\"file\" id=\"file\" name=\"file\">\n"
            + "                </li>\n"
            + "            </ul>\n"
            + "            <button data-inline=\"true\" data-theme=\"b\" type=\"submit\">Post it</button>\n"
            + "        </form>";

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
        DBManager dbmanager = (DBManager) getServletContext().getAttribute("dbmanager");
        int groupId = Integer.parseInt(request.getParameter("id"));
        Group group = dbmanager.getGroup(groupId);
        if (group == null) {
            HTML.print404(out);
        } else {
            HTML.printPage(out, "Write your post", "forum/group?id=" + groupId, FORM_HTML);
        }
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
        PrintWriter out = response.getWriter();
        DBManager dbmanager = (DBManager) getServletContext().getAttribute("dbmanager");
        int groupId = Integer.parseInt(request.getParameter("id"));
        Group group = dbmanager.getGroup(groupId);
        if (group == null) {
            HTML.print404(out);
        } else {
            try {
                MultipartRequest multipart = new MultipartRequest(request, group.getRealFilesPath(request), 10 * 1024 * 1024, "UTF-8", new DefaultFileRenamePolicy());
                
                Enumeration files = multipart.getFileNames();
                
            } catch (IOException ex) {
                this.getServletContext().log(ex, "Problems during file upload.");
            }
        }

    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servet to handle creation of new posts";
    }// </editor-fold>

}
