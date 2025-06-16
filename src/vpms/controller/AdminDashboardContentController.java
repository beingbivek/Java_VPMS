/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.view.AdminDashboardContentView;

/**
 *
 * @author being
 */
public class AdminDashboardContentController {
    private final AdminDashboardContentView view;
    public AdminDashboardContentController(AdminDashboardContentView view){
        this.view = view;
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
}
