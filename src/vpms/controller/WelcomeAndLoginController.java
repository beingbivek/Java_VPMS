/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    }
    public void open(){
        this.view.setVisible(true);
//        this.view.setUndecorated(true); 
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.view.setResizable(false);
        this.view.setEmailTextField(getSavedEmail());
        this.view.viewPassword(new TogglePassword());
        
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = screenSize.width;
//        int height = screenSize.height;
//        System.out.println(width+"w and h "+height);
//        this.view.setSize(width, height);

    }
    public void close(){
        this.view.dispose();
    }
    
    public void rememberEmail(String email){
        try{
            Path path = Paths.get(Constants.defaultFileAddress());
            if (this.view.getRememberCheckBox().isSelected()){
                if(Files.notExists(path)){
                    Files.createFile(path);
                }
                Files.write(Paths.get(Constants.defaultFileAddress()), email.getBytes(StandardCharsets.UTF_8));
            }else{
                Files.deleteIfExists(path);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String getSavedEmail(){
        try{
            Path path = Paths.get(Constants.defaultFileAddress());
            if(Files.exists(path)){
                return Files.readString(Paths.get(Constants.defaultFileAddress()));
            }
            return "";
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return "";
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
