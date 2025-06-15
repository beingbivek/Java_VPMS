package vpms.controller;

import vpms.dao.UserDao;
import vpms.model.UserData;
import vpms.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Lists, searches, adds, edits and deletes users.
 * Receives the AdminDashboardView so it can open child
 * internal-frames inside the SAME JDesktopPane.
 */
public class UserManagementController {

    /* -------------- fields -------------- */
    private final UserManagementView view;
    private final UserDao dao = new UserDao();

    /* -------------- ctor -------------- */
    public UserManagementController(UserManagementView view) {
        this.view      = view;

        loadUserData();

        /* hook all listeners coming from view */
        view.addAddButtonListener   (new AddUserListener());
        view.addEditButtonListener  (new EditUserListener());
        view.addDeleteButtonListener(new DeleteUserListener());
        view.addCancelButtonListener(new CancelActionListener());
        view.addSearchButtonListener(new SearchListener());
    }

    public void open()  { view.setVisible(true); }
    public void close() { view.dispose();        }

    /* -------------- helper for other controllers -------------- */
    public void refreshTable() { loadUserData(); }
    public UserManagementView getView() { return view; }

    /* -------------- table population -------------- */
    private void loadUserData() {
        List<UserData> users = dao.showUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        for (UserData u : users) {
            model.addRow(new Object[]{
                    u.getId(), u.getName(), u.getType(), u.getEmail(),
                    u.getPassword(), u.getPhone(), u.getImage()
            });
        }
    }

    /* ===================================================== *
     *  LISTENERS                                            *
     * ===================================================== */

    class AddUserListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            RegisterUserView popup = new RegisterUserView();               // JFrame
            new RegisterUserController(popup, UserManagementController.this)
                    .open();                                               // opens as stand-alone window
        }
    }

    /* ---- EDIT ----------------------------------------------- */
    class EditUserListener implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view,"Select a user to edit."); return;
        }

        /* Build a UserData from the current table row */
        UserData selected = new UserData(
            (int)    view.getTable().getValueAt(row,0),   // id
            (String) view.getTable().getValueAt(row,1),   // name
            (String) view.getTable().getValueAt(row,2),   // type
            (String) view.getTable().getValueAt(row,3),   // email
            (String) view.getTable().getValueAt(row,4),   // password
            (String) view.getTable().getValueAt(row,5),   // phone
            (byte[]) view.getTable().getValueAt(row,6)    // image
        );

        EditUserView popup = new EditUserView();          // JFrame
        new EditUserController(popup, selected, UserManagementController.this)
              .open();
    }
}


    /* ---- DELETE --------------------------------------------- */
    class DeleteUserListener implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Select a user first."); return;
        }

        if (JOptionPane.showConfirmDialog(view, "Delete selected user?",
                "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        int id = (int) view.getTable().getValueAt(row, 0);

        try {
            if (dao.deleteUser(id)) {
                JOptionPane.showMessageDialog(view, "User deleted.");
                loadUserData();                         // refresh table
            } else {
                JOptionPane.showMessageDialog(view,
                        "No record was removed.\nThe user may already be deleted.",
                        "Delete failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            /* display exact DB reason */
            JOptionPane.showMessageDialog(view,
                    "Error deleting user:\n" + ex.getMessage(),
                    "SQL Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();                       // keep log in console
        }
    }
}

    /* ---- CANCEL (reset search) ------------------------------ */
    class CancelActionListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            view.setSearchTextFieldValue("");
            loadUserData();
        }
    }

    /* ---- SEARCH --------------------------------------------- */
    class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchTextFieldValue().trim().toLowerCase();
            if (kw.isEmpty()) { loadUserData(); return; }

            List<UserData> users = dao.showUsers();
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);

            for (UserData u : users) {
                if ( String.valueOf(u.getId()).contains(kw) ||
                     u.getName ().toLowerCase().contains(kw) ||
                     u.getEmail().toLowerCase().contains(kw) ||
                     u.getType ().toLowerCase().contains(kw)) {

                    model.addRow(new Object[]{
                            u.getId(), u.getName(), u.getType(), u.getEmail(),
                            u.getPassword(), u.getPhone(), u.getImage()
                    });
                }
            }
        }
    }
}
