// AdminDashboardController.java (Revised)
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vpms.view.AdminDashboardView;
import vpms.view.UsersTestView;

public class AdminDashboardController {
    private final AdminDashboardView view;
    private UsersTestView usersView;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        initializeControllers();
        attachListeners();
    }

    private void initializeControllers() {
        // Initialize sub-module controllers
        usersView = new UsersTestView();
        new UserTestController(usersView);
    }

    private void attachListeners() {
        view.getUserWindowbtn().addActionListener(e -> showUsersPanel());
        view.getDesktopWindowbtn().addActionListener(e -> showDashboard());
        view.getLogoutBtn().addActionListener(e -> logout());
    }

    private void showUsersPanel() {
        usersView.setVisible(true);
        view.setWindowPanel(usersView);
        usersView.toFront();
        try {
            usersView.setSelected(true);
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
    }

    public void open() {
        view.setVisible(true);
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
