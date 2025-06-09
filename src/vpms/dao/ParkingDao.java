/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vpms.database.MySqlConnection;
import vpms.model.ParkingDetails;


/**
 *
 * @author Chandani
 */
public class ParkingDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerParkingUser(ParkingDetails parkingDetails) {

        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS vpmsUsers ("
        + "parkingId INT AUTO_INCREMENT PRIMARY KEY, "
        + "vehicleId INT(100), "
        + "slotId INT(100), "
        + "entryDate DATETIME, "
        + "entryTime DATETIME NOT NULL, "
        + "entryNote VARCHAR(200) NOT NULL, "
        + "exitTime DATETIME, "
        + "parkingStatus VARCHAR(100), "
        + "parkingType VARCHAR(100), "
        + "exitNote VARCHAR(200)"
        + ")";

         String query=  "INSERT INTO parkingDetails (parkingId,vehicleId,slotId,entryTime,entryNote,parkingStatus,parkingType) VALUES (?,?,?,?, ?,?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ParkingDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, parkingDetails.getVehicleId());
            pstmt.setString(2, parkingDetails.getSlotId());
            pstmt.setString(3, parkingDetails.getEntryDate());
            pstmt.setString(4, parkingDetails.getEntryTime());
            pstmt.setString(5, parkingDetails.getParkingStatus());
            pstmt.setString(6, parkingDetails.getEntryNote());
            pstmt.setString(7, parkingDetails.getParkingType());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    
    
//    public boolean registerParkingUser(ParkingDetails parkingDetails) {
//        String query=  "INSERT INTO parkingDetails (parkingId,vehicleId,slotId,exitTime,exitNote,parkingtype) VALUES (?,?,?,?,?,?)";
    }
}
    
  
 
