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
        
            // Get current date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(dateFormatter);

        // Get current time
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = currentTime.format(timeFormatter);
     
        view.setExitTimeValue(timeString);
}
    
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    
    
    class ParkingExitHandler implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
     
            
            String parkingStatus= view.getParkingStatus().getSelectedItem().toString();
            String parkingType = view.getParkingType().getSelectedItem().toString();
          
            String exitNote = view. getExitNote().getText();

             if (parkingStatus.isEmpty() || parkingType.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
                return;
            }
        }

       
        
    }
}