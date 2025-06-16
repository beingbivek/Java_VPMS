/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.model.UserData;
import vpms.view.StaffDashboardView;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import vpms.utils.ImageHelper;
import vpms.view.ProfileUpdateView;
import vpms.view.WelcomeAndLoginView;
/**
 *
 * @author Chandani
 */
public class StaffDashboardController {
    private StaffDashboardView view;
    private UserData user;
    private ProfileUpdateView puView;
    public StaffDashboardController(StaffDashboardView view, UserData user){
        this.view = view;
        this.user = user;
        attachListeners();
        initializeControllers();
        setWelcomeLabel();
        
    }
    
    private void initializeControllers() {
        // Initialize sub-module controllers
        puView = new ProfileUpdateView();
        new ProfileUpdateController(puView,user,StaffDashboardController.this);
    }

    private void attachListeners() {
        view.getUpdateProfileWindowbtn().addActionListener(e -> showUpdateProfilePanel());
//        view.getDesktopWindowbtn().addActionListener(e -> showDashboard());
        
        view.getLogoutBtn().addActionListener(e -> logout());
    }
    
    public void open(){
        view.setVisible(true);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        /* schedule picture setup AFTER layout is done */
        SwingUtilities.invokeLater(this::setProfilePicture);
    }
    public void close(){
        view.dispose();
    }
    
    private void showUpdateProfilePanel() {
        puView.setVisible(true);
        view.setWindowPanel(puView);
        puView.toFront();
        try {
            puView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setWelcomeLabel(){
        // Setting up welcome label
        String firstName = user.getName().split(" ")[0];
        view.setWelcomeLabel(firstName);
    }
    
    private void setProfilePicture() {
        ImageIcon icon = createUserIcon();              // unchanged logic
        Icon scaled    = ImageHelper.scaleToLabel(icon, view.getPictureLabel());
        view.getPictureLabel().setIcon(scaled);
    }
    private ImageIcon createUserIcon() {
        byte[] imgBytes = user.getImage();
        if (imgBytes != null && imgBytes.length > 0) return new ImageIcon(imgBytes);
        return new ImageIcon(getClass().getResource("/Icons/ProfileForLogin.jpg"));
    }
    
    public void updatedUserModel(UserData user){
        this.user = user;
        setProfilePicture();
        setWelcomeLabel();
    }
    private void logout() {
        view.dispose();
        // Add login screen activation logic here
        WelcomeAndLoginView welcomeView = new WelcomeAndLoginView();
        WelcomeAndLoginController controller = new WelcomeAndLoginController(welcomeView);
        controller.open();
        close();
    }
    
}
