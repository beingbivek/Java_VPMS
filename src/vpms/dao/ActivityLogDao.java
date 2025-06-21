/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vpms.database.MySqlConnection;
import vpms.model.ActivityLog;


/**
 *
 * @author PRABHASH
 */
public class ActivityLogDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean logActivity(ActivityLog log) {
        Connection conn = mySql.openConnection();

        // Create table if not exists
        String createTableSQL = "CREATE TABLE IF NOT EXISTS activity_log ("
                + "log_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_id INT, "
                + "user_type VARCHAR(20), "
                + "action VARCHAR(255), "
                + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (user_id) REFERENCES vpmsUsers(user_id)"
                + ")";

        try {
            PreparedStatement stmt = conn.prepareStatement(createTableSQL);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert log entry
        String query = "INSERT INTO activity_log (user_id, user_type, action) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, log.getUser_id());
            stmt.setString(2, log.getUserType());
            stmt.setString(3, log.getAction());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public List<ActivityLog> showActivities(){
        List<ActivityLog> logList= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM activity_log";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                ActivityLog log = new ActivityLog(
                    result.getInt("user_id"),
                    result.getString("user_type"),
                    result.getString("action"),
                    result.getString("timestamp")
                );
                log.setLog_id(result.getInt("id"));
                logList.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }

            return logList;
        }
    
    
    
   public List<ActivityLog> fetchLast(int number){
        List<ActivityLog> logList= new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM activity_log LIMIT ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, number);

           
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                ActivityLog log = new ActivityLog(
                    result.getInt("user_id"),
                    result.getString("user_type"),
                    result.getString("action"),
                    result.getString("timestamp")
                );
                log.setLog_id(result.getInt("id"));
                logList.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }

            return logList;
        }
     
    
}