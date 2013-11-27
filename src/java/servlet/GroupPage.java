/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Group;
import db.Post;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import java.io.File;

/**
 *
 * @author Pier DAgostino
 */
public class GroupPage extends HttpServlet {

    private final String TITLE = "Group";

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
        try (PrintWriter out = response.getWriter()) {
            DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
            User logged = (User) request.getSession().getAttribute("user");
            String id = request.getParameter("id");
            if (id == null) {
                HTML.print404(out);
            } else {
                String content = "";
                int groupId = Integer.parseInt(id);
                Group currentGroup = manager.getGroup(groupId);
                LinkedList<Post> postOf = manager.getGroupPosts(currentGroup);
                Iterator<Post> i = postOf.iterator();

                while (i.hasNext()) {
                    Post current = i.next();
                    content += "<div class=\"ui-grid-a ui-responsive\">\n"
                            + "            <div class=\"ui-block-a\" style=\"width: 20%\">\n"
                            + "              <div>\n"
                            + "                 <img style=\"max-width: 100%; height: auto\" src=\"" + current.getCreator().getAvatar(request) + "\">\n"
                            + "              </div>\n"
                            + "              <p>\n"
                            + current.getCreator().getName() + "\n"
                            + "              </p>\n"
                            + "            </div>\n"
                            + "            <div class=\"ui-block-b\" style=\"width: 80%\">\n"
                            + current.getText() + "\n"
                            + "            </div>\n"
                            + "        </div>";
                }

                HTML.printPage(out, TITLE, "/forum/groups", content);
            }
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
