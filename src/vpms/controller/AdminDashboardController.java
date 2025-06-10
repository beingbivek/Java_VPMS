// AdminDashboardController.java (Revised)
package vpms.controller;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vpms.view.AdminDashboardView;
import vpms.view.UserManagementView;
import vpms.controller.UserManagementController;
import vpms.view.StaffAndLoginView;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private UserManagementView smView;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        initializeControllers();
        attachListeners();
    }

    private void initializeControllers() {
        // Initialize sub-module controllers
        smView = new UserManagementView();
        new UserManagementController(smView);
    }

    private void attachListeners() {
        view.getUserWindowbtn().addActionListener(e -> showUsersPanel());
        view.getDesktopWindowbtn().addActionListener(e -> showDashboard());
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
        view.getWindowPanel().removeAll();
        view.revalidate();
        view.repaint();
    }

    private void logout() {
        view.dispose();
        // Add login screen activation logic here
        StaffAndLoginView welcomeView = new StaffAndLoginView();
        StaffAndLoginController controller = new StaffAndLoginController(welcomeView);
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
