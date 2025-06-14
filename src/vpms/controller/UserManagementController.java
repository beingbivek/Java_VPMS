/*  UserManagementController.java  –  rewritten to follow the same
 *  structure used in VehicleTypeAndPriceController
 *  (Add / Edit / Delete / Cancel / Search listeners registered
 *  in the constructor and all table-refresh logic in one place).
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.UserManagementView;              // JInternalFrame that contains the table and buttons[1]
import vpms.view.RegisterUserView;
import vpms.view.EditUserView;

public class UserManagementController {

    private final UserManagementView view;
    private final UserDao            dao;

    /* ---------- constructor ---------- */
    public UserManagementController(UserManagementView view) {
        this.view = view;
        this.dao  = new UserDao();

        loadUserData();                                      // fill table on start-up

        /* register all button / text-field listeners */
        this.view.addAddButtonListener   (new AddUserListener());   // [1]
        this.view.addEditButtonListener  (new EditUserListener());  // [1]
        this.view.addDeleteButtonListener(new DeleteUserListener());// [1]
        this.view.addCancelButtonListener(new CancelActionListener());
        this.view.addSearchButtonListener(new SearchListener());
    }

    public void open() { view.setVisible(true); }

    /* ---------- table refresh helper ---------- */
    private void loadUserData() {
        List<UserData> users = dao.showUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);                                       // clear old rows

        for (UserData u : users) {
            Object[] row = {
                u.getId(), u.getName(), u.getType(),
                u.getEmail(), u.getPassword(), u.getImage()
            };
            model.addRow(row);
        }
    }

    /* ---------- ADD ---------- */
    class AddUserListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            view.dispose();                                         // close list view
            RegisterUserView addView = new RegisterUserView();
            RegisterUserController addCtrl =
                    new RegisterUserController(addView);
            addCtrl.open();                                         // open “add” form
        }
    }

    /* ---------- EDIT ---------- */
    class EditUserListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view,"Please select a user to edit.");
                return;
            }
            int    id       = (int)    view.getTable().getValueAt(row,0);
            EditUserView editView = new EditUserView();
            EditUserController editCtrl =
                    new EditUserController(editView,id);
            view.dispose();
            editCtrl.open();
        }
    }

    /* ---------- DELETE ---------- */
    class DeleteUserListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view,"Select a user first.");
                return;
            }
            int choice = JOptionPane.showConfirmDialog(
                    view,"Delete selected user?","Confirm",JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) return;

            int id = (int) view.getTable().getValueAt(row,0);
            if (dao.deleteUser(id)) {
                JOptionPane.showMessageDialog(view,"User deleted.");
                loadUserData();                                     // refresh table
            } else {
                JOptionPane.showMessageDialog(view,"Delete failed.","Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* ---------- CANCEL (reset search) ---------- */
    class CancelActionListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            view.setSearchTextFieldValue("");                       // clear search box
            loadUserData();                                         // reload all rows
        }
    }

    /* ---------- SEARCH ---------- */
    class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchTextFieldValue()
                          .trim().toLowerCase();
            if (kw.isEmpty()) { loadUserData(); return; }

            List<UserData> users = dao.showUsers();
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);

            for (UserData u : users) {
                if ( String.valueOf(u.getId()).contains(kw) ||
                     u.getName().toLowerCase().contains(kw)        ||
                     u.getEmail().toLowerCase().contains(kw)       ||
                     u.getType().toLowerCase().contains(kw)) {

                    Object[] row = {
                        u.getId(), u.getName(), u.getType(),
                        u.getEmail(), u.getPassword(), u.getImage()
                    };
                    model.addRow(row);
                }
            }
        }
    }
}

    
    
 

