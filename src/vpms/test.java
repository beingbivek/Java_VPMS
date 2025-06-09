/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms;

import javax.swing.UIManager;
import vpms.controller.VehicleTypeAndPriceController;
import vpms.view.VehicleTypeAndPriceView;

/**
 *
 * @author PRABHASH
 */
public class test {
    public static void main(String[] args) {
        // Optional: Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Launch the view with controller
        VehicleTypeAndPriceView view = new VehicleTypeAndPriceView();
        VehicleTypeAndPriceController controller = new VehicleTypeAndPriceController(view);
        view.setVisible(true);
    }
    
}
