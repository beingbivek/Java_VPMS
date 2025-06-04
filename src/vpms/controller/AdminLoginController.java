/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import vpms.view.AdminLoginView;
import vpms.model.LoginRequest;
import vpms.model.UserData;
import vpms.dao.UserDao;




import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vpms.view.AdminDashboardView;


/**
 *
 * @author prabhash
 */
public class AdminLoginController {
   private final AdminLoginView view;
    private final UserDao userDao;

    public AdminLoginController(AdminLoginView view) { //constructor
        this.view = view;
        this.userDao = new UserDao();
//        DefaultAdminSeeder.insertDefaultAdminIfNotExists();
        this.view.addLoginButtonListener(new LoginHandler());
        this.view.addForgotPasswordListener(new ForgotPasswordHandler());
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }


    class LoginHandler implements ActionListener { //event handlers
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();
            String password = new String(view.getPasswordField().getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
                return;
            }

            LoginRequest request = new LoginRequest(email, password);
            UserData user = userDao.loginUser(request);
            
            

            if (user != null && "admin".equalsIgnoreCase(user.getType())) {
//                JOptionPane.showMessageDialog(view, "Login successful."); //POPUP. I don't think it is necessary.
                view.dispose();
                AdminDashboardView dashboard = new AdminDashboardView();
                dashboard.setVisible(true);
                // TODO: Navigate to dashboard
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials or not an admin.");
            }
        }
    }

    class ForgotPasswordHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            JOptionPane.showMessageDialog(view, "Forgot password clicked (functionality pending)."); another popup. might or mightnot add.
            // TODO: Navigate to or open ForgotPassword view
        }
    }
  
    
}
