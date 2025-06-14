/*  EditUserController.java  – rewritten, fully functional  */
package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.EditUserView;                   // exposes getters such as getNameTextField()[1]
import vpms.needed.ImageConverter;              // handles default-image fallback
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditUserController {

    /* -------------------------------------------------- *
     *  INSTANCE FIELDS                                   *
     * -------------------------------------------------- */
    private final EditUserView view;
    private final UserDao      dao  = new UserDao();
    private final int          userId;

    private File   selectedImage;   // newly chosen image file (may stay null)
    private byte[] currentImage;    // image already stored in DB

    /* -------------------------------------------------- *
     *  CONSTRUCTOR                                       *
     * -------------------------------------------------- */
    public EditUserController(EditUserView view, int id) {
        this.view   = view;
        this.userId = id;

        populateForm();                                 // load user data into fields

        view.uploadButtonListener (new UploadImageListener());   // register listeners
        view.UpdateButtonListener(new UpdateListener());
    }

    public void open()  { view.setVisible(true); }
    private void close(){ view.dispose();         }

    /* -------------------------------------------------- *
     *  INITIAL DATA FILL                                 *
     * -------------------------------------------------- */
    private void populateForm() {
        UserData u = dao.getUserFromId(userId);         // fetch from DB
        if (u == null) {                                // sanity-check
            JOptionPane.showMessageDialog(view,"User not found.");
            close(); return;
        }
        currentImage = u.getImage();                    // remember current image

        view.getNameTextField()      .setText(u.getName());          // field names from EditUserView[1]
        view.getEmailTextField()     .setText(u.getEmail());
        view.getPasswordField()      .setText(u.getPassword());
        view.getConfirmPasswordField().setText(u.getPassword());
        view.getPhoneTextField()     .setText(u.getPhone());
        view.getTypeField()          .setSelectedItem(u.getType());
    }

    /* -------------------------------------------------- *
     *  LISTENERS                                         *
     * -------------------------------------------------- */

    /* choose a new picture (optional) */
    private class UploadImageListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Images","jpg","jpeg","png"));

            if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                selectedImage = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(view,"Image selected: " + selectedImage.getName());
            }
        }
    }

    /* validate form & persist updates */
    private class UpdateListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            /* ---------- gather input ---------- */
            String name  = view.getNameTextField() .getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String type  = view.getTypeField().getSelectedItem().toString();
            String pwd   = String.valueOf(view.getPasswordField().getPassword());
            String pwd2  = String.valueOf(view.getConfirmPasswordField().getPassword());

            /* ---------- validate ---------- */
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd.isEmpty() || pwd2.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Fill in all the fields."); return;
            }
            if (!pwd.equals(pwd2)) {
                JOptionPane.showMessageDialog(view,"Passwords do not match."); return;
            }

            /* ---------- image handling ---------- */
            byte[] imgBytes;
            try {
                if (selectedImage != null) {                           // user picked new photo
                    imgBytes = new ImageConverter(selectedImage).returnByteArray();
                } else if (currentImage != null) {                     // keep existing
                    imgBytes = currentImage;
                } else {                                               // fall back to default
                    imgBytes = new ImageConverter(null).returnByteArray();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"Could not read image."); ex.printStackTrace(); return;
            }

            /* ---------- build DTO & update ---------- */
            UserData updated = new UserData(userId,name,type,email,pwd,phone,imgBytes);
            boolean ok = dao.updateUser(updated);

            /* ---------- feedback ---------- */
            if (ok) {
                JOptionPane.showMessageDialog(view,"User updated successfully.");
                close();
            } else {
                JOptionPane.showMessageDialog(view,"Failed to update user.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
