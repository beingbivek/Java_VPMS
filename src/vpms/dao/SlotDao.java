/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vpms.database.MySqlConnection;
import vpms.model.SlotData;
/**
 *
 * @author Rupes
 */
public class SlotDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerSlot(SlotData slotData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS slots ("
            + "slot_id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "vehicletandp_id INT NOT NULL, "
            + "number_of_slot INT NOT NULL, "
            + "level_number INT NOT NULL "
            + ")";
         String query=  "INSERT INTO vehicles (vehicletandp, number_of_slot,level_number) VALUES (?,?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SlotDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, slotData.getVehicletandp());
            pstmt.setInt(2, slotData.getNumber_of_slot());
            pstmt.setInt(3, slotData.getLevel_number());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    }
}
