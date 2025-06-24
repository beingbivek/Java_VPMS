/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.LoginRequest;
import vpms.model.UserData;
import vpms.utils.Constants;
import vpms.view.AdminDashboardView;
import vpms.view.ResetStaffPasswordView;
import vpms.view.StaffDashboardView;
import vpms.view.WelcomeAndLoginView;

/**
 *
 * @author being
 */
public class WelcomeAndLoginController {
    WelcomeAndLoginView view = new WelcomeAndLoginView();

    public WelcomeAndLoginController(WelcomeAndLoginView welcomeView) {
        this.view = welcomeView;
        try {
            DefaultAdminSeeder.insertDefaultAdminIfNotExists();
        } catch (Exception ex) {
            System.getLogger(WelcomeAndLoginController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        this.view.loginUser(new LoginUser());
        this.view.forgotPasswordListener(new ResetPassword());
    }
    public void open() {
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.view.setResizable(false);

        String[] saved = getSavedEmail();
        this.view.setEmailTextField(saved[0]);
        this.view.getRememberCheckBox().setSelected(Boolean.parseBoolean(saved[1]));
        this.view.viewPassword(new TogglePassword());
    }

    
    public void close(){
        this.view.dispose();
    }
    
    public void rememberEmail(String email) {
        try {
            Path path = Paths.get(Constants.defaultFileAddress());
            String remember = this.view.getRememberCheckBox().isSelected() ? "true" : "false";
            String content = email + "," + remember;
            if (this.view.getRememberCheckBox().isSelected()) {
                if (Files.notExists(path)) {
                    Files.createFile(path);
                }
                Files.write(path, content.getBytes(StandardCharsets.UTF_8));
            } else {
                Files.deleteIfExists(path);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public String[] getSavedEmail() {
        try {
            Path path = Paths.get(Constants.defaultFileAddress());
            if (Files.exists(path)) {
                String[] arr = Files.readString(path).split(",", 2);
                // Ensure array has both values
                if (arr.length == 2) return arr;
                if (arr.length == 1) return new String[]{arr[0], "false"};
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String[]{"", "false"};
    }

    
    class LoginUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();
            String password = new String(view.getPasswordField().getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.");
                return;
            }

            LoginRequest request = new LoginRequest(email, password);
            UserDao userDao = new UserDao();
            UserData user = userDao.loginUser(request);
            if (user == null){
                JOptionPane.showMessageDialog(view, "Invalid credentials. Try Again!");
            }
            else if ("staff".equalsIgnoreCase(user.getType())){
                StaffDashboardView dashboard = new StaffDashboardView();
                new StaffDashboardController(dashboard,user).open();
                rememberEmail(email);
                close();
            }
            else if ("admin".equalsIgnoreCase(user.getType())) {
                view.dispose();
                AdminDashboardView dashboard = new AdminDashboardView();
                new AdminDashboardController(dashboard,user).open();
                rememberEmail(email);
                close();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials. Try Again!");
            }
                                    
        }
        
    }
    
    class ResetPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            ResetStaffPasswordView rpView = new ResetStaffPasswordView();
            new ResetStaffPasswordController(rpView).open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    class TogglePassword implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(view.getPasswordCheckBox().isSelected()){
                view.getPasswordField().setEchoChar((char) 0);
            } else {
                view.getPasswordField().setEchoChar('*');
            }
        }
    }
}
