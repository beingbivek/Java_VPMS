/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import vpms.dao.ParkingDao;
import vpms.dao.VehicleDao;

import vpms.model.ParkingDetails;
import vpms.view.ParkingEntryView;

/**
 *
 * @author Chandani
 */
public class ParkingEntryController {
   private final ParkingEntryView view;
    private final ParkingDao parkingDao;

    public ParkingEntryController(ParkingEntryView view) { //constructor
        this.view = view;
        this.parkingDao = new ParkingDao();
        
            // Get current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(dateFormatter);

        // Get current time
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = currentTime.format(timeFormatter);
        
        view.setEntryDateValue(dateString);
        view.setEntryTimeValue(timeString);
        
     
    
    public void open() {
        view.setVisible(true);
        this.view.loadVehicleNumeberInComboBox(new VehicleDao().showVehicleNumbers());
    }

    public void close() {
        view.dispose();
    }
    
    
    class ParkingEntryHandler implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String vehicleNumber= view.getEntryVehicleNumber().getSelectedItem().toString();
            String slotNumber = view.getSlotNumber().getSelectedItem().toString();
            String entryNote = view. getEntryNote().getText();

             if (vehicleNumber.isEmpty() || slotNumber.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
                return;
             }
                String entryDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy-mm-dd"));
                String entryTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            
                 ParkingDetails parkingDetail = new ParkingDetails(vehicleNumber,slotNumber,entryTime,entryNote,false);
                 try{
                 boolean success = parkingDao.registerParkingUser(parkingDetail);
                 
                 
//              if(success){
//                  view.clearEntryFields();
//                  view.updateParkingSlotsDisplay();
//                  JOptionPane.showMessageDialog(view,"Vehicle parked successfully in slot" + slotNumber);
//                  
//              } else{
//                  JOptionPane.showMessageDialog(view,"Failed to park vehicle.","Error",JOptionPane.ERROR_MESSAGE);
//              }
//             }catch (ParkingSlotOccupiedException ex){
//                     JOptionPane.showMessageDialog(view,
//                             "Slot " + view.getSlotNumber().getSelectedItem() + " is already occupied.",
//                             "Error", JOptionPane.ERROR_MESSAGE);
//             }
                     
                     } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                             "System error: " + ex.getMessage(),
                             "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

       
        
    }
}

