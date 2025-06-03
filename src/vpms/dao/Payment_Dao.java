/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import vpms.database.MySqlConnection;
import vpms.model.PaymentData;
import java.sql.*;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author PRABHASH
 */
public class Payment_Dao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean addPayment (PaymentData payment) {
        Connection conn = mySql.openConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS payments ("
            + "payment_id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "parking_id INT , "
            + "vehicle_id INT, "
            + "user_id INT, "
            + "regular_price VARCHAR(10), "
            + "demand_price VARCHAR(10), "
            + "reservation_price VARCHAR(10),"
            + "extra_charge VARCHAR(10), "
            + "payment_status VARCHAR(10),"
            +"payment_time DATETIME"
            + ")";
        try{
            PreparedStatement stmnt = conn.prepareStatement (createTableSQL);
            stmnt.executeUpdate();
        }catch(Exception e){
            
            
        }
        String query ="INSERT INTO payment( parking_id, vehicle_id, user_id, regular_price, demand_price, reservation_price, extra_charge, payment_status, payment_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmnt = conn.prepareStatement (query);
            stmnt.setInt (1,payment.getPayment_id());
            stmnt.setInt(2,payment.getParking_id());
            stmnt.setInt(3,payment.getVehicle_id());
            stmnt.setInt(4,payment.getUser_id());
            stmnt.setString(5,payment.getRegularPrice());
            stmnt.setString(6,payment.getDemandPrice());
            stmnt.setString(7,payment.getReservationPrice());
            stmnt.setString(8,payment.getExtraCharge());
            stmnt.setString(9,payment.getPaymentStatus());
            stmnt.setString(10, payment.getPaymentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

     int  result = stmnt.executeUpdate();
                    return result>0;
              
        }catch(Exception e){
         return false;   
         
        }finally{
            mySql.closeConnection(conn);
        }
    }

    
    }

