/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.VehicleTypeAndPriceData;
import vpms.view.AddVehicleTypeAndPriceView;
import vpms.view.EditVehicleTypeAndPriceView;
import vpms.view.VehicleTypeAndPriceView;


/**
 *
 * @author PRABHASH
 */
public class VehicleTypeAndPriceController {
    private VehicleTypeAndPriceView view;
    private VehicleTypeAndPriceDao dao;

    public VehicleTypeAndPriceController(VehicleTypeAndPriceView view) {
        this.view = view;
        this.dao = new VehicleTypeAndPriceDao();

        loadVehicleTypeData();

        this.view.addAddButtonListener(new AddVehicleListener());
        this.view.addEditButtonListener(new EditVehicleListener());
        this.view.addDeleteButtonListener(new DeleteVehicleListener());
        this.view.addCancelButtonListener(new CancelActionListener());
        this.view.addSearchButtonListener(new SearchListener());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void loadVehicleTypeData() {
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        if (list != null) {
            for (VehicleTypeAndPriceData data : list) {
                Object[] row = {
                    data.getId(),
                    data.getVehicleType(),
                    data.getRegularPrice(),
                    data.getDemandPrice(),
                    data.getReservationPrice(),
                    data.getExtraCharge(),
                    data.getStatus()
                };
                model.addRow(row);
            }
        }
    }

    class AddVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // close this page
            AddVehicleTypeAndPriceView addView = new AddVehicleTypeAndPriceView();
            AddVehicleTypeAndPriceController controller = new AddVehicleTypeAndPriceController(addView, VehicleTypeAndPriceController.this);
            controller.open(); // go to add page
        }
    }

    class EditVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Please select a record to edit.");
                return;
            }

            int id = (int) view.getTable().getValueAt(row, 0);
            String vehicleType = (String) view.getTable().getValueAt(row, 1);
            String regularPrice = (String) view.getTable().getValueAt(row, 2);
            String demandPrice = (String) view.getTable().getValueAt(row, 3);
            String reservationPrice = (String) view.getTable().getValueAt(row, 4);
            String extraCharge = (String) view.getTable().getValueAt(row, 5);
            String status = (String) view.getTable().getValueAt(row, 6);

            VehicleTypeAndPriceData selectedData = new VehicleTypeAndPriceData(
                    id, vehicleType, reservationPrice, regularPrice, demandPrice, extraCharge, status
            );

            view.dispose(); // close this view
            EditVehicleTypeAndPriceView editView = new EditVehicleTypeAndPriceView();
            EditVehicleTypeAndPriceController controller = new EditVehicleTypeAndPriceController(editView, selectedData, VehicleTypeAndPriceController.this);
            controller.open(); // go to edit page
        }
    }

    class DeleteVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Please select a record to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            int id = (int) view.getTable().getValueAt(row, 0);
            boolean success = dao.deleteVehicleTypeAndPrice(id);

            if (success) {
                JOptionPane.showMessageDialog(view, "Record deleted successfully.");
                loadVehicleTypeData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete record.");
            }
        }
    }

    class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSearchTextFieldValue("");
            loadVehicleTypeData();
        }
    }
    class SearchListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = view.getSearchTextFieldValue().trim().toLowerCase();

        if (keyword.isEmpty() || keyword.equals("search")) {
            loadVehicleTypeData();
            return;
        }

        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        for (VehicleTypeAndPriceData data : list) {
            String idStr = String.valueOf(data.getId());

            if (idStr.contains(keyword) ||
                data.getVehicleType().toLowerCase().contains(keyword) ||
                data.getStatus().toLowerCase().contains(keyword)) {

                Object[] row = {
                    data.getId(),
                    data.getVehicleType(),
                    data.getRegularPrice(),
                    data.getDemandPrice(),
                    data.getReservationPrice(),
                    data.getExtraCharge(),
                    data.getStatus()
                };
                model.addRow(row);
            }
        }
    }
}
}
    

