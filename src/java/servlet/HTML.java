/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.PrintWriter;

/**
 *
 * @author paolo
 */
public class HTML {
    
    private static final String PRE_TITLE = 
            "<!doctype html>"
            + "<html>"
            + "<head>"
            + "<title>";
    private static final String POST_TITLE =
            "</title>"
            + "<meta charset=\"UTF-8\">"
            + "<meta name=\"viewport\" content=\"width=device-width\">"
            + "<link rel=\"stylesheet\" href=\"static/jquery.mobile-1.3.2.min.css\">"
            + "<script type=\"text/javascript\" src=\"static/jquery-1.9.1.min.js\"></script>"
            + "<script type=\"text/javascript\" src=\"static/jquery.mobile-1.3.2.min.js\"></script>"
            + "</head>"
            + "<body>"
            + "<div data-role=\"header\">"
            + "<h1>";
    private static final String POST_HEADER =
            "</h1>"
            + "</div>"
            + "<div data-role=\"content\">";
    private static final String BOTTOM =
            "</div>"
            + "</body>"
            + "</html>";
    
    public static void printPage(PrintWriter out, String title, String content) {
        out.print(PRE_TITLE);
        out.print(title);
        out.print(POST_TITLE);
        out.print(title);
        out.print(POST_HEADER);
        out.print(content);
        out.print(BOTTOM);
    }
    
}
