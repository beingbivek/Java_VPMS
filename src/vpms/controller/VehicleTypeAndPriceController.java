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
            PaymentData payment = showPaymentInputDialog(null);
            if (payment != null) {
                boolean success = paymentDao.addPayment(payment);
                showMessage(success, "added");
                loadTableData();
            }
        });

        view.getEditButton().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row != -1) {
                int paymentId = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());
                PaymentData oldData = extractDataFromRow(row);
                oldData.setPayment_id(paymentId);

                PaymentData updated = showPaymentInputDialog(oldData);
                if (updated != null) {
                    updated.setPayment_id(paymentId);
                    boolean success = paymentDao.updatePayment(updated);
                    showMessage(success, "updated");
                    loadTableData();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Select a row to edit.");
            }
        });

        view.getDeleteButton().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row != -1) {
                int paymentId = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this payment?");
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

    private PaymentData showPaymentInputDialog(PaymentData existing) {
        try {
            String parkingId = JOptionPane.showInputDialog(view, "Enter Parking ID:", existing != null ? existing.getParking_id() : "");
            String vehicleId = JOptionPane.showInputDialog(view, "Enter Vehicle ID:", existing != null ? existing.getVehicle_id() : "");
            String userId = JOptionPane.showInputDialog(view, "Enter User ID:", existing != null ? existing.getUser_id() : "");

            if (parkingId == null || vehicleId == null || userId == null ||
                parkingId.isBlank() || vehicleId.isBlank() || userId.isBlank()) {
                JOptionPane.showMessageDialog(view, "Parking ID, Vehicle ID, and User ID are required.");
                return null;
            }

            String regular = JOptionPane.showInputDialog(view, "Enter Regular Price:", existing != null ? existing.getRegularPrice() : "");
            String demand = JOptionPane.showInputDialog(view, "Enter Demand Price:", existing != null ? existing.getDemandPrice() : "");
            String reserve = JOptionPane.showInputDialog(view, "Enter Reservation Price:", existing != null ? existing.getReservationPrice() : "");
            String extra = JOptionPane.showInputDialog(view, "Enter Extra Charge:", existing != null ? existing.getExtraCharge() : "");
            String status = JOptionPane.showInputDialog(view, "Enter Payment Status:", existing != null ? existing.getPaymentStatus() : "");

            return new PaymentData(
                    0,
                    Integer.parseInt(parkingId),
                    Integer.parseInt(vehicleId),
                    Integer.parseInt(userId),
                    regular,
                    demand,
                    reserve,
                    extra,
                    status,
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Invalid input! Operation cancelled.");
            return null;
        }
    }
    private PaymentData extractDataFromRow(int row) {
        JTable table = view.getTable();
        return new PaymentData(
                0,
                Integer.parseInt(table.getValueAt(row, 1).toString()),
                Integer.parseInt(table.getValueAt(row, 2).toString()),
                Integer.parseInt(table.getValueAt(row, 3).toString()),
                table.getValueAt(row, 4).toString(),
                table.getValueAt(row, 5).toString(),
                table.getValueAt(row, 6).toString(),
                table.getValueAt(row, 7).toString(),
                table.getValueAt(row, 8).toString(),
                LocalDateTime.now()
        );
    }

    private void showMessage(boolean success, String action) {
        if (success) {
            JOptionPane.showMessageDialog(view, "Payment " + action + " successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Failed to " + action + " payment.");
        }
    }
}
    

