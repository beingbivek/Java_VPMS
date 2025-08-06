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
import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;

public class RegisterUserController {

    private final RegisterUserView         view;      // this is a JFrame
    private final UserManagementController caller;    // may be null
    private       File                     selected;   // image file
    private final UserDao dao = new UserDao();
    int id;
    
    public RegisterUserController(RegisterUserView view,
                                  UserManagementController caller, int id) {
        this.view   = view;
        this.caller = caller;
        this.id = id;

        view.uploadButtonListener  (new UploadListener());
        view.registerButtonListener(new RegisterListener());
    }

    public void open()  { view.setLocationRelativeTo(null); view.setVisible(true); }


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

            String name  = view.getNameTextField().getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String type  = view.getTypeField().getSelectedItem().toString();
            String pwd1  = String.valueOf(view.getPasswordField().getPassword());
            String pwd2  = String.valueOf(view.getConfirmPasswordField().getPassword());

            if (name.isEmpty()||email.isEmpty()||phone.isEmpty()||pwd1.isEmpty()||pwd2.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Fill in all the fields"); return;
            }
            if (!pwd1.equals(pwd2)) {
                JOptionPane.showMessageDialog(view,"Passwords do not match"); return;
            }

            byte[] img;
            try { img = new ImageConverter(selected).returnByteArray(); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"Image error"); return;
            }

            String status = view.getStatusField().getSelectedItem().toString();
            UserData u = new UserData(name, type, email, pwd1, phone, img, status);
            if (dao.registerUser(u)) {
                JOptionPane.showMessageDialog(view,"Registered successfully");
                ActivityLog log = new ActivityLog(id,"User Registered, Obj: "+u);
                new ActivityLogDao().logActivity(log);

                if (caller != null) caller.refreshTable();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,"Registration failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

