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
    private static final String GROUPS_CONTENT_HEAD_TABLE = "<table data-role=\"table\" id=\"user-table\" class=\"ui-dbody-d table-stripe ui-responsive\">\n"
            + "         <thead>\n"
            + "           <tr class=\"ui-bar-d\">\n"
            + "             <th>Name</th>\n"
            + "             <th>Accepted</th>\n"
            + "              <th>Visible</th>\n"
            + "           </tr>\n"
            + "         </thead>\n";
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
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
         response.setContentType("text/html;charset=UTF-8");
        
        //SETTING USER TABLE
        String userContent = "";
        
         String userContentBodyTable =  "<tbody>\n";
        
         String userContentBodyTableEnd = "</tbody>\n"
                + "       </table>\n"
                + "   <input type=\"submit\" value=\"submit\">"
                + "</form>";
       

        //GETTING USER INFORMATION
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");
       
        //SETTING GROUP NAME TABLE
        Group groupToEdit = manager.getGroupMadeByUser(logged);
        String titleString = "Create New Group";
        String nameString = "Insert new name";
        
        if(groupToEdit != null){
            titleString = "Edit Name";
            nameString = groupToEdit.getName();
        }
              String groupTableContent = "<form action=\"/forum/create\" data-ajax=\"false\" method=\"POST\">"
              + "   <table data-role=\"table\" id=\"groups-table\" class=\"ui-dbody-d table-stripe ui-responsive\">\n"
              + "         <thead>\n"
              + "           <tr class=\"ui-bar-d\">\n"
              + "             <th>"+ titleString +"</th>\n"
              + "           </tr>\n"
              + "         </thead>\n"
              + "         <tbody>\n"
              + "             <tr>\n"
              // per qualche ragione il placeholder non prende le parole dopo il primo spazio
              + "                 <td> <input name=\"change_group_name\" type=\"text\" placeholder =" + nameString + "> </td>\n"
              + "             </tr>\n"
              + "        </tbody>\n"
              + "</table>";
                
        
        
        //SHOW USERS FOLLOWING THE GROUP AND VISIBLE 
        LinkedList<User> visibleFollowinUsers = manager.getUsersForGroupAndVisible(logged);
        Iterator<User> i = visibleFollowinUsers.iterator();
         while (i.hasNext()) {
             User userConsidered = i.next();
               userContentBodyTable += "<tr>\n"
                + "             <td>" + userConsidered.getName() + "</td>\n"                     //User Name
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"member\" type=\"checkbox\" checked=\"checked\"> </td>\n"      //Group Member 
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"visible\" type=\"checkbox\" checked=\"checked\"> </td>\n"      //Visible
                + "           </tr>\n";
         }
         
         //SHOW USERS FOLLOWING THE GROUP BUT NOT VISIBLE 
        LinkedList<User> notVisibleFollowinUsers = manager.getUsersForGroupAndNotVisible(logged);
        Iterator<User> s = notVisibleFollowinUsers.iterator();
         while (s.hasNext()) {
             User userConsidered = s.next();             
               userContentBodyTable += "<tr>\n"
                + "             <td>" + userConsidered.getName() + "</td>\n"                     //User Name
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"member\" type=\"checkbox\" checked=\"checked\"> </td>\n"      //Group Member 
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"visible\" type=\"checkbox\"> </td>\n"    //Visible
                + "           </tr>\n";
         }
         
        //SHOW ALL THE OTHER USERS
        LinkedList<User> otherUsers = manager.getUsersNotInGroup(logged);
        Iterator<User> o = otherUsers.iterator();
         while (o.hasNext()) {
             User userConsidered = o.next();             
               userContentBodyTable += "<tr>\n"
                + "             <td>" + userConsidered.getName() + "</td>\n"                     //User Name
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"member\" type=\"checkbox\"> </td>\n"    //Group Member 
                + "             <td> <input name=\""+userConsidered.getId()+"\" id=\"visible\" type=\"checkbox\"> </td>\n"    //Visible
                + "           </tr>\n";
         }
        
        
        
       try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            userContent = groupTableContent + GROUPS_CONTENT_HEAD_TABLE  + userContentBodyTable + userContentBodyTableEnd;
            HTML.printPage(out, GROUP_MANAGER_TITLE, "/forum", userContent);

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
        processRequest(request, response);
        DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
        User logged = (User) request.getSession().getAttribute("user");
        Group groupToEdit = manager.getGroupMadeByUser(logged);

        String newName = request.getParameter("change_group_name");
        System.out.println(newName);
        manager.changeGroupName(groupToEdit, newName);
        
        Map<String, String[]> m = request.getParameterMap();
        
        
        
        try (PrintWriter out = response.getWriter()) {
            String userContent = newName + "ciao";
            HTML.printPage(out, GROUP_MANAGER_TITLE, "/forum", userContent);

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
