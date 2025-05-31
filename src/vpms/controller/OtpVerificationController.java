/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import vpms.view.OtpVerificationView;
import vpms.view.StaffAndLoginView;

/**
 *
 * @author being
 */
public class OtpVerificationController {
    OtpVerificationView view = new OtpVerificationView();
    public OtpVerificationController(OtpVerificationView view){
        this.view = view;
        this.view.backToLoginListerner(new LoginNavigation());
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
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
