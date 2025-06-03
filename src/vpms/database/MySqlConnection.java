/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.database;

import java.sql.*;

/**
 *
 * @author sangyakoirala
 */
public class MySqlConnection implements DbConnection{

    public static Connection getConnection() {
    try {
        String username = "root";
        String password = "coventry2019";
        String database = "vpms_db";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + database, username, password
        );

        if (connection == null) {
            System.out.println(" Database connection failed.");
        } else {
            System.out.println(" Database connection success.");
        }

        return connection;

    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e);
        return null;
    }
}
    
    @Override
    public Connection openConnection() {
        try{
            String username = "root";
            String password = "coventry2019";
            String database = "vpms_db";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database, username, password
            );
            if(connection == null){
                System.out.println("Database connection fail");
            }else{
                System.out.println("Database connection success");
            }
            return connection;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        try{
            if(conn !=null && !conn.isClosed()){
                conn.close();
                System.out.println("Connection closed");
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try{
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            return result;
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public int executeUpdate(Connection conn, String query) {
          try{
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            return result;
        }catch(SQLException e){
            System.out.println(e);
            return -1;
        }
    }    
}
