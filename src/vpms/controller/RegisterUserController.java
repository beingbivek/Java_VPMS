package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.RegisterUserView;
import vpms.needed.ImageConverter;
import vpms.needed.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Handles registration logic.  
 * – If the user selects a picture, store it.  
 * – If not, fall back to the default image defined in Constants.defaultImagePath().
 */
public class RegisterUserController {

    /* ---------- instance fields ---------- */
    private final RegisterUserView view;
    private File selectedImage;                    // null until user picks one
    private final UserDao userDao = new UserDao(); // DAO is reused

    /* ---------- ctor ---------- */
    public RegisterUserController(RegisterUserView view) {
        this.view = view;

        /* hook listeners provided by the View */
        view.uploadButtonListener  (new UploadImageListener());
        view.registerButtonListener(new RegisterListener());
    }

    public void open()  { view.setVisible(true); }
    public void close() { view.dispose();        }

    /* ===================================================== *
     *  LISTENERS                                            *
     * ===================================================== */

    /** Opens a JFileChooser and stores the chosen image file. */
    private class UploadImageListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(
                    new FileNameExtensionFilter("Images","jpg","jpeg","png"));

            if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                selectedImage = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(
                        view,"Image selected: " + selectedImage.getName());
            }
        }
    }

    /** Validates input, converts (or falls-back) image, inserts user row. */
    private class RegisterListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {

            /* ---------- read form fields ---------- */
            String name     = view.getNameTextField().getText().trim();
            String email    = view.getEmailTextField().getText().trim();
            String phone    = view.getPhoneTextField().getText().trim();
            String type     = view.getTypeField().getSelectedItem().toString();
            String pwd      = String.valueOf(view.getPasswordField().getPassword());
            String pwdAgain = String.valueOf(view.getConfirmPasswordField().getPassword());

            /* ---------- basic validation ---------- */
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                    || pwd.isEmpty() || pwdAgain.isEmpty()) {
                JOptionPane.showMessageDialog(view,"Fill in all the fields");
                return;
            }
            if (!pwd.equals(pwdAgain)) {
                JOptionPane.showMessageDialog(view,"Passwords do not match");
                return;
            }

            /* ---------- image handling (default support) ---------- */
            byte[] imageBytes;
            try {
                // ImageConverter returns default image whenever selectedImage == null
                imageBytes = new ImageConverter(selectedImage).returnByteArray();
            } catch (Exception ex) {                       // IO / path errors
                JOptionPane.showMessageDialog(view,"Could not read image file");
                ex.printStackTrace();
                return;
            }

            /* ---------- build model & persist ---------- */
            UserData newUser = new UserData(name,type,email,pwd,phone,imageBytes);
            boolean ok = userDao.registerUser(newUser);            // inserts row[1]

            /* ---------- feedback ---------- */
            if (ok) {
                JOptionPane.showMessageDialog(view,"Registered successfully");
                close();
            } else {
                JOptionPane.showMessageDialog(view,"Registration failed",
                                              "Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
