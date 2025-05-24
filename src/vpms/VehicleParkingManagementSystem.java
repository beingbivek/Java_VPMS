/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vpms;

<<<<<<< Updated upstream
/**
=======
import vpms.view.AdminLoginView;
import vpms.controller.AdminLoginController;
import vpms.controller.DefaultAdminSeeder;



/**D
>>>>>>> Stashed changes
 *
 * @author being
 */
public class VehicleParkingManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
<<<<<<< Updated upstream
        // TODO code application logic here
        
    }
    
=======
    // 👇 THIS LINE is missing right now — add it
    DefaultAdminSeeder.insertDefaultAdminIfNotExists();


    AdminLoginView view = new AdminLoginView();
    AdminLoginController controller = new AdminLoginController(view);
    controller.open();
>>>>>>> Stashed changes
}

}
    

