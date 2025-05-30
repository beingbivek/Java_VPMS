package vpms.controller;

import java.awt.event.ActionEvent;
import vpms.view.StaffAndLoginView;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.LoginRequest;
import vpms.model.UserData;
import vpms.view.AdminLoginView;
import vpms.view.ResetStaffPasswordView;
import vpms.view.StaffDashbaordView;

/**
 *
 * @author Chandani
 */
public class StaffAndLoginController {
    StaffAndLoginView view = new StaffAndLoginView();
    public StaffAndLoginController (StaffAndLoginView view){
        this.view = view;
      
        this.view.loginStaff(new StaffAndLoginController.HandleLogin());
        this.view.addSignAdminListener(new StaffAndLoginController.AdminLoginPage());
    }
      public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }
    

    class HandleLogin implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent ae) {
            String email = view.getEmailField().getText();
            String password = new String(view.getPasswordField().getPassword());
            if(email.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(view, "Fill in all the fields.");
            }else{
                LoginRequest loginReq = new LoginRequest(email,password);
                UserDao userDao = new UserDao();
                UserData user = userDao.loginUser(loginReq);
                if(user == null){
                    JOptionPane.showMessageDialog(view, "Login failed");
                }else{
                    StaffDashbaordView dashbaordView = new StaffDashbaordView();
                    StaffDashboardController dashboardController = new StaffDashboardController(dashbaordView,user);
                    dashboardController.open();
                    close();
                }
            }
            
        }

    }
    class AdminLoginPage implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            AdminLoginView adminLoginView = new AdminLoginView();
            AdminLoginController adminLoginController = new AdminLoginController(adminLoginView);
            adminLoginController.open();
            close();
            
        }
        
    }
    
    class ResetPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            ResetStaffPasswordView view = new ResetStaffPasswordView();
            ResetStaffPasswordController controller = new ResetStaffPasswordController(view);
            controller.open();
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

  
}

    

