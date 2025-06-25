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

    public boolean addReservation(ReservationData data) {
        String query = "CREATE TABLE IF NOT EXISTS reservations ("
                     + "reservation_id INT PRIMARY KEY AUTO_INCREMENT, "
                     + "vehicle_id INT, "
                     + "user_id INT, "
                     + "slot_id INT, "
                     + "vehicle_type VARCHAR(100), "
                     + "contact VARCHAR(100), "
                     + "entry_time VARCHAR(100), "
                     + "exit_time VARCHAR(100), "
                     + "duration VARCHAR(100), "
                     + "status VARCHAR(100), "
                     + "payment_status VARCHAR(100), "
                     + "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE, "
                     + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, "
                     + "FOREIGN KEY (slot_id) REFERENCES slots(id) ON DELETE CASCADE"
                     + ")";
        String insert = "INSERT INTO reservations (vehicle_id, user_id, slot_id, vehicle_type, contact, entry_time, "
                      + "exit_time, duration, status, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = MySqlConnection.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setInt(1, data.getVehicleId());
            ps.setInt(2, data.getUserId());
            ps.setInt(3, data.getSlotId());
            ps.setString(4, data.getVehicleType());
            ps.setString(5, data.getContact());
            ps.setString(6, data.getEntryTime());
            ps.setString(7, data.getExitTime());
            ps.setString(8, data.getDuration());
            ps.setString(9, data.getStatus());
            ps.setString(10, data.getPaymentStatus());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ReservationData> getAllReservations() {
        List<ReservationData> list = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReservationData data = new ReservationData(
                    rs.getInt("reservation_id"),
                    rs.getInt("vehicle_id"),
                    rs.getInt("user_id"),
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.isEmpty() ? null : list;
    }

    public boolean updateReservation(ReservationData data) {
        String query = "UPDATE reservations SET vehicle_id = ?, user_id = ?, slot_id = ?, vehicle_type = ?, contact = ?, "
                     + "entry_time = ?, exit_time = ?, duration = ?, status = ?, payment_status = ? WHERE reservation_id = ?";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, data.getVehicleId());
            ps.setInt(2, data.getUserId());
            ps.setInt(3, data.getSlotId());
            ps.setString(4, data.getVehicleType());
            ps.setString(5, data.getContact());
            ps.setString(6, data.getEntryTime());
            ps.setString(7, data.getExitTime());
            ps.setString(8, data.getDuration());
            ps.setString(9, data.getStatus());
            ps.setString(10, data.getPaymentStatus());
            ps.setInt(11, data.getReservationId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}