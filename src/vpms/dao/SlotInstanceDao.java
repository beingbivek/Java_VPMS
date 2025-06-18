package vpms.dao;

import vpms.database.MySqlConnection;
import vpms.model.SlotInstanceData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SlotInstanceDao {

    private final MySqlConnection db = new MySqlConnection();

    public SlotInstanceDao() throws SQLException { createTable(); }

    /* ---------- bulk create when a new slot collection is inserted ---------- */
    public void bulkInsert(int slotId,int total,String pre,int lvl) throws SQLException{
        String sql="INSERT INTO slot_instances(slot_id,slot_index,code,status) VALUES (?,?,?, 'free')";
        try(Connection c=db.openConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            for(int i=0;i<total;i++){
                ps.setInt(1,slotId);
                ps.setInt(2,i);
                ps.setString(3,pre+"-L"+lvl+"-S"+String.format("%02d",i));
                ps.addBatch();
            }
            ps.executeBatch();          // single network round-trip[4]
        }
    }


    /* ---------- status update ---------- */
    public boolean updateStatus(int instanceId, String status) throws SQLException {
        String sql = "UPDATE slot_instances SET status=? WHERE instance_id=?";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt   (2, instanceId);
            return ps.executeUpdate() == 1;
        }
    }

    /* ---------- query by level for grid ---------- */
    public List<SlotInstanceData> findByLevel(int level) throws SQLException {
        String sql = """
            SELECT si.*, s.level_number, v.vehicle_type
              FROM slot_instances si
              JOIN slots s  ON si.slot_id = s.slot_id
              JOIN vehicle_type_and_price v
                   ON s.vehicletandp = v.id
             WHERE s.level_number = ?
             ORDER BY si.slot_index
            """;
        List<SlotInstanceData> list = new ArrayList<>();
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, level);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }
    
    public Set<Integer> findLevels() throws SQLException {
        Set<Integer> set = new HashSet<>();
        String sql = """
            SELECT DISTINCT s.level_number
              FROM slots s
              JOIN slot_instances si ON si.slot_id = s.slot_id
             ORDER BY s.level_number
            """;
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) set.add(rs.getInt(1));
        }
        return set;
    }

    /* ---------- helpers ---------- */
    private SlotInstanceData map(ResultSet rs) throws SQLException {
        return new SlotInstanceData(
                rs.getInt   ("instance_id"),
                rs.getInt   ("slot_id"),
                rs.getInt   ("slot_index"),
                rs.getString("code"),
                rs.getString("status"),
                rs.getInt   ("level_number"),
                rs.getString("vehicle_type")
        );
    }

    private String buildCode(String prefix,int level,int idx){
        return prefix + "-L" + level + "-S" + String.format("%02d", idx);
    }

    private void createTable() throws SQLException {
        String ddl = """
           CREATE TABLE IF NOT EXISTS slot_instances(
             instance_id INT AUTO_INCREMENT PRIMARY KEY,
             slot_id     INT NOT NULL,
             slot_index  INT NOT NULL,
             code        VARCHAR(15) UNIQUE,
             status      VARCHAR(10) DEFAULT 'free',
             FOREIGN KEY (slot_id) REFERENCES slots(slot_id)
           )""";
        try (Connection c = db.openConnection();
             Statement st = c.createStatement()) { st.executeUpdate(ddl); }
    }
}
