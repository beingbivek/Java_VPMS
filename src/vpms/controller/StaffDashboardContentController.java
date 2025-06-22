/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.sql.SQLException;
import vpms.view.StaffDashboardContentView;

public class StaffDashboardContentController {
    private final StaffDashboardContentView view;
    int id;
    public StaffDashboardContentController(StaffDashboardContentView view,int id) {
        this.view = view;
        this.id = id;
        new SlotGridController(this.view,this.id); // builds the grid tabs
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
}
