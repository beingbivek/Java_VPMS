package vpms.dao;

import vpms.database.MySqlConnection;
import vpms.model.SlotInstanceData;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlotInstanceDao {

    private final MySqlConnection mySql = new MySqlConnection();

    public void bulkInsert(int slotId,int total,String pre,int lvl){
        String sql="INSERT INTO slot_instances(slot_id,slot_index,code,status) VALUES (?,?,?, 'free')";
        Connection conn = mySql.openConnection();
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            for(int i=0;i<total;i++){
                ps.setInt(1,slotId);
                ps.setInt(2,i);
                ps.setString(3,pre+"-L"+lvl+"-S"+String.format("%02d",i));
                ps.addBatch();
            }
            ps.executeBatch();          
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }


    public boolean updateStatus(int instanceId, String status) {
        String sql = "UPDATE slot_instances SET status=? WHERE instance_id=?";
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt   (2, instanceId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }

    public List<SlotInstanceData> findByLevel(int level) {
    String sql = """
        SELECT si.*, s.level_number, v.vehicle_type
          FROM slot_instances si
          JOIN slots s  ON si.slot_id = s.slot_id
          JOIN vehicle_type_and_price v
               ON s.vehicletandp_id = v.id
         WHERE s.level_number = ?
         ORDER BY si.slot_index
        """;
    List<SlotInstanceData> list = new ArrayList<>();
    Connection conn = mySql.openConnection();
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, level);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        mySql.closeConnection(conn);
    }
    return list;
}

    
    public Set<Integer> findLevels() {
        Set<Integer> set = new HashSet<>();
        String sql = """
            SELECT DISTINCT s.level_number
              FROM slots s
              JOIN slot_instances si ON si.slot_id = s.slot_id
             ORDER BY s.level_number
            """;
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) set.add(rs.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return set;
    }
    
    public SlotInstanceData findByInstanceId(int instanceId) {
        String sql = """
            SELECT si.*, s.level_number, v.vehicle_type
              FROM slot_instances si
              JOIN slots s ON si.slot_id = s.slot_id
              JOIN vehicle_type_and_price v ON s.vehicletandp_id = v.id
             WHERE si.instance_id = ?
             LIMIT 1
        """;
        Connection conn = mySql.openConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, instanceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    private SlotInstanceData map(ResultSet rs){
        try {
            return new SlotInstanceData(
                    rs.getInt   ("instance_id"),
                    rs.getInt   ("slot_id"),
                    rs.getInt   ("slot_index"),
                    rs.getString("code"),
                    rs.getString("status"),
                    rs.getInt   ("level_number"),
                    rs.getString("vehicle_type")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildCode(String prefix,int level,int idx){
        return prefix + "-L" + level + "-S" + String.format("%02d", idx);
    }
    
    public String getSlotCodeFromInstanceId(int instance_id) {
        String sql = "SELECT code FROM slot_instances WHERE instance_id = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, instance_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return "";
    }

    public int getTotalSlotCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM slot_instances";
        try (Connection conn = mySql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Total slot count error: " + ex);
        }
        return count;
    }

    public int getAvailableSlotCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM slot_instances WHERE status = 'free'";
        try (Connection conn = mySql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Available slot count error: " + ex);
        }
        return count;
    }

}
