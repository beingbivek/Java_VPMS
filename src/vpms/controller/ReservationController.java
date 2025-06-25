/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vpms.dao.ReservationDao;
import vpms.model.ReservationData;
import vpms.view.AddReservationView;
import vpms.view.EditReservationView;
import vpms.view.ReservationView;



/**
 *
 * @author PRABHASH
 */
public class ReservationController {
    private ReservationView view;
    private ReservationDao dao;

    public ReservationController(ReservationView view) {
        this.view = view;
        this.dao = new ReservationDao();

        loadReservationData();

        this.view.addAddButtonListener(new AddReservationListener());
        this.view.addEditButtonListener(new EditReservationListener());
        this.view.addDeleteButtonListener(new DeleteReservationListener());
        this.view.addCancelButtonListener(new CancelActionListener());
        this.view.addSearchFieldKeyListener(new SearchKeyListener());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void loadReservationData() {
        List<ReservationData> list = dao.getAllReservations();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        view.getTable().setDefaultEditor(Object.class, null);
        view.getTable().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        if (list != null) {
            for (ReservationData data : list) {
                Object[] row = {
                    data.getReservationId(),
                    data.getVehicleId(),
                    data.getUserId(),
                    data.getSlotId(),
                    data.getVehicleType(),
                    data.getContact(),
                    data.getEntryTime(),
                    data.getExitTime(),
                    data.getDuration(),
                    data.getStatus(),
                    data.getPaymentStatus()
                };
                model.addRow(row);
            }
        }
    }

    

    class DeleteReservationListener implements ActionListener {
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
            boolean success = dao.deleteReservation(id);

            if (success) {
                JOptionPane.showMessageDialog(view, "Record deleted successfully.");
                loadReservationData();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to delete record.");
            }
        }
    }

    class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSearchTextFieldValue("");
            loadReservationData();
        }
    }

    class SearchKeyListener implements KeyListener {
        @Override
        public void keyReleased(KeyEvent e) {
            String keyword = view.getSearchField().getText().trim().toLowerCase();

            if (keyword.isEmpty() || keyword.equals("search")) {
                loadReservationData();
                return;
            }

            List<ReservationData> list = dao.getAllReservations();
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);

            for (ReservationData data : list) {
                String idStr = String.valueOf(data.getReservationId());

                if (idStr.contains(keyword)
                        || data.getVehicleType().toLowerCase().contains(keyword)
                        || data.getContact().toLowerCase().contains(keyword)
                        || data.getStatus().toLowerCase().contains(keyword)
                        || data.getPaymentStatus().toLowerCase().contains(keyword)) {

                    Object[] row = {
                        data.getReservationId(),
                        data.getVehicleId(),
                        data.getUserId(),
                        data.getSlotId(),
                        data.getVehicleType(),
                        data.getContact(),
                        data.getEntryTime(),
                        data.getExitTime(),
                        data.getDuration(),
                        data.getStatus(),
                        data.getPaymentStatus()
                    };
                    model.addRow(row);
                }
            }
        }

        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {}
    }
    class AddReservationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        AddReservationView addView = new AddReservationView();
        AddReservationController controller = new AddReservationController(addView, ReservationController.this);
        controller.open();
    }
}
    class EditReservationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a record to edit.");
            return;
        }

        int reservationId  = (int) view.getTable().getValueAt(row, 0);
        int vehicleId      = (int) view.getTable().getValueAt(row, 1);
        int slotId         = (int) view.getTable().getValueAt(row, 3);
        String vehicleType = (String) view.getTable().getValueAt(row, 4);
        String contact     = (String) view.getTable().getValueAt(row, 5);
        String entryTime   = (String) view.getTable().getValueAt(row, 6);
        String exitTime    = (String) view.getTable().getValueAt(row, 7);
        String duration    = (String) view.getTable().getValueAt(row, 8);
        String status      = (String) view.getTable().getValueAt(row, 9);
        String paymentStatus = (String) view.getTable().getValueAt(row, 10);

        ReservationData selectedData = new ReservationData(
                reservationId, vehicleId, 0, slotId,
                vehicleType, contact, entryTime, exitTime,
                duration, status, paymentStatus
        );

        EditReservationView editView = new EditReservationView();
        EditReservationController controller = new EditReservationController(editView, selectedData, ReservationController.this);
        controller.open();
    }
}
}