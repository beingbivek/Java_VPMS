/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.LoginRequest;
import vpms.model.UserData;
import vpms.view.AdminDashboardView;
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
        DefaultAdminSeeder.insertDefaultAdminIfNotExists();
        this.view.loginUser(new LoginUser());
    }
    public void open(){
        this.view.setVisible(true);
//        this.view.setUndecorated(true); 
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.view.setResizable(false);
        
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = screenSize.width;
//        int height = screenSize.height;
//        System.out.println(width+"w and h "+height);
//        this.view.setSize(width, height);

    }
    public void close(){
        this.view.dispose();
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
            if ("staff".equalsIgnoreCase(user.getType())){
                StaffDashboardView dashboard = new StaffDashboardView();
                new StaffDashboardController(dashboard,user).open();
                close();
            }
            else if ("admin".equalsIgnoreCase(user.getType())) {
                view.dispose();
                AdminDashboardView dashboard = new AdminDashboardView();
                new AdminDashboardController(dashboard,user).open();
                close();
            }  
            else if(user != null){
                JOptionPane.showMessageDialog(view, "Invalid credentials. Try Again!");
            } else {
                JOptionPane.showMessageDialog(view, "You don't have account here!");
            }
                                    
        }
        
    }
}
