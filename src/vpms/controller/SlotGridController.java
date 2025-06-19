/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import vpms.dao.SlotInstanceDao;
import vpms.model.SlotInstanceData;
import vpms.utils.SlotButton;
import vpms.view.StaffDashboardContentView;

/**
 * Builds the “cinema-hall” style parking map inside the
 * JTabbedPane of StaffDashboardContentView.
 *
 * Each SlotButton represents one physical bay (row from slot_instances).
 * Colour = status (free / reserved / occupied / disabled).
 *
 * @author being
 */
public class SlotGridController {

    private final StaffDashboardContentView view;
    // full CRUD
    private final SlotInstanceDao           siDao;   

    /* ----------------------------------------------------------- */
    public SlotGridController(StaffDashboardContentView v) throws SQLException {
        this.siDao = new SlotInstanceDao();
        this.view = v;
        try {
            buildTabs();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Cannot load slot data:\n" + ex.getMessage(),
                    "DB Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /* ----------------------------------------------------------- */
    private void buildTabs() throws SQLException {

        /* one tab per level */
        Set<Integer> levels = siDao.findLevels();               // DISTINCT level_number
        for (Integer lvl : levels) {
            List<SlotInstanceData> bays = siDao.findByLevel(lvl);
            JScrollPane scroll = new JScrollPane(buildGrid(bays));
            view.getTabLevels().add("Level " + lvl, scroll);
        }
    }

    /* ----------------------------------------------------------- */
    private JPanel buildGrid(List<SlotInstanceData> bays) {

        final int COLS = 10;                                    // 10 buttons per row
        int rows = (int) Math.ceil(bays.size() / (double) COLS);

        JPanel grid = new JPanel(new GridLayout(rows, COLS, 6, 6));
        for (SlotInstanceData bay : bays) {
            SlotButton btn = new SlotButton(bay);
            btn.addActionListener(e -> handleClick(btn));
            grid.add(btn);
        }
        return grid;
    }

    /* ----------------------------------------------------------- */
    private void handleClick(SlotButton btn) {

        SlotInstanceData bay = btn.getBay();

        switch (bay.getStatus()) {
            case "free" -> {
                changeStatus(bay, btn, "occupied", "Slot marked as occupied.");
            }
            case "reserved" -> JOptionPane.showMessageDialog(
                    view, "Slot is reserved.");
            case "occupied" -> JOptionPane.showMessageDialog(
                    view, "Slot already occupied.");
            case "disabled" -> JOptionPane.showMessageDialog(
                    view, "Slot is disabled.");
        }
    }

    /* ----------------------------------------------------------- */
    private void changeStatus(SlotInstanceData bay, SlotButton btn,
                              String newStatus, String message) {

        try {
            boolean ok = siDao.updateStatus(bay.getInstanceId(), newStatus);
            if (ok) {
                btn.setStatus(newStatus);   // immediately recolour
                JOptionPane.showMessageDialog(view, message);
            } else {
                JOptionPane.showMessageDialog(
                        view, "Status change failed (DB returned 0 rows).",
                        "Update error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Database error:\n" + ex.getMessage(),
                    "SQL Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
