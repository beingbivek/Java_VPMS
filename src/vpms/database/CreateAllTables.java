/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.database;

/**
 *
 * @author being
 */
public class CreateAllTables {
    MySqlConnection mySql = new MySqlConnection();
    private CreateAllTables(){
        Connection conn = mySql.openConnection();
    }
    
}
