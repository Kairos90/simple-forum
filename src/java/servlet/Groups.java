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
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final String GROUPS_TITLE = "My Groups";
    private static final String GROUPS_CONTENT_HEAD_TABLE = "<table data-role=\"table\" id=\"groups-table\" class=\"ui-dbody-d table-stripe ui-responsive\">\n"
            + "         <thead>\n"
            + "           <tr class=\"ui-bar-d\">\n"
            + "             <th>Group Name</th>\n"
            + "             <th>News</th>\n"
            + "             <th>Latest Post</th>\n"
            + "             <th><abbr title=\"Rotten Tomato Rating\">Modify</abbr></th>\n"
            + "             <th>Summerize</th>\n"
            + "           </tr>\n"
            + "         </thead>\n";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        //SETTING CONTENT OF THE PAGE
        String groupsContent = "";
        String groupsContentBodyTableEnd = "</tbody>\n"
                + "       </table>";
        String groupsContentBodyTable = "<tbody>\n";

        //GETTING USER INFORMATION
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");
        LinkedList<Group> groupsOf = manager.getUserGroups(logged);
        Iterator<Group> i = groupsOf.iterator();
        
        while (i.hasNext()) {
            Group groupConsidering = i.next();
            //SETTING TABLE DEPENDING ON OWN PROPERTY
            if (groupConsidering.getCreator() == logged.getId()) {
                groupsContentBodyTable += "<tr>\n"
                + "             <th><a href=\"/forum/group?id=" + groupConsidering.getId() + "\">" + groupConsidering.getName() + "</a></th>\n"                          //Group Name
                + "             <td></td>\n"                     //News
                + "             <td>" + manager.getLatestPost(groupConsidering) + "</td>\n"             //Latest Post
                + "             <td></td>\n"                                                            //Modifica
                + "             <td></td>\n"                                                            //Resoconto
                + "           </tr>\n";
            } else {
                groupsContentBodyTable += "<tr>\n"
                + "             <th>" + groupConsidering.getName() + "</th>\n"                          //Group Name
                + "             <td>jadjsdvjksnkjv</td>\n"                     //News
                + "             <td><a href=\"/forum/group?id=" + groupConsidering.getId() + "\">" + manager.getLatestPost(groupConsidering) + "</a></td>\n"             //Latest Post
                + "             <td></td>\n"                                        //Modifica
                + "             <td></td>\n"                                        //Resoconto
                + "           </tr>\n";
            }
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            groupsContent = GROUPS_CONTENT_HEAD_TABLE + groupsContentBodyTable + groupsContentBodyTableEnd;
            HTML.printPage(out, GROUPS_TITLE, "/forum", groupsContent);

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Groups.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Groups.class.getName()).log(Level.SEVERE, null, ex);
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
