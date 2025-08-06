package vpms.controller;

import vpms.dao.*;
import vpms.model.*;
import vpms.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import vpms.utils.TableEnhancer;

public class UserManagementController {

    /* -------------- fields -------------- */
    private final UserManagementView view;
    private final UserDao dao = new UserDao();
    private final int adminId;                    // for activity-log

    /* -------------- ctor -------------- */
    public UserManagementController(UserManagementView view, int adminId) {
        this.view     = view;
        this.adminId  = adminId;

        loadUserData();

        /* hook label-buttons */
        view.addAddButtonListener()   .addMouseListener(new AddUserListener());
        view.addEditButtonListener()  .addMouseListener(new EditUserListener());
        view.addDeleteButtonListener().addMouseListener(new DeleteUserListener());
        view.addCancelButtonListener().addMouseListener(new CancelListener());

        /* hook real JButton (search) */
        view.addSearchButtonListener().addActionListener(new SearchListener());
    }

    public void open()  { view.setVisible(true); }
    public void close() { view.dispose();        }

    /* ============ table population ============ */
    private void loadUserData() {
        List<UserData> users = dao.showUsers();

        DefaultTableModel m = new DefaultTableModel(){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "ID","Name","Type","Email","Phone","Status"
        });
        for (UserData u : users) {
            m.addRow(new Object[]{
                u.getId(),u.getName(),u.getType(),
                u.getEmail(),u.getPhone(),u.getStatus()
            });
        }
        JTable t = view.getTable();
        t.setModel(m);

        TableEnhancer.beautifyTable(t,new int[]{50,120,70,180,100,70});
    }

    
    public void refreshTable() {loadUserData();}

    /* ============ ADD ============ */
    private class AddUserListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            RegisterUserView popup = new RegisterUserView();
            new RegisterUserController(popup,UserManagementController.this,adminId).open();
        }
    }

    /* ============ EDIT ============ */
    private class EditUserListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a user."); return; }
            EditUserView popup = new EditUserView();
            new EditUserController(popup,(int) view.getTable().getValueAt(row,0),UserManagementController.this,adminId).open();
        }
    }

    /* ============ DELETE ============ */
    private class DeleteUserListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a user."); return; }

            int delId = (int) view.getTable().getValueAt(row,0);
            if (JOptionPane.showConfirmDialog(view,"Delete user "+delId+"?",
                    "Confirm",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;

            try {
                if (dao.deleteUser(delId)) {
                    JOptionPane.showMessageDialog(view,"User deleted.");
                    new ActivityLogDao().logActivity(
                        new ActivityLog(adminId,"User deleted, id:"+delId,
                                        LocalDateTime.now().format(
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(view,"Delete failed – not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,"DB error:\n"+ex.getMessage(),
                                              "Error",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /* ============ CANCEL (reset) ============ */
    private class CancelListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            view.setSearchTextFieldValue("");
            loadUserData();
        }
    }

    /* ============ SEARCH ============ */
    private class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchTextFieldValue().trim().toLowerCase();
            if (kw.isEmpty()) { loadUserData(); return; }

            List<UserData> users = dao.showUsers();
            DefaultTableModel m = (DefaultTableModel) view.getTable().getModel();
            m.setRowCount(0);

            for (UserData u : users) {
                if ( String.valueOf(u.getId()).contains(kw) ||
                     u.getName().toLowerCase().contains(kw)  ||
                     u.getEmail().toLowerCase().contains(kw) ||
                     u.getType().toLowerCase().contains(kw)) {

                    m.addRow(new Object[]{
                        u.getId(), u.getName(), u.getType(), u.getEmail(),
                        u.getPassword(), u.getPhone(), u.getImage(), u.getStatus()
                    });
                }
            }
        }
    }
}
