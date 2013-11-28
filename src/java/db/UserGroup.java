/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

/**
 *
 * @author halfblood
 */
public class UserGroup {
    private int user_id;
    private int group_id;
    private boolean group_accepted;
    private boolean visible;

    
    public UserGroup(int user_id, int group_id, boolean accepted, boolean visible) {
        this.user_id = user_id;
        this.group_id = group_id;
        this.group_accepted = accepted;
        this.visible = visible;
    }
    
    public boolean isAccepted() {
        return group_accepted;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public int getUserId() {
        return user_id;
    }
    
    public int getGroupId() {
        return group_id;
    }
}