/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.model.UserData;
import vpms.view.StaffDashboardView;

/**
 *
 * @author Chandani
 */
public class StaffDashboardController {
    private StaffDashboardView view;
    private UserData user;
    public StaffDashboardController(StaffDashboardView view, UserData user){
        this.view = view;
        this.user = user;
        // Setting up welcome label
        String fullName = user.getName();
        String[] nameParts = fullName.split(" ");
        this.view.setWelcomeLabel(nameParts[0]);
        
    }
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
    
}
