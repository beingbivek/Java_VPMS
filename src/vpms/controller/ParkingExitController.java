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
//        
//}
//    
//    public void open() {
//        view.setVisible(true);
//    
//    view.loadParkedVehiclesInComboBox(parkingDao.getParkedVehicleNumbers());
//    }
//
//    public void close() {
//        view.dispose();
//    }
//    
//    
//    private class SearchVehicleHandler implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//     
//            
//            String vehicleNumber= view.getExitVehicleNumber().getSelectedItem().toString();
//            
//             if (vehicleNumber.isEmpty() || vehicleNumber.equals("Select Vehicle")){
//              
//                JOptionPane.showMessageDialog(view, "Please select a vehicle number.");
//                return;
//            }
//             try{
//                 ParkingDetails parkingDetail = ParkingDao.getParkingDetailsByVehicleNumber(vehicleNumber);
//              
//                 if (parkingDetail != null){
//                     view.setSlotNumber(parkingDetail.getSlotNumber());
//                     view.setEntryDate(parkingDetail.getEntryDate());
//                     view.getParkingTime(parkingDetail.getEntryTime());
//                     view.getParkingNote(parkingDetail.getEntryNote());
//                     
//                     calculateParkingFee(parkingDetail);
//                     view.enableExitButton(true);
//                 }else{
//                     JOptionPane.showMessageDialog(view,"Vehicle not found in parking records"),"Not Found",
//                     JOptionPane.(WARNING_MESSAGE);
//                     view.clearExitFields();
//                     view.enableExitButton(false);
//                    
//                     
//                 }
//             }catch (vehicleNotFoundException ex){
//                 
//             }
        }

       
        
    }
