/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package vpms.dao;
 
//at.favre.lib.bytes.*;
//at.favre.lib.crypto.bcrypt.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpms.database.MySqlConnection;
import vpms.model.LoginRequest;
import vpms.model.ResetPasswordRequest;
import vpms.model.UserData;
 
/**
*
* @author being
*/
public class UserDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean registerUser(UserData userData) {
        Connection conn = mySql.openConnection();
        String query = "INSERT INTO vpmsUsers (name, type, email, password, phone, image, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getType());
            pstmt.setString(3, userData.getEmail());
            pstmt.setString(4, bcryptHashString);
            pstmt.setString(5, userData.getPhone());
            pstmt.setBytes(6, userData.getImage());
            pstmt.setString(7, userData.getStatus());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("user insertion" + ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return false;
    }

    public UserData loginUser(LoginRequest req) {
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vpmsUsers WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, req.getEmail());
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String storedHash = result.getString("password");
                boolean ok = BCrypt.verifyer().verify(req.getPassword().toCharArray(), storedHash.toCharArray()).verified;
                if (ok) {
                    UserData u = new UserData(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("type"),
                        result.getString("email"),
                        storedHash,
                        result.getString("phone"),
                        result.getBytes("image"),
                        result.getString("status")
                    );
                    return u;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    public List<UserData> showUsers() {
        List<UserData> userList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vpmsUsers";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                UserData user = new UserData(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("type"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("phone"),
                    result.getBytes("image"),
                    result.getString("status")
                );
                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return userList;
    }

    public List<UserData> searchUsers(String data) {
        List<UserData> userList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vpmsUsers WHERE name LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                UserData user = new UserData(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("type"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("phone"),
                    result.getBytes("image"),
                    result.getString("status")
                );
                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return userList;
    }

    public boolean checkEmail(String email) {
        Connection conn = mySql.openConnection();
        String query = "SELECT * FROM vpmsUsers WHERE email=?";
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            return result.next();
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean resetPassword(ResetPasswordRequest resetReq) {
        Connection conn = mySql.openConnection();
        String query = "UPDATE vpmsUsers SET password = ? WHERE email = ?";
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, resetReq.getPassword().toCharArray());
            stmnt.setString(1, bcryptHashString);
            stmnt.setString(2, resetReq.getEmail());
            int result = stmnt.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public UserData getUserFromId(int id) {
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vpmsUsers WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                UserData user = new UserData(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("type"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("phone"),
                    result.getBytes("image"),
                    result.getString("status")
                );
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    public boolean updateUser(UserData userData) {
        Connection conn = mySql.openConnection();
        String query = "UPDATE vpmsUsers SET name=?, type=?, password=?, email=?, phone=?, image=?, status=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getType());
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());
            pstmt.setString(3, bcryptHashString);
            pstmt.setString(4, userData.getEmail());
            pstmt.setString(5, userData.getPhone());
            pstmt.setBytes(6, userData.getImage());
            pstmt.setString(7, userData.getStatus());
            pstmt.setInt(8, userData.getId());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM vpmsusers WHERE id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public int getTotalUserCount() {
        int count = 0;
        Connection conn = mySql.openConnection();
        String sql = "SELECT COUNT(*) FROM vpmsUsers";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }

    public int getTotalStaffCount() {
        int count = 0;
        Connection conn = mySql.openConnection();
        String sql = "SELECT COUNT(*) FROM vpmsUsers WHERE type = 'Staff'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }

    public int getActiveStaffCount() {
        int count = 0;
        Connection conn = mySql.openConnection();
        String sql = "SELECT COUNT(*) FROM vpmsUsers WHERE type = 'Staff' AND status = 'Active'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return count;
    }
}
