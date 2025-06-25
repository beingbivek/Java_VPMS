package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.VehicleData;
import vpms.view.EditVehiclesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;

public class EditVehicleController {
    private final EditVehiclesView view;
    private final VehicleDao vehicleDao = new VehicleDao();
    private final VehicleTypeAndPriceDao vtDao = new VehicleTypeAndPriceDao();
    private final int vehicleId;
    private final VehicleManagementController vmController;
    private int currentVehicletandpId = -1;
    int id;

    public EditVehicleController(EditVehiclesView view, int vehicleId,int id,VehicleManagementController vmController) {
        this.view = view;
        this.vehicleId = vehicleId;
        this.id = id;
        this.vmController = vmController;
        fillVehicleTypeComboBox();
        loadVehicleData();
        view.getBtnUpdateVehicle().addActionListener(new UpdateListener());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.dispose();
    }

    private void fillVehicleTypeComboBox() {
        List<String> types = vtDao.getAllVehicleTypeNames();
        JComboBox<String> combo = view.getVehicleTypeComboBox();
        combo.removeAllItems();
        for (String type : types) {
            combo.addItem(type);
        }
    }

    private void loadVehicleData() {
        VehicleData vehicle = vehicleDao.getVehicleById(vehicleId);
        if (vehicle == null) {
            JOptionPane.showMessageDialog(view, "Vehicle not found.");
            view.dispose();
            return;
        }
        // Set fields
        view.getTxtVehicleNumber().setText(vehicle.getVehicleNumber());
        view.getTxtOwnerName().setText(vehicle.getOwnerName());
        view.getTxtOwnerContact().setText(vehicle.getOwnerContact());

        // Get vehicle type name from vehicletandp_id
        try {
            int vehicletandpId = Integer.parseInt(vehicle.getType());
            String vehicleTypeName = vtDao.getVehicleTypeById(vehicletandpId);
            currentVehicletandpId = vehicletandpId;
            view.getVehicleTypeComboBox().setSelectedItem(vehicleTypeName);
        } catch (Exception ex) {
            // fallback: select first
            view.getVehicleTypeComboBox().setSelectedIndex(0);
        }
    }

    class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String vehicleTypeName = (String) view.getVehicleTypeComboBox().getSelectedItem();
            int vehicletandpId = vtDao.getIdByVehicleType(vehicleTypeName);

            String vehicleNumber = view.getTxtVehicleNumber().getText().trim();
            String ownerName = view.getTxtOwnerName().getText().trim();
            String ownerContact = view.getTxtOwnerContact().getText().trim();

            // Validation
            if (vehicleNumber.isEmpty() || ownerName.isEmpty() || ownerContact.isEmpty()) {
                JOptionPane.showMessageDialog(view, "All fields are required.");
                return;
            }

            // Set updatedAt
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            VehicleData vehicle = new VehicleData(
                vehicleId,
                String.valueOf(vehicletandpId),
                vehicleNumber,
                ownerName,
                ownerContact,
                null, // createdAt not changed
                now
            );

            boolean success = vehicleDao.updateVehicle(vehicle);
            if (success) {
                ActivityLog log = new ActivityLog(id,"VehicleData Edited, Obj: "+vehicle);
                new ActivityLogDao().logActivity(log);
                vmController.loadVehicleTable();
                JOptionPane.showMessageDialog(view, "Vehicle updated successfully!");
                close();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update vehicle.");
            }
        }
    }
}
