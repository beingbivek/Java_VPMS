package vpms.dao;

import vpms.database.MySqlConnection;
import vpms.model.SlotData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for the `slots` table
 * Columns:
 *   slot_id         INT  PK  AI
 *   vehicletandp_id INT      NOT NULL   – FK → vehicle_type_and_price(id)
 *   number_of_slot  INT      NOT NULL
 *   level_number    INT      NOT NULL
 *
 * All methods throw SQLException so the caller can decide how to handle /
 * display the error.
 */
public class SlotDao {

    private final MySqlConnection mySql = new MySqlConnection();

    /* ===================================================== *
     *  C R E A T E                                          *
     * ===================================================== */
    public int insertReturnId(SlotData s) {
    String sql = "INSERT INTO slots(vehicletandp_id, number_of_slot, level_number) VALUES (?,?,?)";
    Connection conn = mySql.openConnection();
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getVehicletandp());
            ps.setInt(2, s.getNumber_of_slot());
            ps.setInt(3, s.getLevel_number());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return 0;
    }

    /* ===================================================== *
     *  R E A D                                              *
     * ===================================================== */
    public SlotData findById(int id) {
        String sql = "SELECT * FROM slots WHERE slot_id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;   // not found
    }

    public List<SlotData> findAll() {
        List<SlotData> list = new ArrayList<>();
        String sql = "SELECT * FROM slots";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return list;
    }

    /* ===================================================== *
     *  U P D A T E                                          *
     * ===================================================== */
    public boolean update(SlotData s) {
        String sql = """
            UPDATE slots SET
                vehicletandp_id = ?,
                number_of_slot  = ?,
                level_number    = ?
            WHERE slot_id = ?
            """;
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getVehicletandp());
            ps.setInt(2, s.getNumber_of_slot());
            ps.setInt(3, s.getLevel_number());
            ps.setInt(4, s.getSlot_id());
            return ps.executeUpdate() == 1;
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }

    /* ===================================================== *
     *  D E L E T E                                          *
     * ===================================================== */
    public boolean delete(int id) {
        String sql = "DELETE FROM slots WHERE slot_id = ?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }

    /* ===================================================== *
     *  helpers                                              *
     * ===================================================== */
    private SlotData map(ResultSet rs) {
        try {
            return new SlotData(
                    rs.getInt("slot_id"),
                    rs.getInt("vehicletandp_id"),
                    rs.getInt("number_of_slot"),
                    rs.getInt("level_number")
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
