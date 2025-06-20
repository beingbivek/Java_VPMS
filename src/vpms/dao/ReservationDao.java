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

    // Add reservation (with table creation and FK references)
    public void addReservation(ReservationData data) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS reservations ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "vehicle_id INT, "
                + "slot_id INT, "
                + "vehicle_type VARCHAR(50), "
                + "contact VARCHAR(30), "
                + "entry_time VARCHAR(50), "
                + "exit_time VARCHAR(50), "
                + "duration VARCHAR(30), "
                + "status VARCHAR(20), "
                + "payment_status VARCHAR(20), "
                + "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id), "
                + "FOREIGN KEY (slot_id) REFERENCES slot_instances(id))";

        String insertSQL = "INSERT INTO reservations (vehicle_id, slot_id, vehicle_type, contact, entry_time, exit_time, duration, status, payment_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = mySql.openConnection()) {
            try (PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                createStmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, data.getVehicleId());
                pstmt.setInt(2, data.getSlotId());
                pstmt.setString(3, data.getVehicleType());
                pstmt.setString(4, data.getContact());
                pstmt.setString(5, data.getEntryTime());
                pstmt.setString(6, data.getExitTime());
                pstmt.setString(7, data.getDuration());
                pstmt.setString(8, data.getStatus());
                pstmt.setString(9, data.getPaymentStatus());
                pstmt.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // 2. Fetch all reservations
    public List<ReservationData> getAllReservations() {
        List<ReservationData> list = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReservationData data = new ReservationData(
                        rs.getInt("id"),
                        rs.getInt("vehicle_id"),
                        rs.getInt("slot_id"),
                        rs.getString("vehicle_type"),
                        rs.getString("contact"),
                        rs.getString("entry_time"),
                        rs.getString("exit_time"),
                        rs.getString("duration"),
                        rs.getString("status"),
                        rs.getString("payment_status")
                );
                list.add(data);
            }
            return list.isEmpty() ? null : list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // 3. Update reservation
    public void updateReservation(ReservationData data) {
        String query = "UPDATE reservations SET vehicle_id=?, slot_id=?, vehicle_type=?, contact=?, entry_time=?, exit_time=?, duration=?, status=?, payment_status=? WHERE id=?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, data.getVehicleId());
            pstmt.setInt(2, data.getSlotId());
            pstmt.setString(3, data.getVehicleType());
            pstmt.setString(4, data.getContact());
            pstmt.setString(5, data.getEntryTime());
            pstmt.setString(6, data.getExitTime());
            pstmt.setString(7, data.getDuration());
            pstmt.setString(8, data.getStatus());
            pstmt.setString(9, data.getPaymentStatus());
            pstmt.setInt(10, data.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            mySql.closeConnection(conn);
        }
    }

    // 4. Delete reservation
    public void deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Search reservation
    public List<ReservationData> searchReservations(String term) {
        List<ReservationData> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE status LIKE ? OR payment_status LIKE ? OR vehicle_type LIKE ? OR contact LIKE ?";

        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + term + "%");
            stmt.setString(2, "%" + term + "%");
            stmt.setString(3, "%" + term + "%");
            stmt.setString(4, "%" + term + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ReservationData data = new ReservationData(
                            rs.getInt("id"),
                            rs.getInt("vehicle_id"),
                            rs.getInt("slot_id"),
                            rs.getString("vehicle_type"),
                            rs.getString("contact"),
                            rs.getString("entry_time"),
                            rs.getString("exit_time"),
                            rs.getString("duration"),
                            rs.getString("status"),
                            rs.getString("payment_status")
                    );
                    list.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.isEmpty() ? null : list;
    }
}

