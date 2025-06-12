/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import vpms.dao.VehicleDao;
import vpms.model.VehicleData;
import vpms.view.AddVehiclesView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Arrays;


/**
 *
 * @author PRABHASH
 */
public class AddVehiclesController {
    private AddVehiclesView view;  
    public AddVehiclesController (AddVehiclesView view) {
        this.view = view;
    }  
    public void open() {
        this.view.setVisible(true);
    }   
    public void close() {
        this.view.dispose();
    }
    
    
    
    public boolean addVehicle(String type, String vehicleNumber, String ownerName, String ownerContact, String createdAt, String updatedAt){
        VehicleData data = new VehicleData(type,vehicleNumber, ownerName, ownerContact, createdAt, updatedAt);
    return new VehicleDao().registerVehicle(data);
}
    public void handleSaveVehicle() {
    try {
        
        String type = view.getTxtType().getText().trim();
        String vehicleNumber = view.getTxtVehicleNumber().getText().trim();
        String ownerName = view.getTxtOwnerName().getText().trim();
        String ownerContact = view.getTxtOwnerContact().getText().trim();

        
        List<String> allowedTypes = Arrays.asList("Car","Bike", "Van", "EV");
        if (!allowedTypes.contains(type)) {
            JOptionPane.showMessageDialog(view, "Vehicle Type Must be one of: Car, Bike, Van or EV");
            return;
        }
        
        if (!vehicleNumber.matches("^[A-Za-z0-9 -]{6,20}$")) {
            JOptionPane.showMessageDialog(view, "Vehicle number must be 6–20 characters.");
            return;
        }

        
        if (!ownerContact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(view, "Contact must be a 10-digit number.");
            return;
        }
  
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdAt = LocalDateTime.now().format(dtf);
        String updatedAt = createdAt;

        
        VehicleData data = new VehicleData(type, vehicleNumber, ownerName, ownerContact, createdAt, updatedAt);
        boolean inserted = new VehicleDao().registerVehicle(data);

        if (inserted) {
            JOptionPane.showMessageDialog(view, "Vehicle added successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add vehicle.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
    }
}

    
}

  