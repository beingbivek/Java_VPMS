///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package vpms.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.dao.ReservationDao;
import vpms.model.ReservationData;
import vpms.view.EditReservationView;

//
///**
// *
// * @author PRABHASH
// */

public class EditReservationController {
    private EditReservationView view;
    private ReservationDao dao;
    private ReservationController parentController;
    private ReservationData selectedData;

    public EditReservationController(EditReservationView view, ReservationData selectedData, ReservationController parentController) {
        this.view = view;
        this.dao = new ReservationDao();
        this.selectedData = selectedData;
        this.parentController = parentController;

        addSaveButtonListener();
        addCancelButtonListener();

        loadSelectedReservationData();
    }

    public void open() {
        this.view.setVisible(true);
    }

    private void loadSelectedReservationData() {
        view.getVehicleIdField().setText(String.valueOf(selectedData.getVehicleId()));
        view.getSlotIdField().setText(String.valueOf(selectedData.getSlotId()));
        view.getVehicleTypeField().setText(selectedData.getVehicleType());
        view.getContactField().setText(selectedData.getContact());
        view.getEntryTimeField().setText(selectedData.getEntryTime());
        view.getExitTimeField().setText(selectedData.getExitTime());
        view.getDurationLabel().setText(selectedData.getDuration());
        view.getStatusComboBox().setSelectedItem(selectedData.getStatus());
        view.getPaymentStatusComboBox().setSelectedItem(selectedData.getPaymentStatus());
    }

    private void addSaveButtonListener() {
        view.getSaveButton().addActionListener(new SaveButtonListener());
    }

    private void addCancelButtonListener() {
        view.getCancelButton().addActionListener(new CancelButtonListener());
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int vehicleId = Integer.parseInt(view.getVehicleIdField().getText().trim());
                int slotId    = Integer.parseInt(view.getSlotIdField().getText().trim());

                String vehicleType = view.getVehicleTypeField().getText().trim();
                String contact     = view.getContactField().getText().trim();
                String entryTime   = view.getEntryTimeField().getText().trim();
                String exitTime    = view.getExitTimeField().getText().trim();
                String duration    = view.getDurationLabel().getText().trim();
                String status      = view.getStatusComboBox().getSelectedItem().toString();
                String paymentStatus = view.getPaymentStatusComboBox().getSelectedItem().toString();

                if (vehicleType.isEmpty() || contact.isEmpty() || entryTime.isEmpty() || exitTime.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please fill in all required fields.");
                    return;
                }

                ReservationData updatedData = new ReservationData(
                        selectedData.getReservationId(),
                        vehicleId,
                        0, // Skipping userId
                        slotId,
                        vehicleType,
                        contact,
                        entryTime,
                        exitTime,
                        duration,
                        status,
                        paymentStatus
                );

                boolean success = dao.updateReservation(updatedData);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Reservation updated successfully.");
                    view.dispose();
                    parentController.loadReservationData();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update reservation.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid input: please enter valid numbers.");
            }
        }
    }

    class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
}