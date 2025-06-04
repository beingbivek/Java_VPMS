/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import vpms.dao.UserDao;
import vpms.model.UserData;


/**
 *
 * @author PRABHASH
 */
public class DefaultAdminSeeder {
     public static void insertDefaultAdminIfNotExists() {
        String defaultEmail = "admin@vpms.com";
        String defaultPassword = "adminvpms123";
        String userType = "admin";
        String defaultName = "Admin";
        String defaultPhone = "9860060060";
        
        
        UserDao userDao = new UserDao();
        userDao.createTable();
        UserData user = new UserData(defaultName,userType,defaultEmail,defaultPassword,defaultPhone,null);
        if(userDao.getUserFromId(1) == null){
            userDao.registerUser(user);
        }
    }
    
}
