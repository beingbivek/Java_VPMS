
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.UserManagementView;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import vpms.view.EditUserView;

public class UserManagementController {

    private UserManagementView view;
    private UserDao userDao;

    public UserManagementController(UserManagementView view) {
        this.view = view;
        this.userDao = new UserDao();
        loadStaffData();
        DeleteUser deleteUser = new DeleteUser();
        this.view.deleteUser(deleteUser);
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }

    private void loadStaffData() {
        List<UserData> users = userDao.showUsers();
        (users == null)?view.getUserTable()

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
        }
    }

    class EditUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getUserTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a user to edit.", "No User Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                // Get current data from the selected row
                int id = (int) view.getUserTable().getValueAt(selectedRow, 0);
                EditUserView editUserView = new EditUserView();
                EditUserController controller = new EditUserController(editUserView,id);
                controller.open();
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

}
    
    
 

