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
    private String action;
    private String timestamp;
    
    
    public ActivityLog() {
        
    }
    
    public ActivityLog (int userId, String action) {
        this.user_id = userId;
        this.action = action;
    }

    
    public ActivityLog (int userId, String action, String timestamp) {
        this.user_id = userId;
        this.action = action;
        this.timestamp = timestamp;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
