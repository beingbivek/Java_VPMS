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
         String createTableSQL = "CREATE TABLE IF NOT EXISTS parkings ("
        + "parkingId INT AUTO_INCREMENT PRIMARY KEY, "
        + "vehicleId INT(100), "
        + "slotId INT(100), "
        + "entryDateTime DATETIME NOT NULL, "
        + "entryNote VARCHAR(200) NOT NULL, "
        + "exitDateTime DATETIME, "
        + "parkingStatus VARCHAR(100), "
        + "parkingType VARCHAR(100), "
        + "exitNote VARCHAR(200),"
        + "penaltyApplied BOOLEAN"
        + ")";

         String query=  "INSERT INTO parkings (vehicleId,slotId,entryDateTime,entryNote,parkingStatus,parkingType,penaltyApplied) VALUES (?,?,?,?, ?,?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ParkingDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, parkingDetails.getVehicleId());
            pstmt.setString(2, parkingDetails.getSlotId());
            pstmt.setString(3, parkingDetails.getEntryDateTime());
            pstmt.setString(4, parkingDetails.getEntryNote());
            pstmt.setString(5, parkingDetails.getParkingStatus());
            pstmt.setString(6, parkingDetails.getParkingtype());
            pstmt.setBoolean(7, parkingDetails.isPenaltyApplied());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    
    }
          
    public boolean vehicleExit(ParkingDetails parkingDetails) {
        
    
        String query = "UPDATE parkings SET exitDateTime = ?, parkingStatus = ?, exitNote = ?, WHERE parkingId = ?"; 
        Connection conn= mySql.openConnection();
    try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,parkingDetails.getVehicleId());
            stmnt.setString(2,parkingDetails.getParkingStatus());
            stmnt.setString(3,parkingDetails.getExitDateTime());
            stmnt.setString(4,parkingDetails.getExitNote());
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            return false;
        }finally{
            mySql.closeConnection(conn);
    
    
    
    }
   }
    
}
    
 