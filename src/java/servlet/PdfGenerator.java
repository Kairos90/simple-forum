/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.DBManager;
import db.Group;
import db.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pier DAgostino
 */
@WebServlet(name = "PdfGenerator", urlPatterns = {"/pdf-generator"})
public class PdfGenerator extends HttpServlet {

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
        Document report = new Document();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(report, baos);

            DBManager manager = (DBManager) getServletContext().getAttribute("dbmanager");
            Group groupToReport = manager.getGroup(Integer.parseInt(request.getParameter("id")));
            LinkedList<User> groupUsers = manager.getUsersForGroupAndVisible(groupToReport.getId());
            Timestamp lastPosted = manager.getLatestPost(groupToReport);
            int numberOfPosts = manager.getGroupPosts(groupToReport).size();
            String context = request.getServletContext().getRealPath("/");
            Iterator<User> groupIterator = groupUsers.iterator();

            report.open();
            // INSERTING DOCUMENT CONTENT AREA
            Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Font text = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Paragraph documentHead = new Paragraph(groupToReport.getName(), title);
            Paragraph newLine = new Paragraph(Chunk.NEWLINE);
            Paragraph latestPost = new Paragraph("Latest post inserted on: " + lastPosted, text);
            Paragraph postsNumberToReport = new Paragraph("Number of posts: " + numberOfPosts, text);

            //LAYOUT AND FINAL PARAGRAPH EDITING AREA
            report.add(documentHead);
            report.add(newLine);
            report.add(postsNumberToReport);
            report.add(newLine);
            report.add(latestPost);
            report.add(newLine);
            PdfPTable usersTable = new PdfPTable(5);

            // LOOP FOR SETTING TABLE
            while (groupIterator.hasNext()) {
                User u = groupIterator.next();
                String userName = u.getName();
                PdfPCell avatarCell = new PdfPCell(Image.getInstance(context + u.getAvatar(request)));
                avatarCell.setBorder(0);
                avatarCell.setPaddingBottom(10);
                avatarCell.setColspan(1);
                PdfPCell userNameCell = new PdfPCell(new Phrase(userName));
                userNameCell.setColspan(4);
                userNameCell.setBorder(0);
                userNameCell.setPaddingBottom(10);
                userNameCell.setPaddingLeft(10);
                usersTable.addCell(avatarCell);
                usersTable.addCell(userNameCell);
            }
            usersTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            report.add(usersTable);

            report.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            System.out.println("ci sono");
            try (OutputStream os = response.getOutputStream()) {
                baos.writeTo(os);
                os.flush();
                os.close();
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
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
