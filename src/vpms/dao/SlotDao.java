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

    private final MySqlConnection db = new MySqlConnection();

    public SlotDao() throws SQLException {
        createTableIfMissing();
    }

    /* ===================================================== *
     *  C R E A T E                                          *
     * ===================================================== */
    public int insertReturnId(SlotData s) throws SQLException {
    String sql = "INSERT INTO slots(vehicletandp_id, number_of_slot, level_number) VALUES (?,?,?)";
    try (Connection c = db.openConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getVehicletandp());
            ps.setInt(2, s.getNumber_of_slot());
            ps.setInt(3, s.getLevel_number());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    /* ===================================================== *
     *  R E A D                                              *
     * ===================================================== */
    public SlotData findById(int id) throws SQLException {
        String sql = "SELECT * FROM slots WHERE slot_id = ?";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;   // not found
    }

    public List<SlotData> findAll() throws SQLException {
        List<SlotData> list = new ArrayList<>();
        String sql = "SELECT * FROM slots";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /* ===================================================== *
     *  U P D A T E                                          *
     * ===================================================== */
    public boolean update(SlotData s) throws SQLException {
        String sql = """
            UPDATE slots SET
                vehicletandp_id = ?,
                number_of_slot  = ?,
                level_number    = ?
            WHERE slot_id = ?
            """;
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, s.getVehicletandp());
            ps.setInt(2, s.getNumber_of_slot());
            ps.setInt(3, s.getLevel_number());
            ps.setInt(4, s.getSlot_id());
            return ps.executeUpdate() == 1;
        }
    }

    /* ===================================================== *
     *  D E L E T E                                          *
     * ===================================================== */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM slots WHERE slot_id = ?";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    /* ===================================================== *
     *  helpers                                              *
     * ===================================================== */
    private SlotData map(ResultSet rs) throws SQLException {
        return new SlotData(
                rs.getInt("slot_id"),
                rs.getInt("vehicletandp_id"),
                rs.getInt("number_of_slot"),
                rs.getInt("level_number")
        );
    }

    private void createTableIfMissing() throws SQLException {
        String ddl = """
            CREATE TABLE IF NOT EXISTS slots(
              slot_id         INT AUTO_INCREMENT PRIMARY KEY,
              vehicletandp_id INT NOT NULL,
              number_of_slot  INT NOT NULL,
              level_number    INT NOT NULL,
              FOREIGN KEY (vehicletandp_id)
                     REFERENCES vehicle_type_and_price(id)
            )""";
        try (Connection c = db.openConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate(ddl);
        }
    }
}
