/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.*;
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

    // Add new reservation (also creates table if not exists)
    public void addReservation(ReservationData data) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reservations ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_id INT, "
                + "vehicle_id INT, "
                + "slot_id INT, "
                + "reservation_time DATETIME, "
                + "status VARCHAR(20), "
                + "FOREIGN KEY (user_id) REFERENCES vpmsUsers(id), "
                + "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id), "
                + "FOREIGN KEY (slot_id) REFERENCES slots(id))";

        String insertSQL = "INSERT INTO reservations (user_id, vehicle_id, slot_id, reservation_time, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = mySql.openConnection()) {
            try (PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                createStmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, data.getUserId());
                pstmt.setInt(2, data.getVehicleId());
                pstmt.setInt(3, data.getSlotId());
                pstmt.setString(4, data.getReservationTime());
                pstmt.setString(5, data.getStatus());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Show all reservations
    public List<ReservationData> getAllReservations() {
        List<ReservationData> list = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReservationData data = new ReservationData(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("vehicle_id"),
                        rs.getInt("slot_id"),
                        rs.getString("reservation_time"),
                        rs.getString("status")
                );
                list.add(data);
            }
            return list.isEmpty() ? null : list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    // Update reservation (optional)
    public void updateReservation(ReservationData data) {
        String query = "UPDATE reservations SET user_id=?, vehicle_id=?, slot_id=?, reservation_time=?, status=? WHERE id=?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, data.getUserId());
            pstmt.setInt(2, data.getVehicleId());
            pstmt.setInt(3, data.getSlotId());
            pstmt.setString(4, data.getReservationTime());
            pstmt.setString(5, data.getStatus());
            pstmt.setInt(6, data.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
