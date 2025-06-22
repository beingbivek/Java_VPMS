/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import vpms.dao.ReportDao;
import vpms.model.ReportModel;
import vpms.view.ReportView;

/**
 *
 * @author Chandani
 */
public class ReportController {
 
    private ReportView view;
    private ReportDao reportDao;

    public ReportController(ReportView view) {
        this.view = view;
        this.reportDao = new ReportDao();
    }

    public void loadReport(LocalDateTime from, LocalDateTime to) {
        List<ReportModel> reports = reportDao.getReportByDate(from, to);
        DefaultTableModel model = (DefaultTableModel) view.getReportTable().getModel();
        model.setRowCount(0);

        for (ReportModel r : reports) {
            model.addRow(new Object[]{
                    r.getPaymentTime(),
                    r.getVehicleNumber(),
                    r.getEntryTime() + " - " + r.getExitTime(),
                    r.getTotalFee()
            });
        }

        double total = reportDao.getTotalRevenueByDate(from, to);
        view.getTotal().setText("Rs. " + total);
    }
}
    

