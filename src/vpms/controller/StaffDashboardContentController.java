/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.sql.SQLException;
import vpms.view.StaffDashboardContentView;

/**
 *
 * @author being
 */
public class StaffDashboardContentController {
    private final StaffDashboardContentView view;
    public StaffDashboardContentController(StaffDashboardContentView view) throws SQLException{
        this.view = view;
        new SlotGridController(this.view);
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
}
