package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.model.VehicleData;
import vpms.view.VehicleManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import vpms.dao.ParkingDao;
import vpms.model.ParkingDetails;
import vpms.view.AddVehiclesView;
import vpms.view.EditVehiclesView;

public class VehicleManagementController {
    private final VehicleManagementView view;
    private final VehicleDao dao = new VehicleDao();
    int id;

    public VehicleManagementController(VehicleManagementView view,int id) {
        this.view = view;
        this.id = id;
        loadVehicleTable();
        // Listeners
        view.getAddButton().addActionListener(e -> addVehicle());
        view.getEditButton().addActionListener(e -> editVehicle());
        view.getDeleteUserButton().addActionListener(e -> deleteVehicle());
        view.getCancelButton().addActionListener(e -> loadVehicleTable());
        view.getSearchField().addActionListener(e -> searchVehicle());
    }
    /** Load all vehicles into the table */
    public void loadVehicleTable() {
        List<VehicleData> list = dao.findByNumberLike(""); // Loads all
        
        // Create a non-editable table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Set column names
        model.setColumnIdentifiers(new String[] {
            "ID", "Owner Name", "Vehicle Type", "Vehicle Number", "Contact", "Created At", "Updated At"
        });

        if (list != null && !list.isEmpty()) {
            for (VehicleData v : list) {
                Object[] row = {
                    v.getId(),
                    v.getOwnerName(),
                    v.getType(),
                    v.getVehicleNumber(),
                    v.getOwnerContact(),
                    v.getCreatedAt(),
                    v.getUpdatedAt()
                };
                model.addRow(row);
            }
        }
        view.getVehicleTable().setModel(model);
        
        
        view.getVehicleTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        view.getVehicleTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedVehicleParkingHistory();
            }
        });
    }

    /** Add vehicle (open dialog or new view as needed) */
    private void addVehicle() {
        AddVehiclesView avView = new AddVehiclesView();
        new AddVehiclesController(avView,id,VehicleManagementController.this).open();
    }

    /** Edit selected vehicle */
    private void editVehicle() {
        int row = view.getVehicleTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Select a vehicle to edit.");
            return;
        }
        int editid = (int) view.getVehicleTable().getValueAt(row, 0);
        EditVehiclesView editView = new EditVehiclesView();
        new EditVehicleController(editView, editid,id,VehicleManagementController.this).open();
    }

    /** Delete selected vehicle */
    private void deleteVehicle() {
        int row = view.getVehicleTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Select a vehicle to delete.");
            return;
        }
        int id = (int) view.getVehicleTable().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Delete vehicle ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Implement delete in DAO
        boolean deleted = dao.deleteVehicleById(id);
        if (deleted) {
            JOptionPane.showMessageDialog(view, "Vehicle deleted.");
            loadVehicleTable();
        } else {
            JOptionPane.showMessageDialog(view, "Delete failed.");
        }
    }

    /** Search vehicles by number */
    private void searchVehicle() {
        String keyword = view.getSearchField().getText().trim();
        List<VehicleData> list = dao.findByNumberLike(keyword);
        DefaultTableModel model = (DefaultTableModel) view.getVehicleTable().getModel();
        model.setRowCount(0);

        if (list != null && !list.isEmpty()) {
            for (VehicleData v : list) {
                Object[] row = {
                    v.getId(),
                    v.getOwnerName(),
                    v.getType(),
                    v.getVehicleNumber(),
                    v.getOwnerContact(),
                    v.getCreatedAt(),
                    v.getUpdatedAt()
                };
                model.addRow(row);
            }
        }
    }
    
    private void showSelectedVehicleParkingHistory() {
        int row = view.getVehicleTable().getSelectedRow();
        if (row == -1) {
            view.getSelectedVehicleLabel().setText("Selected Vehicle Parking History");
            clearParkingHistoryTable();
            return;
        }

        int vehicleId = (int) view.getVehicleTable().getValueAt(row, 0);
        String vehicleNumber = (String) view.getVehicleTable().getValueAt(row, 3); // Vehicle Number column

        // Update label
        view.getSelectedVehicleLabel().setText(vehicleNumber + " parking history");

        // Fetch parking history
        List<ParkingDetails> history = new ParkingDao().getParkingHistoryByVehicleId(vehicleId);

        // Build non-editable model
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Entry Time", "Exit Time", "Status", "Entry Note", "Exit Note", "Slot Instance ID"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ParkingDetails p : history) {
            model.addRow(new Object[]{
                p.getEntryDateTime(),
                p.getExitDateTime(),
                p.getParkingStatus(),
                p.getEntryNote(),
                p.getExitNote(),
                p.getSlotInstanceId()
            });
        }
        view.getVehicleParkingHistoryTable().setModel(model);
        view.getVehicleParkingHistoryTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        view.getVehicleParkingHistoryTable().setFillsViewportHeight(true);
    }
    
    //Helper
    private void clearParkingHistoryTable() {
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Entry Time", "Exit Time", "Status", "Entry Note", "Exit Note", "Slot Instance ID"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getVehicleParkingHistoryTable().setModel(model);
    }


}
