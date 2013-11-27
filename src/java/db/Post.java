/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.sql.Date;

/**
 *
 * @author paolo
 */
public class Post {
    private int id;
    private String text;
    private Date date;
    private User creator;
    
    public Post(int id, String text, Date date, User creator) {
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
