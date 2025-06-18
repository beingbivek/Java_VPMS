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
import vpms.model.ReservationData;

/**
 *
 * @author PRABHASH
 */
public class ReservationDao {
    MySqlConnection mySql = new MySqlConnection();

    // Add reservation (with table creation and FK references)
    public void addReservation(ReservationData data) {

        String insertSQL = "INSERT INTO reservations (user_id, vehicle_id, slot_id, reservation_time, status, duration, payment_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = mySql.openConnection()) {

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, data.getUserId());
                pstmt.setInt(2, data.getVehicleId());
                pstmt.setInt(3, data.getSlotId());
                pstmt.setString(4, data.getReservationTime());
                pstmt.setString(5, data.getStatus());
                pstmt.setString(6, data.getDuration());
                pstmt.setString(7, data.getPaymentStatus());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all reservations
    public List<ReservationData> getAllReservations() {
        List<ReservationData> list = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int vehicleId = rs.getInt("vehicle_id");
                int slotId = rs.getInt("slot_id");
                String reservationTime = rs.getString("reservation_time");
                String status = rs.getString("status");
                String duration = rs.getString("duration");
                String paymentStatus = rs.getString("payment_status");

                ReservationData data = new ReservationData(
                        id, userId, vehicleId, slotId, reservationTime, status, duration, paymentStatus
                );
                list.add(data);
            }
            return list.isEmpty() ? null : list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update reservation
    public void updateReservation(ReservationData data) {
        String query = "UPDATE reservations SET user_id=?, vehicle_id=?, slot_id=?, reservation_time=?, status=?, duration=?, payment_status=? WHERE id=?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, data.getUserId());
            pstmt.setInt(2, data.getVehicleId());
            pstmt.setInt(3, data.getSlotId());
            pstmt.setString(4, data.getReservationTime());
            pstmt.setString(5, data.getStatus());
            pstmt.setString(6, data.getDuration());
            pstmt.setString(7, data.getPaymentStatus());
            pstmt.setInt(8, data.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete reservation
    public void deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE id = ?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
