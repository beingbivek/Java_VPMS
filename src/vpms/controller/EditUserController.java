package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.EditUserView;
import vpms.utils.ImageConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;

/**
 * Popup window for editing a user.
 * • Receives the full UserData object – so no extra DB query is required.  
 * • Keeps the old picture unless the operator selects a new one; if no
 *   picture exists, falls back to the default image bundled in resources.  
 * • Notifies the calling UserManagementController so the table refreshes
 *   when the update succeeds.
 */
public class EditUserController {

    private final EditUserView            view;      // JFrame
    private final UserManagementController caller;   // may be null
    private final UserDao                 dao = new UserDao();
    private       UserData                user;      // current record
    private int userID;
    private       File                    selected;  // new picture
    int id;

    public EditUserController(EditUserView view,
                              int userID,
                              UserManagementController caller, int id) {
        this.view   = view;
        this.userID   = userID;
        this.user = dao.getUserFromId(userID);
        this.caller = caller;
        this.id = id;

        fillForm();

        view.uploadButtonListener (new UploadListener());
        view.UpdateButtonListener(new SaveListener());
    }

    public void open() { view.setLocationRelativeTo(null); view.setVisible(true); }

    private void fillForm() {
        if (user == null) {
            JOptionPane.showMessageDialog(view,"User not found");
            view.dispose(); return;
        }
        view.getNameTextField()      .setText(user.getName());
        view.getEmailTextField()     .setText(user.getEmail());
        view.getPhoneTextField()     .setText(user.getPhone());
        view.getTypeField()          .setSelectedItem(user.getType());
        view.getPasswordField()      .setText(user.getPassword());
        view.getConfirmPasswordField().setText(user.getPassword());
    }

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

    private class SaveListener implements ActionListener {
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
            try {
                if (selected != null) {
                    img = new ImageConverter(selected).returnByteArray();
                } else if (user.getImage() != null && user.getImage().length > 0) {
                    img = user.getImage();
                } else {
                    img = new ImageConverter(null).returnByteArray();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"Image error"); return;
            }

            user = new UserData(user.getId(), name, type, email, pwd1, phone, img, user.getStatus());
            boolean ok = dao.updateUser(user);

            if (ok) {
                JOptionPane.showMessageDialog(view,"User updated");
                ActivityLog log = new ActivityLog(id,"User Edited, Obj: "+user);
                new ActivityLogDao().logActivity(log);
                if (caller != null) caller.refreshTable();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,"Update failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

