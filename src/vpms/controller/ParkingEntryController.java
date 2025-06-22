/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.dao.ParkingDao;
import vpms.dao.SlotInstanceDao;

import vpms.model.ParkingDetails;
import vpms.model.SlotInstanceData;
import vpms.model.VehicleData;
import vpms.utils.DateAndTimeMethods;
import vpms.utils.SlotButton;
import vpms.view.ParkingEntryView;

/**
 *
 * @author Chandani
 */
public class ParkingEntryController {
    private final ParkingEntryView view;
    private final ParkingDao parkingDao;
    private final String entrydateTimeString;
    VehicleData vehicle;
    SlotInstanceData bay;

    public ParkingEntryController(ParkingEntryView view,VehicleData selected, SlotInstanceData bay) { //constructor
        this.view = view;
        this.parkingDao = new ParkingDao();
        this.vehicle = selected;
        this.bay = bay;
        this.entrydateTimeString = DateAndTimeMethods.getDateAndTime();
        this.view.setEntryDateValue(DateAndTimeMethods.splitDateAndTime(entrydateTimeString)[0]);
        this.view.setEntryTimeValue(DateAndTimeMethods.splitDateAndTime(entrydateTimeString)[1]);
        this.view.setVehicleNumber(selected.getVehicleNumber());
        this.view.setSlotNumber(bay.getCode());
        this.view.entryButtonListener(new ParkingEntryHandler());
    }   
        
    public void open(){
        view.setVisible(true);
}

    public void close() {
        view.dispose();
    }
    
    public void changeStatus(SlotInstanceData bay, SlotButton btn, String newStatus, String message) {
        try {
            boolean ok = new SlotInstanceDao().updateStatus(bay.getInstanceId(), newStatus);
            if (ok) {
                btn.setStatus(newStatus);
                JOptionPane.showMessageDialog(view, message);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Status change failed (DB returned 0 rows).",
                        "Update error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Database error:\n" + ex.getMessage(),
                    "SQL Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    class ParkingEntryHandler implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String entryNote = view. getEntryNote().getText();
            String parkingType = view.getParkingType().getSelectedItem().toString();
            
            ParkingDetails parkingDetail = new ParkingDetails();
            
            parkingDetail.ParkingEntryDetails(vehicle.getId(), bay.getInstanceId(), entrydateTimeString, entryNote, "Occupied", parkingType);
            
            try{
                boolean success = parkingDao.registerParkingUser(parkingDetail);
                if(success){
                    SlotButton btn = new SlotButton(bay);
                    changeStatus(bay,btn,"park","Vehicle parked successfully in slot: " + bay.getCode());
                    close();
              } else{
                  JOptionPane.showMessageDialog(view,"Failed to park vehicle.","Error",JOptionPane.ERROR_MESSAGE);
              }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(view,
                        "Slot is already occupied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
