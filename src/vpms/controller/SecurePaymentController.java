package vpms.controller;

import vpms.dao.PaymentDao;
import vpms.model.PaymentData;
import vpms.view.SecurePaymentView;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SecurePaymentController {
    private final SecurePaymentView view;
    private final PaymentDao paymentDao = new PaymentDao();

    public SecurePaymentController(SecurePaymentView view) {
        this.view = view;
        loadPaymentsTable();
        view.getSearchTextField().addActionListener(e -> filterPayments());
        // Cancel button closes the window
        view.getCancelTextField().addActionListener(e -> view.dispose());
    }

    private void loadPaymentsTable() {
        List<PaymentData> paymentList = paymentDao.showPayments();
        
        view.getPaymentTable().setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Payment ID", "Parking ID", "Vehicle ID", "User ID", "Regular Price", "Demand Price",
                "Reservation Price", "Extra Charge", "Payment Status", "Payment Time"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) view.getPaymentTable().getModel();
        model.setRowCount(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (PaymentData payment : paymentList) {
            Object[] row = {
                payment.getPayment_id(),
                payment.getParking_id(),
                payment.getVehicle_id(),
                payment.getUser_id(),
                payment.getRegularPrice(),
                payment.getDemandPrice(),
                payment.getReservationPrice(),
                payment.getExtraCharge(),
                payment.getPaymentStatus(),
                payment.getPaymentTime() != null ? payment.getPaymentTime().format(dtf) : ""
            };
            model.addRow(row);
        }
    }
    
    private void filterPayments() {
        String keyword = view.getSearchTextField().getText().trim();
        List<PaymentData> paymentList = paymentDao.searchPayments(keyword);
        DefaultTableModel model = (DefaultTableModel) view.getPaymentTable().getModel();
        model.setRowCount(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (PaymentData payment : paymentList) {
            Object[] row = {
                payment.getPayment_id(),
                payment.getParking_id(),
                payment.getVehicle_id(),
                payment.getUser_id(),
                payment.getRegularPrice(),
                payment.getDemandPrice(),
                payment.getReservationPrice(),
                payment.getExtraCharge(),
                payment.getPaymentStatus(),
                payment.getPaymentTime() != null ? payment.getPaymentTime().format(dtf) : ""
            };
            model.addRow(row);
        }
    }
}
