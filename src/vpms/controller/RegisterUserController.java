package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.RegisterUserView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegisterUserController {
    private File selectedFile;
    private RegisterUserView registerUser;

    public RegisterUserController(RegisterUserView registerUser){
        this.registerUser = registerUser;
        
        // Attach listeners
        this.registerUser.registerButtonListener(new RegisterUser());
        this.registerUser.uploadButtonListener(new UploadImageListener());
    }

    public void open(){
        this.registerUser.setVisible(true);
    }

    public void close(){
        this.registerUser.dispose();
    }

    class UploadImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(registerUser);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(registerUser, "Image selected: " + selectedFile.getName());
            }
        }
    }

    class RegisterUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = registerUser.getNameTextField().getText();
            String email = registerUser.getEmailTextField().getText();
            String password = String.valueOf(registerUser.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(registerUser.getConfirmPasswordField().getPassword());
            String phone = registerUser.getPhoneTextField().getText();
            String type = registerUser.getTypeField().getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(registerUser, "Fill in all the fields");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerUser, "Passwords do not match");
            } else if (selectedFile == null) {
                JOptionPane.showMessageDialog(registerUser, "Please upload an image");
            } else {
                try {
                    byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());

                    UserData user = new UserData(name, type, email, password, phone, imageBytes);
                    UserDao userDao = new UserDao();
                    boolean result = userDao.registerUser(user);

                    if (result) {
                        JOptionPane.showMessageDialog(registerUser, "Registered Successfully");
                        close();
                    } else {
                        JOptionPane.showMessageDialog(registerUser, "Failed to Register");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(registerUser, "Failed to read image file");
                    ex.printStackTrace();
                }
            }
        }
    }
}
