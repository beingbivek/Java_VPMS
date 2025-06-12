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
import vpms.view.EditVehicleTypeAndPriceView;
import vpms.view.VehicleTypeAndPriceView;
/**
 *
 * @author PRABHASH
 */
public class EditVehicleTypeAndPriceController {
    private EditVehicleTypeAndPriceView view;
    private VehicleTypeAndPriceDao dao;
    private VehicleTypeAndPriceController mainController;
    private VehicleTypeAndPriceData selectedData;

    public EditVehicleTypeAndPriceController(EditVehicleTypeAndPriceView view, VehicleTypeAndPriceData selectedData, VehicleTypeAndPriceController mainController) {
        this.view = view;
        this.dao = new VehicleTypeAndPriceDao();
        this.selectedData = selectedData;
        this.mainController = mainController;

        populateFields();
        this.view.addUpdateListener(new UpdateAction());
    }

    public void open() {
        this.view.setVisible(true);
    }

    private void populateFields() {
        view.setVehicleType(selectedData.getVehicleType());
        view.setReservationPrice(selectedData.getReservationPrice());
        view.setRegularPrice(selectedData.getRegularPrice());
        view.setDemandPrice(selectedData.getDemandPrice());
        view.setExtraCharge(selectedData.getExtraCharge());
        view.setStatus(selectedData.getStatus());
    }

    class UpdateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String vehicleType = view.getVehicleType();
                String reservationPrice = view.getReservationPrice().trim();
                String regularPrice = view.getRegularPrice().trim();
                String demandPrice = view.getDemandPrice().trim();
                String extraCharge = view.getExtraCharge().trim();
                String status = view.getStatus();

                // Optional fallback: store empty as "0"
                if (reservationPrice.isEmpty()) reservationPrice = "0";
                if (regularPrice.isEmpty()) regularPrice = "0";
                if (demandPrice.isEmpty()) demandPrice = "0";
                if (extraCharge.isEmpty()) extraCharge = "0";

                VehicleTypeAndPriceData updated = new VehicleTypeAndPriceData(
                        selectedData.getId(), vehicleType, reservationPrice, regularPrice, demandPrice, extraCharge, status
                );

                boolean success = dao.updateVehicleTypeAndPrice(updated);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Vehicle type updated successfully.");
                    view.dispose();
                    
                    VehicleTypeAndPriceView mainView = new VehicleTypeAndPriceView();
                    VehicleTypeAndPriceController controller = new VehicleTypeAndPriceController(mainView);
                    controller.open();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update vehicle type.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "An error occurred while updating.");
            }
        }
    }

}
    

