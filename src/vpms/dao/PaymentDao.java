/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import vpms.database.MySqlConnection;
import vpms.model.PaymentData;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author PRABHASH
 */
public class PaymentDao {
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
            +"payment_time DATETIME,"
            + "FOREIGN KEY (parking_id) REFERENCES parking(parking_id),"
            + "FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id),"
            + "FOREIGN KEY (user_id) REFERENCES vpmsUsers(user_id)"
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

    public List<PaymentData> showPayments() {
        List<PaymentData> paymentList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM payment";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                PaymentData payment = new PaymentData(
                        result.getInt("payment_id"),
                        result.getInt("parking_id"),
                        result.getInt("vehicle_id"),
                        result.getInt("user_id"),
                        result.getString("regular_price"),
                        result.getString("demand_price"),
                        result.getString("reservation_price"),
                        result.getString("extra_charge"),
                        result.getString("payment_status"),
                        result.getTimestamp("payment_time").toLocalDateTime() 
                );
                paymentList.add(payment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return paymentList;
    }

    public boolean updatePayment(PaymentData payment) {
        Connection conn = mySql.openConnection();
        String sql = "UPDATE payment SET parking_id=?, vehicle_id=?, user_id=?, regular_price=?, demand_price=?, reservation_price=?, extra_charge=?, payment_status=?, payment_time=? WHERE payment_id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, payment.getParking_id());
            stmt.setInt(2, payment.getVehicle_id());
            stmt.setInt(3, payment.getUser_id());
            stmt.setString(4, payment.getRegularPrice());
            stmt.setString(5, payment.getDemandPrice());
            stmt.setString(6, payment.getReservationPrice());
            stmt.setString(7, payment.getExtraCharge());
            stmt.setString(8, payment.getPaymentStatus());
            stmt.setTimestamp(9, Timestamp.valueOf(payment.getPaymentTime())); // ✅ Timestamp
            stmt.setInt(10, payment.getPayment_id());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deletePayment(int paymentId) {
        Connection conn = mySql.openConnection();
        String sql = "DELETE FROM payment WHERE payment_id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, paymentId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
    
    

