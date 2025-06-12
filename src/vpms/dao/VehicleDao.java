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
import vpms.model.VehicleData;

/**
 *
 * @author being
 */
public class VehicleDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerVehicle(VehicleData vehicleData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicles ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "type VARCHAR(50) NOT NULL, "
            + "vehicle_number VARCHAR(100) NOT NULL, "
            + "owner_name VARCHAR(100) NOT NULL, "
            + "owner_contact VARCHAR(50) NOT NULL, "
            + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "
            + "updated_at DATETIME"
            + ")";
         String query=  "INSERT INTO vehicles (type, vehicle_number, owner_name, owner_contact,created_at,updated_at) VALUES (?,?,?,?,?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(VehicleDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehicleData.getType());
            pstmt.setString(2, vehicleData.getVehicleNumber());
            pstmt.setString(3, vehicleData.getOwnerName());
            pstmt.setString(4, vehicleData.getOwnerContact());
            pstmt.setString(5, vehicleData.getCreatedAt());
            pstmt.setString(6, vehicleData.getUpdatedAt());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
          
          
          
    }
    public List<String> showVehicleNumbers() {
    List<String> vehicleNumberList = new ArrayList<>();
    Connection conn = mySql.openConnection();
    String sql = "SELECT vehicle_number FROM vehicles";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
            String number = result.getString("vehicle_number");
            vehicleNumberList.add(number);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    } finally {
        mySql.closeConnection(conn);
    }

    return vehicleNumberList;
    }
}
