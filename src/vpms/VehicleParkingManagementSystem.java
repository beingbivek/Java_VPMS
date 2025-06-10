/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vpms;

//import vpms.controller.AdminDashboardController;
import vpms.controller.StripePaymentController;
import vpms.model.StripePaymentModel;
//import vpms.controller.AdminLoginController;
//import vpms.controller.DefaultAdminSeeder;
//import vpms.view.AdminDashboardView;
import vpms.view.StripePaymentView;
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
            // For dashbaord
//            AdminDashboardView view = new AdminDashboardView();
//            new AdminDashboardController(view).open();
              StripePaymentView view = new StripePaymentView();
              StripePaymentModel model = new StripePaymentModel();
            new StripePaymentController(view,model).open();
        }
    });
//        DefaultAdminSeeder.insertDefaultAdminIfNotExists();
//        AdminLoginView view = new AdminLoginView();
//        AdminLoginController controller = new AdminLoginController(view);
//        controller.open();
    }
    

    
    


    
    
    

}


    

