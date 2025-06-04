/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vpms;

import vpms.controller.AdminLoginController;
import vpms.controller.DefaultAdminSeeder;
import vpms.view.AdminLoginView;

/**
 *
 * @author being
 */
public class VehicleParkingManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DefaultAdminSeeder.insertDefaultAdminIfNotExists();
        AdminLoginView view = new AdminLoginView();
        AdminLoginController controller = new AdminLoginController(view);
        controller.open();
    }
    

    
    


    
    
    

}


    

