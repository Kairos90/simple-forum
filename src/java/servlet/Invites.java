/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Group;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author halfblood
 */
public class Invites extends HttpServlet {

    public static final String INVITES_TITLE = "My Invites";
    private static final String INVITES_HEAD_TABLE = "<form action=\"/forum/invites\" data-ajax=\"false\" method=\"post\">\n"
            + "<table data-role=\"table\" id=\"user-table\" class=\"ui-dbody-d table-stripe ui-responsive\">\n"
            + "         <thead>\n"
            + "           <tr class=\"ui-bar-d\">\n"
            + "             <th>Group Name</th>\n"
            + "             <th>Creator</th>\n"
            + "              <th>Accept</th>\n"
            + "           </tr>\n"
            + "         </thead>\n";

    String InvitesBodyTableEnd = "</tbody>\n"
            + "       </table>\n"
            + "   <input type=\"submit\" value=\"submit\" data-inline=\"true\">"
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");
        String userContent;
        String InvitesBodyTable = "<tbody>\n";
        LinkedList<Group> invitesToGroup = manager.getInvites(logged);
        Iterator<Group> i = invitesToGroup.iterator();
        while (i.hasNext()) {
            Group nextGroup = i.next();
            User creator = manager.getUser(nextGroup.getCreator());
            InvitesBodyTable += "<tr>\n"
                    + "             <td>" + nextGroup.getName() + "</td>\n" //Group Name
                    + "             <td>" + creator.getName() + "</td>\n" //Group Creator 
                    + "             <td> <input name=\"" + nextGroup.getId() + "\" value=\"accepted\" type=\"checkbox\"> </td>\n" //Accept Group
                    + "           </tr>\n";
        }

        userContent = INVITES_HEAD_TABLE + InvitesBodyTable + InvitesBodyTableEnd;
        HTML.printPage(out, INVITES_TITLE, "/forum", userContent);
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
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");

        Map<String, String[]> map = request.getParameterMap();

        try {
            manager.acceptInvitesFromGroups(map, logged.getId());
        } catch (Exception e) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, e);
        }

        response.sendRedirect("/forum");

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
