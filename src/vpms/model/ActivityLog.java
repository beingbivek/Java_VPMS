/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

import java.security.Timestamp;

/**
 *
 * @author PRABHASH
 */
public class ActivityLog {
    private int log_id;
    private int user_id;
    private String userType;
    private String action;
    private Timestamp timestamp;
    
    
    public ActivityLog() {
        
    }
    
    
    public ActivityLog (int userId, String userType, String action, String module, int relatedId) {
        this.user_id = userId;
        this.userType = userType;
        this.action = action;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
