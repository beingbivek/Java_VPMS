/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.utils.ImageConverter;
import vpms.view.ProfileUpdateView;

/**
 *
 * @author being
 */
public class ProfileUpdateController {
    private final ProfileUpdateView            view;
    private final UserDao                 dao = new UserDao();
    private       UserData                user;      // current record
    private       File                    selected;  // new picture
    private StaffDashboardController sdController;

    /* ---------- ctor ---------- */
    public ProfileUpdateController(ProfileUpdateView view,
                              UserData user, StaffDashboardController sdController) {
        this.view   = view;
        this.user   = user;
        this.sdController = sdController;

        fillForm();                                      // show current data
        setProfilePicture();
        view.uploadButtonListener (new UploadListener());
        view.UpdateButtonListener(new SaveListener());
    }

    public void open() { view.setVisible(true); }

    /* ---------- show data in form ---------- */
    private void fillForm() {
        if (user == null) {                              // safety-net
            JOptionPane.showMessageDialog(view,"User not found");
            view.dispose(); return;
        }
        view.getNameTextField()      .setText(user.getName());
        view.getEmailTextField()     .setText(user.getEmail());
        view.getPhoneTextField()     .setText(user.getPhone());
        view.getPasswordField()      .setText(user.getPassword());
        view.getConfirmPasswordField().setText(user.getPassword());
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

    /* ===================================================== *
     *  LISTENERS                                            *
     * ===================================================== */

    /* --- choose new picture (optional) -------------------- */
    private class UploadListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Images","jpg","jpeg","png"));
            if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                selected = fc.getSelectedFile();
                JOptionPane.showMessageDialog(view,"Image: "+ selected.getName());
            }
            setProfilePicture();
        }
    }

    /* --- save changes ------------------------------------- */
    private class SaveListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            /* read fields */
            String name  = view.getNameTextField().getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String type  = "Staff";
            String pwd1  = String.valueOf(view.getPasswordField().getPassword());
            String pwd2  = String.valueOf(view.getConfirmPasswordField().getPassword());

            /* basic validation */
            if (name.isEmpty()||email.isEmpty()||phone.isEmpty()||pwd1.isEmpty()||pwd2.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Fill in all the fields"); return;
            }
            if (!pwd1.equals(pwd2)) {
                JOptionPane.showMessageDialog(view,"Passwords do not match"); return;
            }

            /* picture handling */
            byte[] img;
            try {
                if (selected != null) {                                  // new file
                    img = new ImageConverter(selected).returnByteArray();
                } else if (user.getImage() != null && user.getImage().length > 0) {
                    img = user.getImage();                               // keep old
                } else {
                    img = new ImageConverter(null).returnByteArray();    // default
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"Image error"); return;
            }

            /* build new object & persist */
            user = new UserData(user.getId(), name, type, email, pwd1, phone, img);
            boolean ok = dao.updateUser(user);

            if (ok) {
                JOptionPane.showMessageDialog(view,"User updated, Logout and Login to show latest updates.");
                sdController.updatedUserModel(user);
            } else {
                JOptionPane.showMessageDialog(view,"Update failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
