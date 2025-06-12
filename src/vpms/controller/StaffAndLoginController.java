/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    class LoginStaffUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailField().getText();
            String password = String.valueOf(view.getPasswordField().getPassword());
            String type = "Staff";
                                    
        }
        
    }
}
