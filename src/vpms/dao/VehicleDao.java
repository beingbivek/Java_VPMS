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
import vpms.model.VehicleData;

/**
 *
 * @author being
 */
public class VehicleDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerVehicle(VehicleData vehicleData){
        Connection conn= mySql.openConnection();
        String query=  "INSERT INTO vehicles (vehicletandp_id, vehicle_number, owner_name, owner_contact,created_at,updated_at) VALUES (?,?,?,?,?,?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vehicleData.getType());
            pstmt.setString(2, vehicleData.getVehicleNumber());
            pstmt.setString(3, vehicleData.getOwnerName());
            pstmt.setString(4, vehicleData.getOwnerContact());
            pstmt.setString(5, vehicleData.getCreatedAt());
            pstmt.setString(6, vehicleData.getUpdatedAt());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
          
          
          
    }
    public String[] showVehicleNumbers() {
        ArrayList<String> vehicleNumberList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT vehicle_number FROM vehicles";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                String number = result.getString("vehicle_number");
                vehicleNumberList.add(number);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        String[] vnum = vehicleNumberList.toArray(new String[0]);

        return vnum;
    }
    
    public List<VehicleData> findByNumberLike(String number) {
        List<VehicleData> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE vehicle_number LIKE ?";
        Connection c = mySql.openConnection();
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + number + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VehicleData v = new VehicleData(
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicletandp_id"),
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("owner_contact"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                    );
                    list.add(v);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            mySql.closeConnection(c);
        }
        return list;
    }
    
    public boolean deleteVehicleById(int id) {
        Connection conn = mySql.openConnection();
        String sql = "DELETE FROM vehicles WHERE vehicle_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }
    
    // Get vehicle by ID
    public VehicleData getVehicleById(int id) {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new VehicleData(
                        rs.getInt("vehicle_id"),
                        String.valueOf(rs.getInt("vehicletandp_id")),
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("owner_contact"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    // Update vehicle
    public boolean updateVehicle(VehicleData vehicle) {
        String sql = "UPDATE vehicles SET vehicletandp_id=?, vehicle_number=?, owner_name=?, owner_contact=?, updated_at=? WHERE vehicle_id=?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getType());
            ps.setString(2, vehicle.getVehicleNumber());
            ps.setString(3, vehicle.getOwnerName());
            ps.setString(4, vehicle.getOwnerContact());
            ps.setString(5, vehicle.getUpdatedAt());
            ps.setInt(6, vehicle.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }


    }
