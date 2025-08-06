/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vpms;

//import vpms.controller.AdminDashboardController;
import vpms.controller.WelcomeAndLoginController;
//import vpms.controller.StripePaymentController;
//import vpms.model.StripePaymentModel;
//import vpms.controller.AdminLoginController;
//import vpms.controller.DefaultAdminSeeder;
//import vpms.view.AdminDashboardView;
import vpms.view.WelcomeAndLoginView;
//import vpms.view.StripePaymentView;
//import vpms.view.AdminLoginView;
/**
 *
 * @author being
 */
public class VehicleParkingManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            WelcomeAndLoginView view = new WelcomeAndLoginView();
            new WelcomeAndLoginController(view).open();
        }
    });
    }
    

    
    


    
    
    

}


    

