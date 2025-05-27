/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vpms;

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
        AdminLoginController.DefaultAdminSeeder.insertDefaultAdminIfNotExists();
        AdminLoginView view = new AdminLoginView();
        AdminLoginController controller = new AdminLoginController(view);
        controller.open();

        // TODO code application logic here
        AdminLoginView view = new AdminLoginView();
        view.setVisible(true);
    }
    

    
    


    
    
    

}


    

