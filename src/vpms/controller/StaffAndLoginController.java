/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.view.StaffAndLoginView;

/**
 *
 * @author being
 */
public class StaffAndLoginController {
    StaffAndLoginView view = new StaffAndLoginView();

    public StaffAndLoginController(StaffAndLoginView welcomeView) {
        this.view = welcomeView;
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    
}
