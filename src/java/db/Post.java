/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.sql.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author paolo
 */
public class Post {
    private int id;
    private String text;
    private Date date;
    private User creator;
    
    public Post(int id, String text, Date date, User creator, HashMap<String, GroupFile> groupFiles, Group group) {
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll(">", "&gt;");
        /*
        Pattern p = Pattern.compile("\\$\\$([^\\s]+)\\$\\$");
        Matcher m = p.matcher(text);
        String filesPath = "/static/files/" + group.getId() + "/";
        while(m.find()) {
            String g = m.group();
            String rep;
            if(groupFiles.get(g) != null) {
                rep = "<a href=\"" + filesPath + g + "\">" + g + "</a>";
            } else {
                rep = "<a href=\"" + g + "\">" + g + "</a>";
            }
            m.replaceAll(rep);
        }
        */
        this.id = id;
        this.text = text;
        this.date = date;
        this.creator = creator;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getText() {
        return text;
    }
    
    public Date getDate() {
        return date;
    }
    
    public User getCreator() {
        return creator;
    }
}
