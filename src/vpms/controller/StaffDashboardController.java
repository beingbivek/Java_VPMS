/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.model.UserData;
import vpms.view.StaffDashboardView;

import javax.swing.ImageIcon;
import java.awt.Image;
/**
 *
 * @author Chandani
 */
public class StaffDashboardController {
    private StaffDashboardView view;
    private UserData user;
    public StaffDashboardController(StaffDashboardView view, UserData user){
        this.view = view;
        this.user = user;
        // Setting up welcome label
        String firstName = user.getName().split(" ")[0];
        view.setWelcomeLabel(firstName);
        // For picture
        setProfilePicture();
        
    }
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
    
    private void setProfilePicture() {

        byte[] imgBytes = user.getImage();          // may be null / empty
        ImageIcon icon;

        if (imgBytes != null && imgBytes.length > 0) {
            icon = new ImageIcon(imgBytes);         // byte[] –> ImageIcon[6]
        } else {
            /* fallback to the default avatar bundled in resources */
            icon = new ImageIcon(getClass()
                     .getResource("/Icons/ProfileForLogin.jpg"));
        }

        /* scale to label size so it always fits */
        int w = view.getPictureLabel().getWidth();
        int h = view.getPictureLabel().getHeight();
        Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

        view.getPictureLabel().setIcon(new ImageIcon(scaled));
    }
    
    
    
}
