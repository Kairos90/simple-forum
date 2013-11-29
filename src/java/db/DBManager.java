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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
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

    public LinkedList<GroupFile> getPostFiles(Post p) {
        LinkedList<GroupFile> f = new LinkedList<>();
        try {
            String query = "SELECT * FROM \"file\" NATURAL JOIN \"post\" WHERE post_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, p.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        f.add(
                                new GroupFile(
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
            String query = "SELECT MAX(post_date) AS max_date FROM \"post\" WHERE group_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, group.getId());
                ResultSet res = stm.executeQuery();

                try {
                    res.next();
                    date = res.getDate("max_date");
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

    public LinkedList<Group> getInvites(User user) {
        LinkedList<Group> u = new LinkedList<>();
        try {
            String query = "SELECT * FROM \"user_group\" NATURAL JOIN \"group\" WHERE user_id = ? AND group_accepted = 0";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, user.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        u.add(
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
        return u;
    }

    public LinkedList<User> getUsersForGroupAndVisible(User user) {
        LinkedList<User> u = new LinkedList<>();
        try {
            //togliere il creatore del gruppo dalla richiesta
            String query = "SELECT * FROM (SELECT * FROM \"group\" NATURAL JOIN \"user_group\" WHERE creator_id = ? AND visible = TRUE) t natural join \"user\"";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, user.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        u.add(
                                new User(
                                        res.getInt("user_id"),
                                        res.getString("user_name")
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
        return u;
    }

    public LinkedList<User> getUsersForGroupAndNotVisible(User user) {
        LinkedList<User> u = new LinkedList<>();
        try {
            String query = "SELECT * FROM (SELECT * FROM \"group\" NATURAL JOIN \"user_group\" WHERE creator_id = ? AND visible = FALSE) t natural join \"user\"";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, user.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        u.add(
                                new User(
                                        res.getInt("user_id"),
                                        res.getString("user_name")
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
        return u;
    }

    public LinkedList<User> getUsersNotInGroup(User user) {
        LinkedList<User> u = new LinkedList<>();
        try {
            String query = "select * from (select * from \"user_group\" natural join \"group\" where creator_id = ?) t natural right outer join \"user\" where t.user_id IS NULL";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, user.getId());
                ResultSet res = stm.executeQuery();
                try {
                    while (res.next()) {
                        u.add(
                                new User(
                                        res.getInt("user_id"),
                                        res.getString("user_name")
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
        return u;
    }

    public Group getGroupMadeByUser(User user) {
        Group u = null;
        try {
            String query = "SELECT * FROM \"group\" WHERE creator_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(1, user.getId());
                ResultSet res = stm.executeQuery();
                try {
                    res.next();
                    u = new Group(
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
        return u;
    }

    public void changeGroupName(Group u, String name) {
        try {
            String query = "UPDATE \"group\" SET group_name = ? WHERE group_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setString(1, name);
                stm.setInt(2, u.getId());
                stm.executeQuery();
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMyGroupValues(Group u, Map<String, String[]> m) {
        try {
            String query = "UPDATE \"user_group\" SET group_accepted = ?, visible = ? WHERE group_id = ? AND user_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            try {
                stm.setInt(3, u.getId());
                for (Map.Entry<String, String[]> entry : m.entrySet()) {
                    String key = entry.getKey();
                    String[] value = entry.getValue();
                    // ...
                }
//                stm.setBoolean(1,m.get(group_accepted));
//                stm.setBoolean(2,m.get("visible"));
//                stm.setInt(4,)
                stm.executeQuery();
            } finally {
                stm.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
