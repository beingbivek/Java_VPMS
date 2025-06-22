package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.VehicleData;
import vpms.view.AddVehiclesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddVehiclesController {
    private final AddVehiclesView view;
    private final VehicleDao vehicleDao = new VehicleDao();
    private final VehicleTypeAndPriceDao vtDao = new VehicleTypeAndPriceDao();
    int id;

    public AddVehiclesController(AddVehiclesView view,int id) {
        this.view = view;
        fillVehicleTypeComboBox();
        this.id = id;
        view.getBtnSaveVehicle().addActionListener(new SaveListener());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.dispose();
    }

    private void fillVehicleTypeComboBox() {
        List<String> types = vtDao.getAllVehicleTypeNames();
        JComboBox<String> combo = view.getVehicleTypeCombobox();
        combo.removeAllItems();
        for (String type : types) {
            combo.addItem(type);
        }
    }

    class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String vehicleTypeName = (String) view.getVehicleTypeCombobox().getSelectedItem();
            int vehicletandpId = vtDao.getIdByVehicleType(vehicleTypeName);

            String vehicleNumber = view.getTxtVehicleNumber().getText().trim();
            String ownerName = view.getTxtOwnerName().getText().trim();
            String ownerContact = view.getTxtOwnerContact().getText().trim();

            // You may want to validate input here

            // Set createdAt and updatedAt
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            VehicleData vehicle = new VehicleData(
                vehicletandpId + "", // VehicleData expects String for type
                vehicleNumber,
                ownerName,
                ownerContact,
                now,
                now
            );

            boolean success = vehicleDao.registerVehicle(vehicle);
            if (success) {
                JOptionPane.showMessageDialog(view, "Vehicle added successfully!");
                close();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add vehicle.");
            }
        }
    }
}
