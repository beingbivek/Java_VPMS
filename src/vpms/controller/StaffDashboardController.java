/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.model.UserData;
import vpms.view.StaffDashboardView;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import vpms.utils.ImageHelper;
import vpms.view.ProfileUpdateView;
import vpms.view.SecurePaymentView;
import vpms.view.StaffDashboardContentView;
import vpms.view.VehicleManagementView;
import vpms.view.WelcomeAndLoginView;
/**
 *
 * @author Chandani
 */
public class StaffDashboardController {
    private StaffDashboardView view;
    private UserData user;
    private ProfileUpdateView puView;
    private StaffDashboardContentView sdcView;
    private SecurePaymentView pView;
    private VehicleManagementView vView;
    
    public StaffDashboardController(StaffDashboardView view, UserData user){
        this.view = view;
        this.user = user;
        attachListeners();
        initializeControllers();
        setWelcomeLabel();
        showDashboard();
        
    }
    
    private void initializeControllers() {
        // Initialize sub-module controllers
        vView = new VehicleManagementView();
        VehicleManagementController vmController = new VehicleManagementController(vView,user.getId());
        puView = new ProfileUpdateView();
        ProfileUpdateController puController = new ProfileUpdateController(puView,user,StaffDashboardController.this);
        sdcView = new StaffDashboardContentView();
        StaffDashboardContentController sdcController = new StaffDashboardContentController(sdcView,user.getId(),vmController);
        pView = new SecurePaymentView();
        SecurePaymentController spController = new SecurePaymentController(pView);
        
    }

    private void attachListeners() {
        view.getUpdateProfileWindowbtn().addActionListener(e -> showUpdateProfilePanel());
        view.getDesktopWindowbtn().addActionListener(e -> showDashboard());
        view.getPaymentWindowbtn().addActionListener(e -> showPaymentPanel());
        view.getVehicleWindowbtn().addActionListener(e -> showVehiclePanel());
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
    
    private void showPaymentPanel() {
        pView.setVisible(true);
        view.setWindowPanel(pView);
        pView.toFront();
        try {
            pView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
    
    private void showVehiclePanel() {
        vView.setVisible(true);
        view.setWindowPanel(vView);
        vView.toFront();
        try {
            vView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
    
    private void showDashboard() {
        sdcView.setVisible(true);
        view.setWindowPanel(sdcView);
        sdcView.toFront();
        try {
            sdcView.setSelected(true);
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
        return new ImageIcon(getClass().getResource("/Icons/ProfileForLogin.png"));
    }
    
    public void updatedUserModel(UserData user){
        this.user = user;
        setProfilePicture();
        setWelcomeLabel();
    }
    private void logout() {
        close();
        // Add login screen activation logic here
        WelcomeAndLoginView welcomeView = new WelcomeAndLoginView();
        WelcomeAndLoginController controller = new WelcomeAndLoginController(welcomeView);
        controller.open();
        close();
    }
    
}
