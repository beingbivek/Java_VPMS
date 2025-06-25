package vpms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vpms.dao.ReportDao;
import vpms.model.ReportModel;
import vpms.view.ReportView;

public class ReportController {

    private final ReportView view;
    private final ReportDao reportDao;

    public ReportController(ReportView view) {
        this.view = view;
        this.reportDao = new ReportDao();
        setTodayInTextFields();
        loadTodayReport();
        this.view.getGenerateReport().addActionListener(e -> generateReportFromInput());
    }

    private void setTodayInTextFields() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        view.getFrom().setText(today);
        view.getTo().setText(today);
    }

    private void loadTodayReport() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime from = LocalDate.parse(today, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime to = LocalDate.parse(today, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
        loadReport(from, to);
    }

    public void generateReportFromInput() {
        String fromStr = view.getFrom().getText().trim();
        String toStr = view.getTo().getText().trim();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate fromDate = LocalDate.parse(fromStr, dateFormatter);
            LocalDate toDate = LocalDate.parse(toStr, dateFormatter);

            if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(view, "'From' date cannot be after 'To' date.");
                return;
            }

            LocalDateTime from = fromDate.atStartOfDay();
            LocalDateTime to = toDate.atTime(23, 59, 59);
            loadReport(from, to);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view, "Invalid date format. Please use yyyy-MM-dd");
        }
    }

    public void loadReport(LocalDateTime from, LocalDateTime to) {
        List<ReportModel> reports = reportDao.getReportByDate(from, to);
        DefaultTableModel model = (DefaultTableModel) view.getReportTable().getModel();
        model.setRowCount(0);

        for (ReportModel r : reports) {
            model.addRow(new Object[]{
                    r.getPaymentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    r.getVehicleNumber(),
                    r.getEntryTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            + " - " +
                            r.getExitTime(),
                    r.getTotalFee()
            });
        }

        double total = reportDao.getTotalRevenueByDate(from, to);
        view.getTotal().setText("Rs. " + total);
    }
}
