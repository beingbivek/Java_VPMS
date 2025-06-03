/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpms.database.MySqlConnection;
import vpms.model.LoginRequest;
import vpms.model.UserData;

/**
 *
 * @author being
 */
public class UserDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean registerUser(UserData userData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS vpmsUsers ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "name VARCHAR(50) NOT NULL, "
            + "type VARCHAR(20) NOT NULL, "
            + "email VARCHAR(100) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "image BLOB"
            + ")";
         String query=  "INSERT INTO vpmsUsers (name, type, email, password,image) VALUES (?,?, ?, ?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getEmail());
            pstmt.setString(3, userData.getType());
            pstmt.setString(4, userData.getPassword());
            pstmt.setBytes(5, userData.getImage());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    }
    
    public UserData loginUser(LoginRequest loginData){
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM vpmsUsers where email = ? and password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginData.getEmail());
            pstmt.setString(2, loginData.getPassword());
            ResultSet result = pstmt.executeQuery();
            if(result.next()){
                UserData user  = new UserData(
                    result.getString("name"),
                    result.getString("type"),
                    result.getString("email"));
                user.setId(result.getInt("id"));
);}
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
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
                result.getString("name"),
                result.getString("type"),
                result.getString("email"));
            user.setId(result.getInt("id"));
            userList.add(user);
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    } finally {
        mySql.closeConnection(conn);
    }

    return userList;
}
    public boolean deleteUser(int userId){
        Connection conn = mySql.openConnection();
        String query = "DELETE FROM vpmsUsers WHERE id = ?";
        String sql = null;
         try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("Error deleting user: " + ex.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean updateUser(UserData updatedUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<UserData> searchUsers(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

