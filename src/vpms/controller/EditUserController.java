/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.EditUserView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author sahingurung
 */
public class EditUserController {

    private EditUserView editUser;
    private File selectedFile;

    public EditUserController(EditUserView editUser, int id) {
        this.editUser = editUser;
        UserDao userDao = new UserDao();
        
        UserData user = userDao.getUserFromId(id);
        user.getName();
        setDetails(user,editUser);
        // Attach listeners
        this.editUser.UpdateButtonListener(new UpdateUser());
        this.editUser.uploadButtonListener(new UploadImageListener());
    }

    public void open() {
        this.editUser.setVisible(true);
    }

    public void close() {
        this.editUser.dispose();
    }

    class UploadImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(editUser);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(editUser, "Image selected: " + selectedFile.getName());
            }
        }
    }
 
    private static void setDetails(UserData user,EditUserView editUser) {
        editUser.getNameTextField().setText(user.getName());
        editUser.getEmailTextField().setText(user.getEmail());
        editUser.getPasswordField().setText(user.getPassword());
        editUser.getConfirmPasswordField().setText(user.getPassword());
        editUser.getPhoneTextField().setText(user.getPhone());
        editUser.getTypeField().setSelectedItem(user.getType());
    
}

    class UpdateUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get input values
            String name = editUser.getNameTextField().getText();
            String email = editUser.getEmailTextField().getText();
            String password = String.valueOf(editUser.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(editUser.getConfirmPasswordField().getPassword());
            String phone = editUser.getPhoneTextField().getText();
            String type = editUser.getTypeField().getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(editUser, "Please fill in all the fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(editUser, "Passwords do not match.");
                return;
            }

            byte[] imageBytes = null;
            try {
                if (selectedFile != null) {
                    imageBytes = Files.readAllBytes(selectedFile.toPath());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(editUser, "Error reading selected image.");
                ex.printStackTrace();
                return;
            }

            UserData user = new UserData(name, type, email, password, phone, imageBytes);
            UserDao userDao = new UserDao();
            boolean result = userDao.updateUser(user); // Make sure this method exists

            if (result) {
                JOptionPane.showMessageDialog(editUser, "User updated successfully.");
                close();
            } else {
                JOptionPane.showMessageDialog(editUser, "Failed to update user.");
            }
        }
    }
}