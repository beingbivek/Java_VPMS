/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vpms.database.MySqlConnection;
import vpms.model.ParkingDetails;
import java.sql.ResultSet;
import vpms.model.ParkedDetails;



/**
 *
 * @author Chandani
 */
public class ParkingDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean registerParkingUser(ParkingDetails parkingDetails) {
        Connection conn= mySql.openConnection();

        String query=  "INSERT INTO parkings (vehicleId,slotInstanceId,entryDateTime,entryNote,parkingStatus,parkingType) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, parkingDetails.getVehicleId());
            pstmt.setInt(2, parkingDetails.getSlotInstanceId());
            pstmt.setString(3, parkingDetails.getEntryDateTime());
            pstmt.setString(4, parkingDetails.getEntryNote());
            pstmt.setString(5, parkingDetails.getParkingStatus());
            pstmt.setString(6, parkingDetails.getParkingtype());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }

    public boolean vehicleExit(ParkingDetails parkingDetails) {
        String query = "UPDATE parkings SET exitDateTime = ?, parkingStatus = ?, exitNote = ?, penaltyApplied = ? WHERE parkingId = ?";
        Connection conn= mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, parkingDetails.getExitDateTime());
            stmnt.setString(2, parkingDetails.getParkingStatus());
            stmnt.setString(3, parkingDetails.getExitNote());
            stmnt.setBoolean(4, parkingDetails.isPenaltyApplied());
            stmnt.setInt(5, parkingDetails.getParkingId());
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch(Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public int getTotalVehicleEntryCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM parkings";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Vehicle entry count error: " + ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }

    public int getCurrentlyParkedCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM parkings WHERE parkingStatus = 'Parked'";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Currently parked count error: " + ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }

    public int getExitedVehicleCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM parkings WHERE parkingStatus = 'Exited'";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Exited vehicle count error: " + ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }
    
    // In ParkingDao.java
    public ParkedDetails getActiveParkingBySlotInstanceId(int instanceId) {
        String sql = """
            SELECT p.entryDateTime, p.entryNote, v.vehicle_number, v.owner_name, v.owner_contact
            FROM parkings p
            JOIN vehicles v ON p.vehicle_id = v.vehicle_id
            WHERE p.instance_id = ? AND (p.parkingStatus = 'Parked' OR p.exitDateTime IS NULL)
            ORDER BY p.entryDateTime DESC LIMIT 1
        """;
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, instanceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ParkedDetails details = new ParkedDetails();
                    details.setEntryDateTime(rs.getString("entryDateTime"));
                    details.setEntryNote(rs.getString("entryNote"));
                    details.setVehicleNumber(rs.getString("vehicle_number"));
                    details.setOwnerName(rs.getString("owner_name"));
                    details.setOwnerContact(rs.getString("owner_contact"));
                    return details;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

} 

 