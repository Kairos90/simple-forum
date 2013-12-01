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
import db.User;
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

    private static final String FORM_HTML_TOP =
              "<form action=\"/forum/post\" data-ajax=\"false\" method=\"post\" enctype=\"multipart/form-data\">\n"
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
            + "            <button data-inline=\"true\" data-theme=\"b\" type=\"submit\">Post it</button>\n";
    private static final String FORM_HTML_BOTTOM =
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
        DBManager dbmanager = (DBManager) getServletContext().getAttribute("dbmanager");
        String groupIdParam = request.getParameter("id");
        int groupId = groupIdParam != null ? Integer.parseInt(groupIdParam) : 0;
        Group group = dbmanager.getGroup(groupId);
        if (group == null) {
            HTML.print404(out);
        } else {
            String error = "";
            if(request.getParameter("error") != null) {
                error += "<h2>Please insert some text</h2>";
            }
            String groupIdInput = "<input type=\"hidden\" name=\"id\" value=\"" + groupId + "\">";
            HTML.printPage(out, "Write your post", "forum/group?id=" + groupId, error + FORM_HTML_TOP + groupIdInput + FORM_HTML_BOTTOM);
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
        User user = (User) request.getSession().getAttribute("user");
        String groupIdParam = request.getParameter("id");
        int groupId = groupIdParam != null ? Integer.parseInt(groupIdParam) : 0;
        Group group = dbmanager.getGroup(groupId);
        String text = request.getParameter("text");
        if (group == null) {
            HTML.print404(out);
        } else if(text.length() < 2) {
            response.sendRedirect("/forum/post?error=error&id=" + groupId);
        } else {
            try {
                MultipartRequest multipart = new MultipartRequest(request, group.getRealFilesPath(request), 10 * 1024 * 1024, "UTF-8", new DefaultFileRenamePolicy());
                
                dbmanager.addGroupFiles(group, multipart);
                dbmanager.addPost(groupId, user.getId(), text);
            } catch (IOException ex) {
                this.getServletContext().log(ex, "Problems during file upload.");
            }
            response.sendRedirect("/forum/group?id=" + groupId);
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
