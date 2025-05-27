/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vpms.database.MySqlConnection;


/**
 *
 * @author PRABHASH
 */
public class DefaultAdminSeeder {
     public static void insertDefaultAdminIfNotExists() {
        String defaultEmail = "admin@vpms.com";
        String defaultPassword = "adminvpms123";
        String userType = "admin";

        try (Connection conn = MySqlConnection.getConnection();
) {
            String checkQuery = "SELECT * FROM vpmsUsers WHERE type = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, userType);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                String insertQuery = "INSERT INTO vpmsUsers (email, password, type) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, defaultEmail);
                insertStmt.setString(2, defaultPassword);
                insertStmt.setString(3, userType);
                insertStmt.executeUpdate();
                System.out.println(" Default admin inserted.");
            } else {
                System.out.println("️ Admin already exists.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
