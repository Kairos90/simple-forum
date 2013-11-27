/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import db.DBManager;
import db.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
public class AvatarUploadPage extends HttpServlet {

    private final String TITLE = "AVATAR'S SETTING PAGE";

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // SETTING NEEDED ITEMS
            DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
            User logged = (User) request.getSession().getAttribute("user");
            int id = logged.getId();
            System.out.println(id + "");
            if (id == 0) {
                HTML.print404(out);
            } else {
                String content = "";
                String avatarDirName = request.getServletContext().getRealPath("/").replace("\\","/") + "static/avatars";
                // UPLOADING PROCEDURE
                RenameFile avatarRenamePolicy = new RenameFile(request, id);
                try {
                    MultipartRequest multipart = new MultipartRequest(request, avatarDirName, 1024 * 1024, avatarRenamePolicy);
                } catch(IOException ex) {
                    this.getServletContext().log(ex,"error reading or saving file!");
                }
                
                
                
                
                
                HTML.printPage(out, TITLE, "/forum", content);
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
        return "Short description";
    }// </editor-fold>
}

class RenameFile implements FileRenamePolicy {
    
    private HttpServletRequest request;
    private int userId;
    
    public RenameFile(HttpServletRequest r, int id) {
        request = r;
        userId = id;
    }

    @Override
    public File rename(File file) {
        File f = null;
        try {
            Path p = Files.move(file.toPath(), Paths.get(file.getParentFile().getAbsolutePath() + "/" + userId + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
            f = p.toFile();
        } catch (IOException ex) {
            Logger.getLogger(RenameFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    
    
}
