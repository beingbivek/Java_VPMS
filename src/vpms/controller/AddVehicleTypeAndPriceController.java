/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.VehicleTypeAndPriceData;
import vpms.view.AddVehicleTypeAndPriceView;
import vpms.view.VehicleTypeAndPriceView;
/**
 *
 * @author PRABHASH
 */
public class AddVehicleTypeAndPriceController {
     private AddVehicleTypeAndPriceView view;
    private VehicleTypeAndPriceDao dao;
    private VehicleTypeAndPriceController mainController;

    public AddVehicleTypeAndPriceController(AddVehicleTypeAndPriceView view, VehicleTypeAndPriceController mainController) {
        this.view = view;
        this.dao = new VehicleTypeAndPriceDao();
        this.mainController = mainController;

        this.view.addSubmitListener(new SubmitAction());
    }

    public void open() {
        this.view.setVisible(true);
    }

    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String vehicleType = view.getVehicleType();
                String reservationPrice = view.getReservationPrice().trim();
                String regularPrice = view.getRegularPrice().trim();
                String demandPrice = view.getDemandPrice().trim();
                String extraCharge = view.getExtraCharge().trim();
                String status = view.getStatus();

                
                if (vehicleType.isEmpty() || regularPrice.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vehicle type, regular price, and status are required.");
                    return;
                }

                
                if (reservationPrice.isEmpty()) reservationPrice = "0";
                if (demandPrice.isEmpty()) demandPrice = "0";
                if (extraCharge.isEmpty()) extraCharge = "0";

                VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                        vehicleType, reservationPrice, regularPrice, demandPrice, extraCharge, status
                );

                boolean success = dao.addVehicleTypeAndPrice(vehicle);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Vehicle type added successfully.");
                    view.dispose();
                    
                VehicleTypeAndPriceView mainView = new VehicleTypeAndPriceView();
                VehicleTypeAndPriceController controller = new VehicleTypeAndPriceController(mainView);
                controller.open();  
    
    
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add vehicle type.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "An error occurred while adding.");
            }
        }
    }
}
