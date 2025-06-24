/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import javax.swing.JOptionPane;
import vpms.dao.ParkingDao;
import vpms.dao.SlotInstanceDao;
import vpms.model.SlotInstanceData;
import vpms.view.ParkingExitView;
import vpms.view.StaffDashboardContentView;

public class StaffDashboardContentController {
    private final StaffDashboardContentView view;
    int id;
    public StaffDashboardContentController(StaffDashboardContentView view,int id,VehicleManagementController vmController) {
        this.view = view;
        this.id = id;
        new SlotGridController(this.view,this.id,vmController); // builds the grid tabs
        this.view.getTicketButton().addActionListener(e -> checkTicket());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.dispose();
    }
    
    public void checkTicket(){
       int ticketId = Integer.parseInt(view.getTicketIdNumber().getText().trim());
       int instance_id = new ParkingDao().getInstanceIdFromParkingId(ticketId);
       if(instance_id != -1){
           SlotInstanceData bay = new SlotInstanceDao().findByInstanceId(instance_id);
           ParkingExitView peView = new ParkingExitView();
           new ParkingExitController(peView,bay,id).open();
       } else {
           JOptionPane.showMessageDialog(view, "This Ticket ID is Invalid!");
       }
    }
}
