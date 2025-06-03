
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.StaffManagementView;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class StaffManagementController {

    private StaffManagementView view;
    private UserDao userDao;

    public StaffManagementController(StaffManagementView view) {
        this.view = view;
        this.userDao = new UserDao();
        loadStaffData();
        DeleteUser deleteUser = new DeleteUser();
        this.view.deleteUser(deleteUser);
    }

    private void loadStaffData() {
        List<UserData> users = userDao.showUsers();

        DefaultTableModel tableModel = (DefaultTableModel) view.getUserTable().getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (UserData user : users) {
            Object[] row = {
                user.getId(),
                user.getName(),
                user.getType(),
                user.getEmail(),
                user.getPassword(),
                user.getImage() // This is byte[], you may customize this column if needed
            };
            tableModel.addRow(row);
        }
    }

    class DeleteUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getUserTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "User not selected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete the selected user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int userId = (int) view.getUserTable().getValueAt(selectedRow, 0); // ID must be in column 0
                        boolean success = userDao.deleteUser(userId);
                        if (success) {
                            JOptionPane.showMessageDialog(view, "User deleted successfully");
                            loadStaffData(); // Refresh table
                        } else {
                            JOptionPane.showMessageDialog(view, "Failed to delete user", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        }
        
    }
  class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Here you would typically open a new dialog or frame for adding a user
            // For simplicity, let's show a basic input dialog for now.
            // In a real application, you'd have a separate AddUserView and AddUserController.

            String name = JOptionPane.showInputDialog(view, "Enter User Name:");
            if (name == null || name.trim().isEmpty()) return;

            String type = JOptionPane.showInputDialog(view, "Enter User Role (e.g., Staff, Admin):");
            if (type == null || type.trim().isEmpty()) return;

            String email = JOptionPane.showInputDialog(view, "Enter User Email:");
            if (email == null || email.trim().isEmpty()) return;

            String password = JOptionPane.showInputDialog(view, "Enter User Password:");
            if (password == null || password.trim().isEmpty()) return;

            // For image, you would normally have a file chooser. For this example, we'll use null.
            UserData newUser = new UserData(name, type, email); // No image for simplicity
            
            boolean success = userDao.registerUser(newUser);
            if (success) {
                JOptionPane.showMessageDialog(view, "User added successfully.");
                loadStaffData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add user. Email might already exist.", "Add User Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EditStaffListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getUserTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a user to edit.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                // Get current data from the selected row
                int id = (int) view.getUserTable().getValueAt(selectedRow, 0);
                String currentName = (String) view.getUserTable().getValueAt(selectedRow, 1);
                String currentType = (String) view.getUserTable().getValueAt(selectedRow, 2);
                String currentEmail = (String) view.getUserTable().getValueAt(selectedRow, 3);
                String currentPassword = (String) view.getUserTable().getValueAt(selectedRow, 4);
                byte[] currentImage = (byte[]) view.getUserTable().getValueAt(selectedRow, 5);

                // Prompt for updated information
                String newName = JOptionPane.showInputDialog(view, "Enter new name:", currentName);
                if (newName == null) return; // User cancelled

                String newType = JOptionPane.showInputDialog(view, "Enter new role (e.g., Staff, Admin):", currentType);
                if (newType == null) return;

                String newEmail = JOptionPane.showInputDialog(view, "Enter new email:", currentEmail);
                if (newEmail == null) return;

                String newPassword = JOptionPane.showInputDialog(view, "Enter new password:", currentPassword);
                if (newPassword == null) return;

                // Create a UserData object with updated info
                UserData updatedUser = new UserData(newName, newType, newEmail, newPassword,);
                updatedUser.setId(id); // Set the ID for the update query

                boolean success = userDao.updateUser(updatedUser);
                if (success) {
                    JOptionPane.showMessageDialog(view, "User updated successfully.");
                    loadStaffData(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update user. Email might already exist.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class FilterStaffListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm =  view.getSearchText().getText().trim();
            List<UserData> filteredUsers;
            if (searchTerm.isEmpty() || searchTerm.equals("Search                                    ")) {
                filteredUsers = userDao.showUsers(); // If search box is empty, show all users
            } else {
                filteredUsers = userDao.searchUsers(searchTerm);
            }

            DefaultTableModel tableModel = (DefaultTableModel) view.getUserTable().getModel();
            tableModel.setRowCount(0); // Clear existing rows

            if (filteredUsers.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No users found matching the search criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }

            for (UserData user : filteredUsers) {
                Object[] row = {
                    user.getId(),
                    user.getName(),
                    user.getType(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getImage()
                };
                tableModel.addRow(row);
            }
        }
    }

    class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Example: Clear the search text field and reload all data
            view.setSearchTextFieldValue("Search...."); // Reset search text
            loadStaffData(); // Reload all staff data
        }
    }

    // --- Navigation Listeners (Placeholders) ---
    // You would create separate views and controllers for these.
    /*
    class DashboardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Code to open DashboardView and close StaffManagementView
            // new DashboardView().setVisible(true);
            // view.dispose();
            JOptionPane.showMessageDialog(view, "Navigating to Dashboard!");
        }
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Code to return to login screen or exit application
                // new LoginView().setVisible(true);
                view.dispose(); // Close current window
                JOptionPane.showMessageDialog(view, "Logged out successfully!");
            }
        }
    }
    */
}
    
    
 

