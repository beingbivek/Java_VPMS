/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.RegisterUserView;

/**
 *
 * @author sahingurung
 */
public class RegisterUserController {
    private RegisterUserView registerUser= new RegisterUserView();
    public RegisterUserController(RegisterUserView registerUser){
        this.registerUser=registerUser;       
    }
    public void open(){
        this.registerUser.setVisible(true);
    }
    public void close(){
        this.registerUser.dispose();
    }
    class RegisterUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
//            assign required values to variables
            String name= registerUser.getNameTextField().getText();
            String email = registerUser.getEmailTextField().getText();
            String password = String.valueOf(registerUser.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(registerUser.getConfirmPasswordField().getPassword());
            String type = registerUser.getTypeField().getSelectedItem().toString();
            if (name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
                JOptionPane.showMessageDialog(registerUser, "Fill in all the fields");
            } else if (!password.equals(confirmPassword)){
                JOptionPane.showMessageDialog(registerUser,"Passwords do not match");
            } else{
                UserData user = new UserData(name,email,password,type);
                UserDao userDao = new UserDao();
                boolean result = userDao.registerUser(user);
                if (result){
                    JOptionPane.showMessageDialog(registerUser, "Registered Successfully");
                    close();
                } else {
                JOptionPane.showMessageDialog(registerUser,"Failed to Register");
                    
                }
            }
        }
        
    }
}
