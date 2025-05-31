/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JOptionPane;
import vpms.controller.mail.SMTPSMailSender;
import vpms.dao.UserDao;
import vpms.view.OtpVerificationView;
import vpms.view.ResetStaffPasswordView;
import vpms.view.StaffAndLoginView;

/**
 *
 * @author being
 */
public class ResetStaffPasswordController {
    ResetStaffPasswordView view = new ResetStaffPasswordView();
    public ResetStaffPasswordController(ResetStaffPasswordView view){
        this.view = view;
        this.view.sendOTPListener(new SendOTP());
        this.view.backToLoginListerner(new LoginNavigation());
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    class SendOTP implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();
            if(email.isEmpty()){
                JOptionPane.showMessageDialog(view, "Email cannot be empty");
            }else{
                UserDao userDao = new UserDao();
                boolean emailExists = userDao.checkEmail(email);
                if(!emailExists){
                    JOptionPane.showMessageDialog(view,"Email doesnot exist");
                }else{
                    Random random = new Random();
                    int otp = 100000 + random.nextInt(900000);
                    String OTP = String.valueOf(otp);
                    boolean mailSent = SMTPSMailSender.sendMail(email,"Reset Password Verfication","The OTP to reset your password is "+OTP);
                    if(!mailSent){
                        JOptionPane.showMessageDialog(view, "Failed to send OTP. Try Again later!");
                    }else{
                        OtpVerificationView view = new OtpVerificationView();
                        OtpVerificationController controller = new OtpVerificationController(view,OTP,email);
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
