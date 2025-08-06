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

        String query = "INSERT INTO activity_log (user_id, action) VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, log.getUser_id());
            stmt.setString(2, log.getAction());

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
        String sql = "SELECT * FROM activity_log ORDER BY log_id DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                ActivityLog log = new ActivityLog(
                    result.getInt("user_id"),
                    result.getString("action"),
                    result.getString("timestamp")
                );
                log.setLog_id(result.getInt("log_id"));
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
        String sql = "SELECT * FROM activity_log ORDER BY log_id DESC LIMIT ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, number);

           
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                ActivityLog log = new ActivityLog(
                    result.getInt("user_id"),
                    result.getString("action"),
                    result.getString("timestamp")
                );
                log.setLog_id(result.getInt("log_id"));
                logList.add(log);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }

            return logList;
        }
    public List<ActivityLog> searchActivities(String keyword) {
        List<ActivityLog> logList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM activity_log WHERE " +
                "CAST(log_id AS CHAR) LIKE ? OR " +
                "CAST(user_id AS CHAR) LIKE ? OR " +
                "action LIKE ? OR " +
                "DATE_FORMAT(timestamp, '%Y-%m-%d %H:%i:%s') LIKE ? ORDER BY log_id DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) pstmt.setString(i, like);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                ActivityLog log = new ActivityLog(
                    result.getInt("user_id"),
                    result.getString("action"),
                    result.getString("timestamp")
                );
                log.setLog_id(result.getInt("log_id"));
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