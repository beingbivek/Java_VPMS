/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import javax.swing.table.DefaultTableModel;
import vpms.dao.ActivityLogDao;
import vpms.dao.ParkingDao;
import vpms.dao.PaymentDao;
import vpms.dao.SlotInstanceDao;
import vpms.dao.UserDao;
import vpms.model.ActivityLog;
import vpms.model.UserData;
import vpms.view.AdminDashboardContentView;

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
    ActivityLogDao aDao;
    
    
    public AdminDashboardContentController(AdminDashboardContentView view){
        this.view = view;
        this.uDao = new UserDao();
       
        this.sDao = new SlotInstanceDao();
        this.pDao = new ParkingDao();
        this.pDao = new ParkingDao();
        this.paDao = new PaymentDao();
        this.aDao = new ActivityLogDao();
        
        insertDashboardData();
        loadRecentActivities();
        loadStaffTable();
    }
   
    public void open(){
        this.view.setVisible(true);
        insertDashboardData();
        loadRecentActivities();
        loadStaffTable();
    }
    public void close(){
        this.view.dispose();
    }
    
    public void insertDashboardData(){
        this.view.settotalActiveStffsLabel(String.valueOf(uDao.getActiveStaffCount()));
        this.view.setcurrentlyOccupiedSpacejLabel(String.valueOf(sDao.getTotalSlotCount()-sDao.getAvailableSlotCount())+"/"+String.valueOf(sDao.getTotalSlotCount()));
        this.view.setvehicleEnteredTodayjLabel(String.valueOf(pDao.getTotalVehicleEntryCount()));
        this.view.vehicleExitedTodayjLabel(String.valueOf(pDao.getExitedVehicleCount()));
        this.view.totalEarningsTodayjLabel(String.valueOf(paDao.getTotalRevenue()));
        
     
    }
    
    private void loadRecentActivities() {
        DefaultTableModel m = (DefaultTableModel) view.getActivityTable().getModel();
        m.setRowCount(0);

        for (ActivityLog l : aDao.fetchLast(30)) {      
            m.addRow(new Object[]{
                    l.getTimestamp(),                        
                    l.getAction(),                      
                    l.getUser_id() 
            });
        }
    }
    private void loadStaffTable() {
        DefaultTableModel m = (DefaultTableModel) view.getStaffTable().getModel();
        m.setRowCount(0);

        for (UserData u : uDao.showUsers()) {
            if (!"Staff".equalsIgnoreCase(u.getType())) continue;
            m.addRow(new Object[]{ u.getName(), u.getId(), u.getStatus() });
        }
    }

    }

    
    
  
    
  
