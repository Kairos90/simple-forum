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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author halfblood
 */
public class GroupManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
     // TITLE OF GROUPS PAGE & CONTENT OF GROUPS PAGE
    private static final String GROUP_MANAGER_TITLE = "My Group";
    private static final String GROUPS_CONTENT_HEAD_TABLE = "<table data-role=\"table\" id=\"groups-table\" class=\"ui-dbody-d table-stripe ui-responsive\">\n"
            + "         <thead>\n"
            + "           <tr class=\"ui-bar-d\">\n"
            + "             <th>Name</th>\n"
            + "             <th>Accepted</th>\n"
            + "           </tr>\n"
            + "         </thead>\n";
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Setting Content Page
        String groupsContent = "";
         String groupsContentBodyTableEnd = "</tbody>\n"
                + "       </table>";
        String groupsContentBodyTable = "<tbody>\n";

        //GETTING USER INFORMATION
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");
        LinkedList<Group> Invites = manager.showInvites(logged);
        Iterator<Group> i = Invites.iterator();
        
         while (i.hasNext()) {
             
         }
        
        
        
       try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            groupsContent = GROUPS_CONTENT_HEAD_TABLE + groupsContentBodyTable + groupsContentBodyTableEnd;
            HTML.printPage(out, GROUP_MANAGER_TITLE, "/forum", groupsContent);

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
