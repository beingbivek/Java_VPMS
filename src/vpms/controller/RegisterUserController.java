package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.RegisterUserView;
import vpms.utils.ImageConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Pop-up window (JFrame) for user registration.
 * When launched from UserManagementController it refreshes
 * the table after successful save.
 */
public class RegisterUserController {

    /* ---------- fields ---------- */
    private final RegisterUserView         view;      // this is a JFrame
    private final UserManagementController caller;    // may be null
    private       File                     selected;   // image file
    private final UserDao dao = new UserDao();

    /* ---------- ctors ---------- */
    public RegisterUserController(RegisterUserView view) {
        this(view,null);
    }
    public RegisterUserController(RegisterUserView view,
                                  UserManagementController caller) {
        this.view   = view;
        this.caller = caller;

        view.uploadButtonListener  (new UploadListener());
        view.registerButtonListener(new RegisterListener());
    }

    public void open()  { view.setLocationRelativeTo(null); view.setVisible(true); }

    /* ===================================================== *
     *  LISTENERS                                            *
     * ===================================================== */

    /* --- choose image ------------------------------------- */
    private class UploadListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Images","jpg","jpeg","png"));
            if (fc.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                selected = fc.getSelectedFile();
                JOptionPane.showMessageDialog(view,"Image: "+ selected.getName());
            }
        }
    }

    /* --- register user ------------------------------------ */
    private class RegisterListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            /* collect form data */
            String name  = view.getNameTextField().getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String type  = view.getTypeField().getSelectedItem().toString();
            String pwd1  = String.valueOf(view.getPasswordField().getPassword());
            String pwd2  = String.valueOf(view.getConfirmPasswordField().getPassword());

            /* validate */
            if (name.isEmpty()||email.isEmpty()||phone.isEmpty()||pwd1.isEmpty()||pwd2.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Fill in all the fields"); return;
            }
            if (!pwd1.equals(pwd2)) {
                JOptionPane.showMessageDialog(view,"Passwords do not match"); return;
            }

            /* image (default handled by ImageConverter) */
            byte[] img;
            try { img = new ImageConverter(selected).returnByteArray(); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"Image error"); return;
            }

            /* save */
            UserData u = new UserData(name,type,email,pwd1,phone,img);
            if (dao.registerUser(u)) {
                JOptionPane.showMessageDialog(view,"Registered successfully");

                /* refresh list if we have a caller */
                if (caller != null) caller.refreshTable();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,"Registration failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
