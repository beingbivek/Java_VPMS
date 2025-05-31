/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.model.UserData;
import vpms.view.StaffDashbaordView;

/**
 *
 * @author Chandani
 */
public class StaffDashboardController {
    private StaffDashbaordView view;
    public StaffDashboardController(StaffDashbaordView view, UserData user){
        this.view = view;
    }
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
}
