/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vpms.view.AdminDashboardView;
import vpms.view.UsersView;

/**
 *
 * @author being
 */
public class AdminDashboardController {
    AdminDashboardView view = new AdminDashboardView();
    public AdminDashboardController(AdminDashboardView view){
        this.view = view;
//        this.view.userButtonListener(new OpenUserPanel());
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    
//    class OpenUserPanel implements ActionListener{
//        UsersView uView = new UsersView();
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            view.setWindowPanel(uView);
//        }
//        
//    }
}
