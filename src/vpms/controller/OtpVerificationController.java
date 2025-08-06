/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.ResetPasswordRequest;
import vpms.view.OtpVerificationView;
import vpms.view.WelcomeAndLoginView;

/**
 *
 * @author being
 */
public class OtpVerificationController {
    OtpVerificationView view = new OtpVerificationView();
    private final String otp;
    private final String email;
    public OtpVerificationController(OtpVerificationView view, String otp, String email){
        this.view = view;
        this.otp = otp;
        this.email = email;
        this.view.backToLoginListerner(new LoginNavigation());
        this.view.verifyOTP(new VerifyOTP());
    }
    public void open(){
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.view.setResizable(false);
        view.getOTPTextField().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (view.getOTPTextField().getText().equalsIgnoreCase("enter otp")) {
                    view.getOTPTextField().setText("");
                }
            }
        });
    }
    public void close(){
        this.view.dispose();
    }
    
    class VerifyOTP implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String otpReceived = view.getOTPTextField().getText().strip();
            if (!otp.equals(otpReceived)) {
                JOptionPane.showMessageDialog(view, "OTP did not Match");
            } else {
                String password = JOptionPane.showInputDialog(view, "Enter your new password");
                if (password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Password is Empty");
                } else if (password.trim().length() < 6){
                    JOptionPane.showMessageDialog(view, "Password should be atleast 6 characters!");
                } else {
                    ResetPasswordRequest resetPassword = new ResetPasswordRequest(email, password);
                    UserDao userDao = new UserDao();
                    boolean updateResult = userDao.resetPassword(resetPassword);
                    if (!updateResult) {
                        JOptionPane.showMessageDialog(view, "Failed to reset password, Try Again Later!");
                    } else {
                        JOptionPane.showMessageDialog(view, "Password has been changed successfully!");
                        WelcomeAndLoginView view = new WelcomeAndLoginView();
                        WelcomeAndLoginController controller = new WelcomeAndLoginController(view);
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
            WelcomeAndLoginView view = new WelcomeAndLoginView();
            WelcomeAndLoginController controller = new WelcomeAndLoginController(view);
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