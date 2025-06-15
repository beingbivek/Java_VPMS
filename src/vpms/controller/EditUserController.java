package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.EditUserView;
import vpms.needed.ImageConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private       File                    selected;  // new picture

    /* ---------- ctor ---------- */
    public EditUserController(EditUserView view,
                              UserData user,
                              UserManagementController caller) {
        this.view   = view;
        this.user   = user;
        this.caller = caller;

        fillForm();                                      // show current data

        view.uploadButtonListener (new UploadListener());
        view.UpdateButtonListener(new SaveListener());
    }

    public void open() { view.setLocationRelativeTo(null); view.setVisible(true); }

    /* ---------- show data in form ---------- */
    private void fillForm() {
        if (user == null) {                              // safety-net
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
        }
    }

    /* --- save changes ------------------------------------- */
    private class SaveListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            /* read fields */
            String name  = view.getNameTextField().getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String type  = view.getTypeField().getSelectedItem().toString();
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
                JOptionPane.showMessageDialog(view,"User updated");
                if (caller != null) caller.refreshTable();               // refresh list
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,"Update failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
