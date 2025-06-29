package vpms.controller;

import vpms.dao.*;
import vpms.model.*;
import vpms.view.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.List;
import vpms.utils.TableEnhancer;

public class VehicleManagementController {

    /* ---------------- fields ---------------- */
    private final VehicleManagementView view;
    private final VehicleDao  vehicleDao  = new VehicleDao();
    private final ParkingDao  parkingDao  = new ParkingDao();
    private final int         staffId;         // for activity-log if you add it later

    /* --------------- ctor ------------------- */
    public VehicleManagementController(VehicleManagementView view, int staffId) {
        this.view    = view;
        this.staffId = staffId;

        loadVehicleTable();                 // fill grid

        /* ───── hide history widgets at start ───── */
        view.getVehicleParkingHistoryScroll().setVisible(false);   // JScrollPane
        view.getSelectedVehicleLabel()        .setVisible(false);

        /* ---- label-buttons → MouseAdapter ---- */
        view.getAddButton()      .addMouseListener(new AddListener());
        view.getEditButton()     .addMouseListener(new EditListener());
        view.getDeleteUserButton().addMouseListener(new DeleteListener());
        view.getCancelButton()   .addMouseListener(new CancelListener());

        /* ---- search (either button or ENTER in text-field) ---- */
        view.getSearchField().addActionListener(new SearchListener()); // ENTER key

        /* ---- table row-selection → history ---- */
        view.getVehicleTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        view.getVehicleTable().getSelectionModel()
             .addListSelectionListener(e -> {
                 if (!e.getValueIsAdjusting()) showSelectedVehicleParkingHistory();
             });
    }

    public void open() { view.setVisible(true); }

    /* ================ table population ================ */


    /** fills the grid and beautifies it */
    public void loadVehicleTable() {

        /* ---------- data ---------- */
        List<VehicleData> list = vehicleDao.findByNumberLike("");   // all
        DefaultTableModel model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        model.setColumnIdentifiers(new String[]{
            "ID","Owner Name","Vehicle Type","Vehicle Number",
            "Contact","Created At","Updated At"
        });

        for (VehicleData v : list) {
            model.addRow(new Object[]{
                v.getId(), v.getOwnerName(), v.getType(),
                v.getVehicleNumber(), v.getOwnerContact(),
                v.getCreatedAt(), v.getUpdatedAt()
            });
        }

        /* ---------- table ---------- */
        JTable table = view.getVehicleTable();
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableEnhancer.beautifyTable(
        view.getVehicleTable(),
        new int[]{50,150,100,150,100,150,150});
    }

    
    public void refreshTable() {loadVehicleTable();}

    /* ================ ADD =================== */
    private class AddListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            AddVehiclesView av = new AddVehiclesView();
            new AddVehiclesController(av, staffId, VehicleManagementController.this).open();
        }
    }

    /* ================ EDIT ================== */
    private class EditListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getVehicleTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a vehicle."); return; }

            int id = (int) view.getVehicleTable().getValueAt(row,0);
            EditVehiclesView ev = new EditVehiclesView();
            new EditVehicleController(ev, id, staffId, VehicleManagementController.this).open();
        }
    }

    /* ================ DELETE ================ */
    private class DeleteListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getVehicleTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a vehicle."); return; }

            int vid = (int) view.getVehicleTable().getValueAt(row,0);
            if (JOptionPane.showConfirmDialog(view,
                    "Delete vehicle ID "+vid+"?","Confirm",
                    JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;

            if (vehicleDao.deleteVehicleById(vid)) {
                JOptionPane.showMessageDialog(view,"Vehicle deleted.");
                loadVehicleTable();
                hideHistory();
            } else {
                JOptionPane.showMessageDialog(view,"Delete failed.");
            }
        }
    }

    /* ================ CANCEL ================ */
    private class CancelListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            view.getSearchField().setText("");
            loadVehicleTable();
            hideHistory();
        }
    }

    /* ================ SEARCH ================ */
    private class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchField().getText().trim();
            List<VehicleData> list = vehicleDao.findByNumberLike(kw);

            DefaultTableModel m = (DefaultTableModel) view.getVehicleTable().getModel();
            m.setRowCount(0);
            for (VehicleData v : list) {
                m.addRow(new Object[]{
                    v.getId(), v.getOwnerName(), v.getType(),
                    v.getVehicleNumber(), v.getOwnerContact(),
                    v.getCreatedAt(), v.getUpdatedAt()
                });
            }
            hideHistory();                 // clear old history after new search
        }
    }

    /* ========== parking history of selected vehicle ========== */
    private void showSelectedVehicleParkingHistory() {

        int row = view.getVehicleTable().getSelectedRow();
        if (row == -1) { hideHistory(); return; }

        int    vehicleId     = (int)    view.getVehicleTable().getValueAt(row,0);
        String vehicleNumber = (String) view.getVehicleTable().getValueAt(row,3);

        view.getSelectedVehicleLabel().setText(vehicleNumber + " Parking History");
        view.getSelectedVehicleLabel().setVisible(true);
        view.getVehicleParkingHistoryScroll().setVisible(true);   // JScrollPane

        List<ParkingDetails> history = parkingDao.getParkingHistoryByVehicleId(vehicleId);

        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Entry Time","Exit Time","Status",
                         "Entry Note","Exit Note","Slot Instance ID"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };

        for (ParkingDetails p : history) {
            m.addRow(new Object[]{
                p.getEntryDateTime(), p.getExitDateTime(),
                p.getParkingStatus(), p.getEntryNote(),
                p.getExitNote(), p.getSlotInstanceId()
            });
        }
        view.getVehicleParkingHistoryTable().setModel(m);
        TableEnhancer.beautifyTable(
        view.getVehicleParkingHistoryTable(),
        new int[]{150,150,80,160,160,90});   
    }

    /* helper to hide + clear history */
    private void hideHistory() {
        view.getVehicleParkingHistoryScroll().setVisible(false);
        clearParkingHistoryTable();
    }
    private void clearParkingHistoryTable() {
        DefaultTableModel m = new DefaultTableModel(
            new String[]{"Entry Time","Exit Time","Status",
                         "Entry Note","Exit Note","Slot Instance ID"},0){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        view.getVehicleParkingHistoryTable().setModel(m);
        TableEnhancer.beautifyTable(
        view.getVehicleParkingHistoryTable(),
        new int[]{150,150,80,160,160,90});
    }
}
