/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vpms.database.MySqlConnection;
import vpms.model.VehicleTypeAndPriceData;

/**
 *
 * @author Rupes
 */
public class VehicleTypeAndPriceDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerTypeAndPrice(VehicleTypeAndPriceData vehicleTypeAndPriceData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicletypeandprice ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "vehicle_type VARCHAR(50) NOT NULL, "
            + "reservation_price VARCHAR(10) NOT NULL, "
            + "regular_price VARCHAR(10) NOT NULL, "
            + "demand_price VARCHAR(10) NOT NULL, "
            + "extra_charge VARCHAR(10) NOT NULL, "
            + "status VARCHAR(10) NOT NULL "
            + ")";
         String query=  "INSERT INTO vehicletypeandprice (vehicle_type, reservation_price, regular_price, demand_price,extra_charge,status) VALUES (?,?,?,?,?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(VehicleDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehicleTypeAndPriceData.getVehicleType());
            pstmt.setString(2, vehicleTypeAndPriceData.getReservationPrice());
            pstmt.setString(3, vehicleTypeAndPriceData.getRegularPrice());
            pstmt.setString(4, vehicleTypeAndPriceData.getDemandPrice());
            pstmt.setString(5, vehicleTypeAndPriceData.getExtraCharge());
            pstmt.setString(6, vehicleTypeAndPriceData.getStatus());
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
