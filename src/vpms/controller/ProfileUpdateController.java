/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.view.ProfileUpdateView;

/**
 *
 * @author Chandani
 */
public class ProfileUpdateController {
    // constructor
    private ProfileUpdateView view;
   public ProfileUpdateController (ProfileUpdateView view) {
       this.view= view;
       
       
   } 
   public void open(){
       view.setVisible(true);
   }
   public void close(){
       view.dispose();
   }
   class HandleUpdateProfile implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          
            String nameTextField = view.getnameField().getName();
            String emailTextField = view.getEmailField().getText();
            String phoneTextField = view.getPhoneField().getText();
            String typeComboBox = view.getTypeField().getSelectedItem().toString();
            String passwordField = new String(view.getPasswordField().getPassword());
            String conformPasswordField = new String(view.getConformField().getPassword());
        
            
              
        if (nameTextField.isEmpty() || emailTextField.isEmpty() || phoneTextField.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all the fields.");
            return;
        }

        if (!passwordField.equals(conformPasswordField)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match.");
            return;
        }

        //Call model/database code here to update the profile

        JOptionPane.showMessageDialog(view, "Profile updated successfully!");
        
        
        }
       
        
   }
}

