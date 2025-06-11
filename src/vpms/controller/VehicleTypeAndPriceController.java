/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.VehicleTypeAndPriceData;
import vpms.view.VehicleTypeAndPriceView;





/**
 *
 * @author PRABHASH
 */
public class VehicleTypeAndPriceController {
    
     private VehicleTypeAndPriceView view;
    private VehicleTypeAndPriceDao dao;
    private DefaultTableModel tableModel;

    public VehicleTypeAndPriceController(VehicleTypeAndPriceView view) {
        this.view = view;
        this.dao = new VehicleTypeAndPriceDao();

        tableModel = (DefaultTableModel) view.getTable().getModel();
        loadTableData();

        view.getAddButton().addActionListener(e -> addVehicleType());
        view.getUpdateButton().addActionListener(e -> updateVehicleType());
        view.getDeleteButton().addActionListener(e -> deleteVehicleType());

        view.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = view.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    view.getVehicleTypeField().setText(tableModel.getValueAt(selectedRow, 1).toString());
                    view.getReservationPriceField().setText(tableModel.getValueAt(selectedRow, 2).toString());
                    view.getRegularPriceField().setText(tableModel.getValueAt(selectedRow, 3).toString());
                    view.getDemandPriceField().setText(tableModel.getValueAt(selectedRow, 4).toString());
                    view.getExtraChargeField().setText(tableModel.getValueAt(selectedRow, 5).toString());
                    view.getStatusComboBox().setSelectedItem(tableModel.getValueAt(selectedRow, 6).toString());
                }
            }
        });
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // clear
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        for (VehicleTypeAndPriceData v : list) {
            Object[] row = {
                v.getId(),
                v.getVehicleType(),
                v.getReservationPrice(),
                v.getRegularPrice(),
                v.getDemandPrice(),
                v.getExtraCharge(),
                v.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void addVehicleType() {
        VehicleTypeAndPriceData vehicle = getFormData();
        if (dao.addVehicleTypeAndPrice(vehicle)) {
            JOptionPane.showMessageDialog(view, "Vehicle type added successfully!");
            loadTableData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to add data.");
        }
    }

    private void updateVehicleType() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            VehicleTypeAndPriceData vehicle = getFormData();
            vehicle.setId(id);

            if (dao.updateVehicleTypeAndPrice(vehicle)) {
                JOptionPane.showMessageDialog(view, "Vehicle type updated successfully!");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update data.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select a row first.");
        }
    }

    private void deleteVehicleType() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteVehicleTypeAndPrice(id)) {
                    JOptionPane.showMessageDialog(view, "Deleted successfully.");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(view, "Delete failed.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select a row first.");
        }
    }

    private VehicleTypeAndPriceData getFormData() {
        return new VehicleTypeAndPriceData(
            view.getVehicleTypeField().getText(),
            view.getReservationPriceField().getText(),
            view.getRegularPriceField().getText(),
            view.getDemandPriceField().getText(),
            view.getExtraChargeField().getText(),
            view.getStatusComboBox().getSelectedItem().toString()
        );
    }

    private void clearForm() {
        view.getVehicleTypeField().setText("");
        view.getReservationPriceField().setText("");
        view.getRegularPriceField().setText("");
        view.getDemandPriceField().setText("");
        view.getExtraChargeField().setText("");
        view.getStatusComboBox().setSelectedIndex(0);
    }
}
    

