// AdminDashboardController.java (Revised)
package vpms.controller;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import vpms.view.AdminDashboardView;
import vpms.view.UserManagementView;
import vpms.model.UserData;
import vpms.view.AdminDashboardContentView;
import vpms.view.SlotManagementView;
import vpms.view.VehicleTypeAndPriceManagementView;
import vpms.view.WelcomeAndLoginView;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private UserManagementView smView;
    private AdminDashboardContentView adcView;
    private SlotManagementView sView;
    private VehicleTypeAndPriceManagementView vtpView;
    UserData user;

    public AdminDashboardController(AdminDashboardView view,UserData user) {
        this.view = view;
        this.user = user;
        initializeControllers();
        attachListeners();
    }

    private void initializeControllers() {
        try {
            // Initialize sub-module controllers
            smView = new UserManagementView();
            new UserManagementController(smView);
            adcView = new AdminDashboardContentView();
            new AdminDashboardContentController(adcView);
            sView = new SlotManagementView();
            new SlotManagementController(sView);
            vtpView = new VehicleTypeAndPriceManagementView();
            new VehicleTypeAndPriceController(vtpView);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void attachListeners() {
        view.getUserWindowbtn().addActionListener(e -> showUsersPanel());
        view.getAdminDashboardWindowbtn().addActionListener(e -> showDashboard());
        view.getSlotWindowbtn().addActionListener(e -> showSlotsPanel());
        view.getVehicleTypeandPriceWindowbtn().addActionListener(e -> showVehicleTandPPanel());
        view.getLogoutBtn().addActionListener(e -> logout());
    }

    private void showUsersPanel() {
        smView.setVisible(true);
        view.setWindowPanel(smView);
        smView.toFront();
        try {
            smView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    private void showDashboard() {
        adcView.setVisible(true);
        view.setWindowPanel(adcView);
        adcView.toFront();
        try {
            adcView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
    
    private void showSlotsPanel() {
        sView.setVisible(true);
        view.setWindowPanel(sView);
        sView.toFront();
        try {
            sView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }
    
    private void showVehicleTandPPanel() {
        vtpView.setVisible(true);
        view.setWindowPanel(vtpView);
        vtpView.toFront();
        try {
            vtpView.setSelected(true);
        } catch (java.beans.PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    private void logout() {
        view.dispose();
        // Add login screen activation logic here
        WelcomeAndLoginView welcomeView = new WelcomeAndLoginView();
        WelcomeAndLoginController controller = new WelcomeAndLoginController(welcomeView);
        controller.open();
        close();
    }

    public void open() {
        view.setVisible(true);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    public void close(){
        view.dispose();
    }
}
