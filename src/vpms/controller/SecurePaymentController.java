package vpms.controller;

import vpms.dao.PaymentDao;
import vpms.model.PaymentData;
import vpms.utils.TableEnhancer;
import vpms.view.SecurePaymentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SecurePaymentController {

    private final SecurePaymentView view;
    private final PaymentDao paymentDao = new PaymentDao();
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SecurePaymentController(SecurePaymentView view) {
        this.view = view;

        loadPaymentsTable();                         // first fill
        view.getSearchTextField().addActionListener(e -> filterPayments());
        view.getCancelButton().addActionListener(e -> {
            view.getSearchTextField().setText("");
            loadPaymentsTable();
        });
    }

    public void open() { view.setVisible(true); }

    /* ========== fill + beautify main table ========== */
    private void loadPaymentsTable() {
        List<PaymentData> list = paymentDao.showPayments();
        buildModel(list);
    }

    private void filterPayments() {
        String kw = view.getSearchTextField().getText().trim();
        buildModel(paymentDao.searchPayments(kw));
    }

    /* ---------- helper that builds model + styles table ---------- */
    private void buildModel(List<PaymentData> rows) {

        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "Payment ID","Parking ID","Vehicle ID","User ID",
            "Regular","Demand","Reservation","Extra",
            "Status","Time"
        });

        for (PaymentData p : rows) {
            m.addRow(new Object[]{
                p.getPayment_id(),
                p.getParking_id(),
                p.getVehicle_id(),
                p.getUser_id(),
                p.getRegularPrice(),
                p.getDemandPrice(),
                p.getReservationPrice(),
                p.getExtraCharge(),
                p.getPaymentStatus(),
                p.getPaymentTime()!=null ? p.getPaymentTime().format(DTF) : ""
            });
        }

        JTable tbl = view.getPaymentTable();
        tbl.setModel(m);

        /* preferred widths (pixels) */
        TableEnhancer.beautifyTable(tbl,
                new int[]{70,70,70,60,80,80,90,70,90,160});
    }
}
