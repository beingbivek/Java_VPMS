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
import vpms.model.ResetPasswordRequest;
import vpms.model.UserData;
 
/**
*
* @author being
*/
public class UserDao {
    MySqlConnection mySql = new MySqlConnection();
    public void createTable(){
        Connection conn= mySql.openConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vpmsUsers ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "name VARCHAR(50) NOT NULL, "
            + "type VARCHAR(20) NOT NULL, "
            + "email VARCHAR(100) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "phone VARCHAR(10) NOT NULL, "  
            + "image BLOB"
            + ")";
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Create table"+ex);
            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            mySql.closeConnection(conn);
        }
    }
    public boolean registerUser(UserData userData){
        Connection conn= mySql.openConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS vpmsUsers ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "name VARCHAR(50) NOT NULL, "
            + "type VARCHAR(20) NOT NULL, "
            + "email VARCHAR(100) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "phone VARCHAR(10) NOT NULL, "  
            + "image BLOB"
            + ")";
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Create table"+ex);
            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         String query=  "INSERT INTO vpmsUsers (name, type, email, password,phone,image) VALUES (?,?, ?, ?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getType());
            pstmt.setString(3, userData.getEmail());
            pstmt.setString(4, userData.getPassword());
            pstmt.setString(5, userData.getPhone());
            pstmt.setBytes(6, userData.getImage());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("user insertion"+ex);
 
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
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("phone"),
                    result.getBytes("image")                 
                );
                user.setId(result.getInt("id"));
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
                result.getString("email"),
                result.getString("password"),
                result.getString("phone"),
                result.getBytes("image")
            );
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
    public List<UserData> searchUsers(String data) {
    List<UserData> userList = new ArrayList<>();
    Connection conn = mySql.openConnection();
    String sql = "SELECT * FROM vpmsUsers WHERE name LIKE ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1,data);
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
            UserData user = new UserData(
                result.getString("name"),
                result.getString("type"),
                result.getString("email"),
                result.getString("password"),
                result.getString("phone"),
                result.getBytes("image")
            );
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
    public boolean checkEmail(String email){
        Connection conn = mySql.openConnection();
        String query = "SELECT * FROM vpmsUsers WHERE email=?";
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,email);
            ResultSet result = stmnt.executeQuery();
            return result.next();
        }catch(Exception e){
            return false;
        }finally{
            mySql.closeConnection(conn);
        }
    }
    public boolean resetPassword(ResetPasswordRequest resetReq){
        Connection conn = mySql.openConnection();
        String query = "UPDATE vpmsUsers SET password = ? WHERE email = ?";
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1,resetReq.getPassword());
            stmnt.setString(2,resetReq.getEmail());
            int result = stmnt.executeUpdate();
            return result > 0;
        }catch(Exception e){
            return false;
        }finally{
            mySql.closeConnection(conn);
        }
    }
    public UserData getUserFromId(int id){
        Connection conn = mySql.openConnection();
    String sql = "SELECT * FROM vpmsUsers WHERE id = ?";
    try {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet result = pstmt.executeQuery();
        UserData user = new UserData(
            result.getInt("id"),
            result.getString("name"),
            result.getString("type"),
            result.getString("email"),
            result.getString("password"),
            result.getString("phone"),
            result.getBytes("image"));
        return user;
    } catch (SQLException ex) {
        System.out.println(ex);
    } finally{
        mySql.closeConnection(conn);
    }
    return null;
    }
    public boolean updateUser(UserData userData) {
    Connection conn = mySql.openConnection();
    String query = "UPDATE vpmsUsers SET name=?, type=?, password=?, email=?, phone=?, image=? WHERE id=?";
 
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, userData.getName());
        pstmt.setString(2, userData.getType());
        pstmt.setString(3, userData.getPassword());
        pstmt.setString(4, userData.getEmail());
        pstmt.setString(5, userData.getPhone());
        pstmt.setBytes(6, userData.getImage());
        pstmt.setInt(7, userData.getId());
 
        
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
        return ps.executeUpdate() > 0;      // returns true only if a row was removed
    }
}
 
}