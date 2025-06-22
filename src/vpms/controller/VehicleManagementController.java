package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.model.VehicleData;
import vpms.view.VehicleManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import vpms.view.AddVehiclesView;
import vpms.view.EditVehiclesView;

public class VehicleManagementController {
    private final VehicleManagementView view;
    private final VehicleDao dao = new VehicleDao();
    int id;

    public VehicleManagementController(VehicleManagementView view,int id) {
        this.view = view;
        this.id = id;
        setupTable();
        loadVehicleTable();

        // Listeners
        view.getAddButton().addActionListener(e -> addVehicle());
        view.getEditButton().addActionListener(e -> editVehicle());
        view.getDeleteUserButton().addActionListener(e -> deleteVehicle());
        view.getCancelButton().addActionListener(e -> loadVehicleTable());
        view.getSearchField().addActionListener(e -> searchVehicle());
    }

    /** Make table non-editable but row-selectable */
    private void setupTable() {
        view.getVehicleTable().setDefaultEditor(Object.class, null);
        view.getVehicleTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /** Load all vehicles into the table */
    private void loadVehicleTable() {
        List<VehicleData> list = dao.findByNumberLike(""); // Loads all
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

    /** Add vehicle (open dialog or new view as needed) */
    private void addVehicle() {
        AddVehiclesView avView = new AddVehiclesView();
        new AddVehiclesController(avView,id).open();
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
        new EditVehicleController(editView, editid,id).open();
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
}
