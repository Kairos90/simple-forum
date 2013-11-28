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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.servlet.http.Part;

/**
 *
 * @author Pier DAgostino
 */
public class AvatarUploadPage extends HttpServlet {

    private final String TITLE = "Choose your avatar";
    private final String form = "<form data-ajax=\"false\" enctype=\"multipart/form-data\" method=\"post\" action=\"/forum/avatar\">\n"
            + "					<ul data-role=\"listview\" data-inset=\"true\">\n"
            + "						<li data-role=\"fieldcontain\">\n"
            + "							<label for=\"photo\">Upload your avatar:</label>\n"
            + "							<input type=\"file\" id=\"photo\" name=\"avatar\">\n"
            + "						</li>\n"
            + "					</ul>\n"
            + "					<button type=\"submit\" data-inline=\"true\">Upload</button>	\n"
            + "				</form>\n";

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            HTML.printPage(out, TITLE, "/forum", form);
        } catch (IOException ex) {
            getServletContext().log(ex, "error reading file");
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // SETTING NEEDED ITEMS
            DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
            User logged = (User) request.getSession().getAttribute("user");
            int loggedUserId = logged.getId();
            String content = form;
            String avatarDirName = request.getServletContext().getRealPath("/static/avatars");
            // UPLOADING PROCEDURE
            try {
                handleUpload(request, "avatar", avatarDirName + File.separator + loggedUserId + ".jpg");
                content = "<h2>Avatar upload success</h2>" + form;
            } catch (Exception ex) {
                content = "<h2>An error occurred during the upload</h2>" + form;
                this.getServletContext().log(ex, "error saving file!");
            }
            HTML.printPage(out, TITLE, "/forum", content);
        }
    }
    
    public void handleUpload(HttpServletRequest request, String paramName, String newFilePathWithName) throws IOException, ServletException {
        final Part filePart = request.getPart(paramName);

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            out = new FileOutputStream(new File(newFilePathWithName));
            filecontent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            this.getServletContext().log("File{0}being uploaded to {1}");
        } catch (FileNotFoundException fne) {
            this.getServletContext().log(fne, "Problems during file upload. Error: {0}");
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
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
