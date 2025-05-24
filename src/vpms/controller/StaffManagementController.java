
package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.StaffManagementView;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StaffManagementController {

    private StaffManagementView view;
    private UserDao userDao;

    public StaffManagementController(StaffManagementView view) {
        this.view = view;
        this.userDao = new UserDao();
        loadStaffData();
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
}
