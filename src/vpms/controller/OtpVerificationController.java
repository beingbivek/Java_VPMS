/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.ResetPasswordRequest;
import vpms.view.OtpVerificationView;
import vpms.view.StaffAndLoginView;

/**
 *
 * @author being
 */
public class OtpVerificationController {
    OtpVerificationView view = new OtpVerificationView();
    private String otp;
    private String email;
    public OtpVerificationController(OtpVerificationView view, String otp, String email){
        this.view = view;
        this.otp = otp;
        this.email = email;
        this.view.backToLoginListerner(new LoginNavigation());
        this.view.verifyOTP(new VerifyOTP());
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    
    class VerifyOTP implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String otpReceived = view.getOTPTextField().getText();
            if (!otp.equals(otpReceived)) {
                JOptionPane.showMessageDialog(view, "OTP did not Match");
            } else {
                String password = JOptionPane.showInputDialog(view, "Enter your new password");
                if (password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Password is Empty");
                } else {
                    ResetPasswordRequest resetPassword = new ResetPasswordRequest(email, password);
                    UserDao userDao = new UserDao();
                    boolean updateResult = userDao.resetPassword(resetPassword);
                    if (!updateResult) {
                        JOptionPane.showMessageDialog(view, "Failed to reset password");
                    } else {
                        JOptionPane.showMessageDialog(view, "Password has been changed");
                        StaffAndLoginView view = new StaffAndLoginView();
                        StaffAndLoginController controller = new StaffAndLoginController(view);
                        controller.open();
                        close();
                    }
                }
            }
        }
        
    }
    
    class LoginNavigation implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            StaffAndLoginView view = new StaffAndLoginView();
            StaffAndLoginController controller = new StaffAndLoginController(view);
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
