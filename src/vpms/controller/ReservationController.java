/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import vpms.dao.ReservationDao;
import vpms.model.ReservationData;
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

        loadTable();
        addListeners();
    }

    public void open() {
        view.setVisible(true);
    }

    private void loadTable() {
        List<ReservationData> list = dao.getAllReservations();
        DefaultTableModel model = (DefaultTableModel) view.getReservationTable().getModel();
        model.setRowCount(0);

        if (list != null) {
            for (ReservationData data : list) {
                model.addRow(new Object[]{
                        data.getId(),
                        data.getUserId(),
                        data.getVehicleId(),
                        data.getSlotId(),
                        data.getReservationTime(),
                        data.getStatus(),
                        data.getDuration(),
                        data.getPaymentStatus()
                });
            }
        }
    }

    private void addListeners() {
        // Cancel Button Logic
        view.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getSearchField().setText("");
                loadTable();
            }
        });
}
    
}
