/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

/**
 *
 * @author paolo
 */
public class Group {
    private String name;
    private int id;
    private int creator;
    
    public Group(int id, String name, int creator) {
        this.name = name;
        this.id = id;
        this.creator = creator;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
}
