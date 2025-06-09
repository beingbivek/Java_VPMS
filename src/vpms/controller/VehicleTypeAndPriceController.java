/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vpms.dao.PaymentDao;
import vpms.model.PaymentData;
import vpms.view.VehicleTypeAndPriceView;



/**
 *
 * @author PRABHASH
 */
public class VehicleTypeAndPriceController {
    
     private VehicleTypeAndPriceView view;
    private PaymentDao paymentDao;

    public VehicleTypeAndPriceController(VehicleTypeAndPriceView view) {
        this.view = view;
        this.paymentDao = new PaymentDao();
        loadTableData();
        attachListeners();
        
    }
    
    private void setupEditableTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"payment_id", "parking_id", "vehicle_id", "user_id", "regular_price", "demand_price", "reservation_price", "extra_charge", "payment_status", "payment_time"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 9;
            }
        };
        view.getTable().setModel(model);
    }
    private void loadTableData() {
         List<PaymentData> dataList = paymentDao.showPayments();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (PaymentData data : dataList) {
            model.addRow(new Object[]{
                    data.getPayment_id(),
                    data.getParking_id(),
                    data.getVehicle_id(),
                    data.getUser_id(),
                    data.getRegularPrice(),
                    data.getDemandPrice(),
                    data.getReservationPrice(),
                    data.getExtraCharge(),
                    data.getPaymentStatus(),
                    data.getPaymentTime().format(formatter)
            });
        }
    }

    private void attachListeners() {
        view.getAddButton().addActionListener(e -> {
    DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();

    // Add a blank editable row (you’ll fill it then press Save)
    model.addRow(new Object[]{
            0,     // payment_id = 0 (will be ignored by DB if auto_increment)
            "",    // parking_id
            "",    // vehicle_id
            "",    // user_id
            "",    // regular_price
            "",    // demand_price
            "",    // reservation_price
            "",    // extra_charge
            "",    // payment_status
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    });

    // Scroll to the new row and auto-select it
    int newRow = model.getRowCount() - 1;
    view.getTable().setRowSelectionInterval(newRow, newRow);
});

        // Optional: delete row
        view.getDeleteButton().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row != -1) {
                int paymentId = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure?");
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = paymentDao.deletePayment(paymentId);
                    showMessage(success, "deleted");
                    loadTableData();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Select a row to delete.");
            }
        });
    }

    private void showMessage(boolean success, String action) {
        if (success) {
            JOptionPane.showMessageDialog(view, "Payment " + action + " successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Failed to " + action + " payment.");
        }
    }
}
    

