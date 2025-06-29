package vpms.controller;

import vpms.dao.ReportDao;
import vpms.model.ReportModel;
import vpms.utils.TableEnhancer;
import vpms.view.ReportView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReportController {

    private final ReportView view;
    private final ReportDao  reportDao;
    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportController(ReportView view) {
        this.view      = view;
        this.reportDao = new ReportDao();

        setTodayInTextFields();
        loadTodayReport();

        view.getGenerateReport().addActionListener(e -> generateReportFromInput());
    }

    /* ---------- helpers ---------- */

    private void setTodayInTextFields() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        view.getFrom().setText(today);
        view.getTo  ().setText(today);
    }

    private void loadTodayReport() {
        LocalDate today = LocalDate.now();
        loadReport(today.atStartOfDay(), today.atTime(23,59,59));
    }

    /* ---------- actions ---------- */

    private void generateReportFromInput() {
        String fromStr = view.getFrom().getText().trim();
        String toStr   = view.getTo  ().getText().trim();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate fromDate = LocalDate.parse(fromStr, df);
            LocalDate toDate   = LocalDate.parse(toStr,   df);

            if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(view,"'From' date cannot be after 'To' date.");
                return;
            }
            loadReport(fromDate.atStartOfDay(), toDate.atTime(23,59,59));

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view,"Invalid date format. Please use yyyy-MM-dd");
        }
    }

    /* ---------- main load ---------- */

    public void loadReport(LocalDateTime from, LocalDateTime to) {
        List<ReportModel> list = reportDao.getReportByDate(from, to);

        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "Payment Time","Vehicle No.","Entry - Exit","Total Fee"
        });

        for (ReportModel r : list) {

            m.addRow(new Object[]{
                r.getPaymentTime().format(DTF),
                r.getVehicleNumber(),
                r.getEntryTime().format(DTF) + "  -  " + r.getExitTime(),
                r.getTotalFee()
            });
        }

        JTable tbl = view.getReportTable();
        tbl.setModel(m);

        /* beautify header, zebra rows, widths */
        TableEnhancer.beautifyTable(tbl,new int[]{160,120,320,80});

        /* grand total */
        double total = reportDao.getTotalRevenueByDate(from, to);
        view.getTotal().setText("Rs. " + total);
    }
}
