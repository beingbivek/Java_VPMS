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
}
    
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    
    
    class ParkingEntryHandler implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(dateFormatter);

        // Get current time
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = currentTime.format(timeFormatter);
            
            String vehicleNumber= view.getEntryVehicleNumber().getSelectedItem().toString();
            String slotNumber = view.getSlotNumber().getSelectedItem().toString();
            view.setEntryDateValue(dateString);
            view.setEntryTimeValue(timeString);
            String entryNOte = view. getEntryNote().getText();

             if (vehicleNumber.isEmpty() || slotNumber.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
                return;
            }
        }

       
        
    }
}