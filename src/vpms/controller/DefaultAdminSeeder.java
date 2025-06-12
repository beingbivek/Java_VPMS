/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.io.File;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.needed.ImageConverter;


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
        File imgFile = new File("src/Icons/adminperfectsize.png");
        ImageConverter img = new ImageConverter(imgFile);
        UserData user = new UserData(defaultName,userType,defaultEmail,defaultPassword,defaultPhone,img.returnByteArray());
        if(userDao.checkEmail(defaultEmail)){
            userDao.registerUser(user);
        }
    }
}
