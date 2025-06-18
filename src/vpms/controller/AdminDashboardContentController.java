/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import vpms.dao.ParkingDao;
import vpms.dao.PaymentDao;
import vpms.dao.SlotInstanceDao;
import vpms.dao.UserDao;
import vpms.view.AdminDashboardContentView;
import vpms.view.UserManagementView;

/**
 *
 * @author being
 */
public class AdminDashboardContentController {
    private final AdminDashboardContentView view;
    UserDao uDao;
    SlotInstanceDao sDao;
    ParkingDao pDao;
    PaymentDao paDao;
    
    
    public AdminDashboardContentController(AdminDashboardContentView view) throws SQLException{
        this.view = view;
        this.uDao = new UserDao();
       
        this.sDao = new SlotInstanceDao();
        this.pDao = new ParkingDao();
        this.pDao = new ParkingDao();
        this.paDao = new PaymentDao();
    }
   
    public void open(){
        this.view.setVisible(true);
        insertDashboardData();
    }
    public void close(){
        this.view.dispose();
    }
    
    public void insertDashboardData(){
        this.view.settotalActiveStffsLabel(String.valueOf(uDao.getActiveStaffCount()));
        this.view.setcurrentlyOccupiedSpacejLabel(String.valueOf(sDao.getAvailableSlotCount())+"/"+String.valueOf(sDao.getTotalSlotCount()));
        this.view.setvehicleEnteredTodayjLabel(String.valueOf(pDao.getTotalVehicleEntryCount()));
        this.view.vehicleExitedTodayjLabel(String.valueOf(pDao.getExitedVehicleCount()));
        this.view.totalEarningsTodayjLabel(String.valueOf(paDao.getTotalRevenue()));
        
     
    }
    
  
    
  
}