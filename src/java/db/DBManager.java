/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paolo
 */
public class DBManager implements Serializable {

    private Connection connection;

    public DBManager() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.connection = DriverManager.getConnection("jdbc:derby://localhost:1527/SimpleForum", "nan", "nan");
    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User authenticate(String userName, String password) {
        User u = null;
        try {
            String query = "SELECT * FROM \"user\" WHERE user_name = ? AND user_password = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setString(1, userName);
                stm.setString(2, password);
                ResultSet res = stm.executeQuery();
                try {
                    if (res.next()) {
                        u = new User(res.getInt("user_id"), res.getString("user_name"));
                    }
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }
    
    public LinkedList<Group> getUserGroups(User u) {
        LinkedList<Group> g = new LinkedList<>();
        try {
            String query = "SELECT * FROM \"user_group\" NATURAL JOIN \"group\" WHERE user_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, u.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        g.add(
                                new Group(
                                    res.getInt("group_id"),
                                    res.getString("group_name"), 
                                    res.getInt("creator_id")
                                )
                        );
                    }
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    
    public LinkedList<Post> getGroupPosts(Group g) {
        LinkedList<Post> p = new LinkedList<>();
        try {
            String query = "SELECT * FROM \"post\" NATURAL JOIN \"user\" WHERE group_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, g.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        p.add(
                                new Post(
                                        res.getInt("post_id"),
                                        res.getString("post_text"),
                                        res.getDate("post_date"),
                                        new User(res.getInt("user_id"), res.getString("user_name"))
                                )
                        );
                    }
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public LinkedList<File> getPostFiles(Post p) {
        LinkedList<File> f = new LinkedList<>();
        try {
            String query = "SELECT * FROM \"file\" NATURAL JOIN \"post\" WHERE post_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, p.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        f.add(
                                new File(
                                        res.getString("file_name"),
                                        res.getString("file_mime"),
                                        res.getInt("file_size")
                                )
                        );
                    }
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }
    
    public Date getLatestPost(Group group) {
        Date date = null;
        
        try {
            String query = "SELECT MAX(post_date) FROM \"post\" WHERE group_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, group.getId());
                ResultSet res = stm.executeQuery();
                
                try {
                    res.next();
                    date = res.getDate("post_date");
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    
    public Group getGroup(int groupId) {
        Group target = null;
        
        try {
            String query = "SELECT * FROM \"group\" WHERE group_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, groupId);
                ResultSet res = stm.executeQuery();
                
                try {
                    res.next();
                    target = new Group(
                        res.getInt("group_id"),
                        res.getString("group_name"), 
                        res.getInt("creator_id")
                    );
                } finally {
                    res.close();
                }
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return target;
    }

}
