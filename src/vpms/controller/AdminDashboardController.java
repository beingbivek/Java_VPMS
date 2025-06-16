// AdminDashboardController.java (Revised)
package vpms.controller;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vpms.view.AdminDashboardView;
import vpms.view.UserManagementView;
import vpms.controller.UserManagementController;
import vpms.model.UserData;
import vpms.view.AdminDashboardContentView;
import vpms.view.WelcomeAndLoginView;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private UserManagementView smView;
    private AdminDashboardContentView adcView;
    UserData user;

    public AdminDashboardController(AdminDashboardView view,UserData user) {
        this.view = view;
        this.user = user;
        initializeControllers();
        attachListeners();
    }

    private void initializeControllers() {
        // Initialize sub-module controllers
        smView = new UserManagementView();
        new UserManagementController(smView);
        adcView = new AdminDashboardContentView();
        new AdminDashboardContentController(adcView);
    }

    private void attachListeners() {
        view.getUserWindowbtn().addActionListener(e -> showUsersPanel());
        view.getAdminDashboardWindowbtn().addActionListener(e -> showDashboard());
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
