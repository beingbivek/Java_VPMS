/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;
import vpms.database.MySqlConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpms.model.VehicleTypeAndPriceData;
/**
 *
 * @author PRABHASH
 */
public class VehicleTypeAndPriceDao {
    MySqlConnection mySql = new MySqlConnection();

    public VehicleTypeAndPriceDao() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS vehicle_type_price ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "vehicle_type VARCHAR(50), "
                + "reservation_price VARCHAR(10), "
                + "regular_price VARCHAR(10), "
                + "demand_price VARCHAR(10), "
                + "extra_charge VARCHAR(10), "
                + "status VARCHAR(10))";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insert(VehicleTypeAndPriceData data) {
        String sql = "INSERT INTO vehicle_type_price (vehicle_type, reservation_price, regular_price, demand_price, extra_charge, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, data.getVehicleType());
            stmt.setString(2, data.getReservationPrice());
            stmt.setString(3, data.getRegularPrice());
            stmt.setString(4, data.getDemandPrice());
            stmt.setString(5, data.getExtraCharge());
            stmt.setString(6, data.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VehicleTypeAndPriceData> getAll() {
        List<VehicleTypeAndPriceData> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicle_type_price";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                VehicleTypeAndPriceData data = new VehicleTypeAndPriceData(
                        rs.getInt("id"),
                        rs.getString("vehicle_type"),
                        rs.getString("reservation_price"),
                        rs.getString("regular_price"),
                        rs.getString("demand_price"),
                        rs.getString("extra_charge"),
                        rs.getString("status")
                );
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(VehicleTypeAndPriceData data) {
        String sql = "UPDATE vehicle_type_price SET vehicle_type=?, reservation_price=?, regular_price=?, demand_price=?, extra_charge=?, status=? WHERE id=?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, data.getVehicleType());
            stmt.setString(2, data.getReservationPrice());
            stmt.setString(3, data.getRegularPrice());
            stmt.setString(4, data.getDemandPrice());
            stmt.setString(5, data.getExtraCharge());
            stmt.setString(6, data.getStatus());
            stmt.setInt(7, data.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM vehicle_type_price WHERE id=?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
