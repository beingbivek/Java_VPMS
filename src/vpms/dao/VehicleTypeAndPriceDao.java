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

    public boolean addVehicleTypeAndPrice(VehicleTypeAndPriceData vehicle) {
        Connection conn = mySql.openConnection();
        
        String insertSQL = "INSERT INTO vehicle_type_and_price(vehicle_type, reservation_price, regular_price, demand_price, extra_charge, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setString(1, vehicle.getVehicleType());
            stmt.setString(2, vehicle.getReservationPrice());
            stmt.setString(3, vehicle.getRegularPrice());
            stmt.setString(4, vehicle.getDemandPrice());
            stmt.setString(5, vehicle.getExtraCharge());
            stmt.setString(6, vehicle.getStatus());

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
    }

    public List<VehicleTypeAndPriceData> showVehicleTypeAndPrices() {
        List<VehicleTypeAndPriceData> list = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vehicle_type_and_price";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                VehicleTypeAndPriceData data = new VehicleTypeAndPriceData(
                        result.getInt("id"),
                        result.getString("vehicle_type"),
                        result.getString("reservation_price"),
                        result.getString("regular_price"),
                        result.getString("demand_price"),
                        result.getString("extra_charge"),
                        result.getString("status")
                );
                list.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return list.isEmpty() ? null : list;
    }

    public boolean updateVehicleTypeAndPrice(VehicleTypeAndPriceData vehicle) {
        Connection conn = mySql.openConnection();
        String sql = "UPDATE vehicle_type_and_price SET vehicle_type=?, reservation_price=?, regular_price=?, demand_price=?, extra_charge=?, status=? WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, vehicle.getVehicleType());
            stmt.setString(2, vehicle.getReservationPrice());
            stmt.setString(3, vehicle.getRegularPrice());
            stmt.setString(4, vehicle.getDemandPrice());
            stmt.setString(5, vehicle.getExtraCharge());
            stmt.setString(6, vehicle.getStatus());
            stmt.setInt(7, vehicle.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deleteVehicleTypeAndPrice(int id) {
        Connection conn = mySql.openConnection();
        String sql = "DELETE FROM vehicle_type_and_price WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            mySql.closeConnection(conn);
        }
    }

    public List<VehicleTypeAndPriceData> searchVehicleTypes(String term) {
        List<VehicleTypeAndPriceData> list = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vehicle_type_and_price WHERE vehicle_type LIKE ? OR status LIKE ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + term + "%");
            stmt.setString(2, "%" + term + "%");

            ResultSet rs = stmt.executeQuery();

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
        } finally {
            mySql.closeConnection(conn);
        }

        return list.isEmpty() ? null : list;
    }
    public VehicleTypeAndPriceData findById(int id) throws SQLException {
    String sql = "SELECT * FROM vehicle_type_and_price WHERE id=?";
    try (Connection c = mySql.openConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new VehicleTypeAndPriceData(
                            rs.getInt("id"),
                            rs.getString("vehicle_type"),
                            rs.getString("regular_price"),
                            rs.getString("demand_price"),
                            rs.getString("reservation_price"),
                            rs.getString("extra_charge"),
                            rs.getString("status"));
                }
            }
        }
        throw new SQLException("VehicleType id not found: " + id);
    }
}
