package vpms.controller;

import vpms.dao.*;
import vpms.model.*;
import vpms.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import vpms.utils.TableEnhancer;

public class VehicleTypeAndPriceController {

    /* ---------------- fields ---------------- */
    private final VehicleTypeAndPriceManagementView view;
    private final VehicleTypeAndPriceDao dao = new VehicleTypeAndPriceDao();
    private final int adminId;                       // for logging

    /* --------------- ctor ------------------- */
    public VehicleTypeAndPriceController(VehicleTypeAndPriceManagementView view, int adminId) throws SQLException {
        this.view    = view;
        this.adminId = adminId;

        loadVehicleTypeData();

        /* label-buttons → MouseAdapter */
        view.addAddButtonListener()   .addMouseListener(new AddListener());
        view.addEditButtonListener()  .addMouseListener(new EditListener());
        view.addDeleteButtonListener().addMouseListener(new DeleteListener());
        view.addCancelButtonListener().addMouseListener(new CancelListener());

        /* search button + ENTER key in text-field */
        view.getSearchTextField().addActionListener(new SearchListener());
    }

    public void open() { view.setVisible(true); }

    /* =============== table fill ============== */
    public void loadVehicleTypeData() {
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();

        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "ID","Vehicle Type","Regular","Demand","Reservation","Extra","Status"
        });
        for (VehicleTypeAndPriceData d : list) {
            m.addRow(new Object[]{
                d.getId(), d.getVehicleType(), d.getRegularPrice(),
                d.getDemandPrice(), d.getReservationPrice(),
                d.getExtraCharge(), d.getStatus()
            });
        }
        JTable t = view.getTable();
        t.setModel(m);

        /* ---- beautify ---- */
        new TableEnhancer().beautifyTable(t, new int[]{50,140,80,80,90,70,80});
    }


    /* =============== ADD ===================== */
    private class AddListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            AddVehicleTypeAndPriceView add = new AddVehicleTypeAndPriceView();
            new AddVehicleTypeAndPriceController(add, VehicleTypeAndPriceController.this, adminId).open();
        }
    }

    /* =============== EDIT ==================== */
    private class EditListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a record."); return; }

            VehicleTypeAndPriceData d = new VehicleTypeAndPriceData(
                (int)    view.getTable().getValueAt(row,0),
                (String) view.getTable().getValueAt(row,1),
                (String) view.getTable().getValueAt(row,4),   // reservation
                (String) view.getTable().getValueAt(row,2),   // regular
                (String) view.getTable().getValueAt(row,3),   // demand
                (String) view.getTable().getValueAt(row,5),   // extra
                (String) view.getTable().getValueAt(row,6)    // status
            );

            EditVehicleTypeAndPriceView edit = new EditVehicleTypeAndPriceView();
            try {
                new EditVehicleTypeAndPriceController(edit, d, VehicleTypeAndPriceController.this, adminId).open();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /* =============== DELETE ================== */
    private class DeleteListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a record."); return; }

            int delId = (int) view.getTable().getValueAt(row,0);
            if (JOptionPane.showConfirmDialog(view,"Delete id "+delId+"?","Confirm",
                    JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;

            if (dao.deleteVehicleTypeAndPrice(delId)) {
                JOptionPane.showMessageDialog(view,"Deleted.");
                new ActivityLogDao().logActivity(new ActivityLog(
                        adminId,"VehicleType deleted, id:"+delId));
                loadVehicleTypeData();
            } else {
                JOptionPane.showMessageDialog(view,"Delete failed.");
            }
        }
    }

    /* =============== CANCEL (reset) ========== */
    private class CancelListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            view.setSearchTextFieldValue("");
            loadVehicleTypeData();
        }
    }

    /* =============== SEARCH ================== */
    private class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchTextFieldValue().trim().toLowerCase();
            if (kw.isEmpty() || kw.equals("search")) { loadVehicleTypeData(); return; }

            List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
            DefaultTableModel m = (DefaultTableModel) view.getTable().getModel();
            m.setRowCount(0);

            for (VehicleTypeAndPriceData d : list) {
                if ( String.valueOf(d.getId()).contains(kw) ||
                     d.getVehicleType().toLowerCase().contains(kw) ||
                     d.getStatus()     .toLowerCase().contains(kw)) {

                    m.addRow(new Object[]{
                        d.getId(), d.getVehicleType(), d.getRegularPrice(),
                        d.getDemandPrice(), d.getReservationPrice(),
                        d.getExtraCharge(), d.getStatus()
                    });
                }
            }
        }
    }
}
