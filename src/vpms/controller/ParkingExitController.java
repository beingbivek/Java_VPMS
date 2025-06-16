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
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import vpms.dao.ParkingDao;

import vpms.model.ParkingDetails;
import vpms.view.ParkingExitView;


/**
 *
 * @author Chandani
 */
public class ParkingExitController {
   private final ParkingExitView view;
    private final ParkingDao parkingDao;

    public ParkingExitController(ParkingExitView view) { //constructor
        this.view = view;
        this.parkingDao = new ParkingDao();
        
            // Set current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(dateFormatter);

        // Set current time
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = currentTime.format(timeFormatter);
     
//        view.setExitDateValue(dateString);
//        view.setExitTimeValue(timeString);
//        
//        view.addSearchVehicleListener(new SearchVehicleHandler());
//        view.addVehicleExitListener(new VehicleExitHandler());
        
}
    
    public void open() {
        view.setVisible(true);
    
//    view.loadParkedVehiclesInComboBox(parkingDao.getParkedVehicleNumbers());
    }

    public void close() {
        view.dispose();
    }
    
    
//    private class SearchVehicleHandler implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//     
            
//            String vehicleNumber= view.getExitVehicleNumber().getSelectedItem().toString();
//            
//             if (vehicleNumber.isEmpty() || vehicleNumber.equals("Select Vehicle")){
//              
//                JOptionPane.showMessageDialog(view, "Please select a vehicle number.");
//                return;
//            }
//             try{
//                 ParkingDetail parkingDetail = ParkingDao.getParkingDetailByVehicleNumber(vehicleNumber);
//              
//                 if (parkingDetail != null){
//                     view.setSlotNumber(parkingDetail.getSlotNumber());
//                     view.setEntryDate(parkingDetail.getEntryDate());
//                     view.getParkingTime(parkingDetail.getEntryTime());
//                     view.getParkingNote(parkingDetail.getEntryNote());
//                     
//                     calculateParkingFee(parkingDetail);
//                     view.enableExitButton(true);
//                     } else {
//                    JOptionPane.showMessageDialog(view, 
//                            "Vehicle not found in parking records.", 
//                            "Not Found", JOptionPane.WARNING_MESSAGE);
//                    view.clearExitFields();
//                    view.enableExitButton(false);
//                }
//                
//            } catch (VehicleNotFoundException ex) {
//                JOptionPane.showMessageDialog(view, 
//                        "Vehicle " + vehicleNumber + " is not currently parked.", 
//                        "Vehicle Not Found", JOptionPane.WARNING_MESSAGE);
//                view.clearExitFields();
//                view.enableExitButton(false);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(view, 
//                        "Error searching vehicle: " + ex.getMessage(), 
//                        "Error", JOptionPane.ERROR_MESSAGE);
//                ex.printStackTrace();
//            }
//        }
//    }
//    
//    // Inner class to handle vehicle exit button clicks
//    private class VehicleExitHandler implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            
//            String vehicleNumber = view.getExitVehicleNumber().getSelectedItem().toString();
//            String slotNumber = view.getSlotNumber();
//            String exitNote = view.getExitNote().getText();
//            
//            if (vehicleNumber.isEmpty() || vehicleNumber.equals("Select Vehicle")) {
//                JOptionPane.showMessageDialog(view, "Please search for a vehicle first.");
//                return;
//            }
//            
//            try {
//                String exitDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                String exitTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//                
//                // Process vehicle exit
//                boolean success = parkingDao.processVehicleExit(vehicleNumber, exitDate, exitTime, exitNote);
//                
//                if (success) {
//                    // Show exit confirmation with parking summary
//                    String duration = view.getParkingDuration();
//                    String totalFee = view.getTotalFee();
//                    
//                    String message = String.format(
//                        "Vehicle %s has exited successfully!\n\n" +
//                        "Slot Number: %s\n" +
//                        "Parking Duration: %s\n" +
//                        "Total Fee: Rs. %s\n\n" +
//                        "Thank you for using our parking service!",
//                        vehicleNumber, slotNumber, duration, totalFee
//                    );
//                    
//                    JOptionPane.showMessageDialog(view, message, 
//                            "Exit Successful", JOptionPane.INFORMATION_MESSAGE);
//                    
//                    // Clear fields and refresh
//                    view.clearExitFields();
//                    view.enableExitButton(false);
//                    view.updateParkingSlotsDisplay();
//                    view.loadParkedVehiclesInComboBox(parkingDao.getParkedVehicleNumbers());
//                    
//                } else {
//                    JOptionPane.showMessageDialog(view, 
//                            "Failed to process vehicle exit. Please try again.", 
//                            "Exit Failed", JOptionPane.ERROR_MESSAGE);
//                }
//                
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(view, 
//                        "System error during vehicle exit: " + ex.getMessage(), 
//                        "Error", JOptionPane.ERROR_MESSAGE);
//                ex.printStackTrace();
//            }
//        }
//    }
//    
//    // Helper method to calculate parking fee based on duration
//    private void calculateParkingFee(ParkingDetails parkingDetail) {
//        try {
//            // Parse entry date and time
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//            
//            LocalDate entryDate = LocalDate.parse(parkingDetail.getEntryDate(), dateFormatter);
//            LocalTime entryTime = LocalTime.parse(parkingDetail.getEntryTime(), timeFormatter);
//            
//            // Current exit date and time
//            LocalDate exitDate = LocalDate.now();
//            LocalTime exitTime = LocalTime.now();
//            
//            // Calculate duration
//            long daysDifference = ChronoUnit.DAYS.between(entryDate, exitDate);
//            long hoursDifference = ChronoUnit.HOURS.between(entryTime, exitTime);
//            long minutesDifference = ChronoUnit.MINUTES.between(entryTime, exitTime) % 60;
//            
//            // Adjust hours if spanning multiple days
//            if (daysDifference > 0) {
//                hoursDifference = daysDifference * 24 + ChronoUnit.HOURS.between(entryTime, exitTime);
//            }
//            
//            // Format duration string
//            String durationString;
//            if (daysDifference > 0) {
//                durationString = String.format("%d day(s), %d hour(s), %d minute(s)", 
//                        daysDifference, hoursDifference % 24, minutesDifference);
//            } else {
//                durationString = String.format("%d hour(s), %d minute(s)", 
//                        hoursDifference, minutesDifference);
//            }
//            
//            // Calculate fee (example: Rs. 20 per hour, minimum Rs. 20)
//            long totalMinutes = ChronoUnit.MINUTES.between(
//                entryDate.atTime(entryTime), exitDate.atTime(exitTime));
//            
//            double totalHours = Math.ceil(totalMinutes / 60.0);
//            double totalFee = Math.max(20.0, totalHours * 20.0); // Minimum Rs. 20
//            
//            // Set calculated values in view
//            view.setParkingDuration(durationString);
//            view.setTotalFee(String.format("%.2f", totalFee));
//            
//        } catch (Exception ex) {
//            view.setParkingDuration("Error calculating duration");
//            view.setTotalFee("0.00");
//            ex.printStackTrace();
//        }
//    }
//}
       
    }
