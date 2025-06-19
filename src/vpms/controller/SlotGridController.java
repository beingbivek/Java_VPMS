package vpms.controller;

import vpms.dao.SlotInstanceDao;
import vpms.model.SlotInstanceData;
import vpms.utils.SlotButton;
import vpms.view.StaffDashboardContentView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class SlotGridController {

    private final StaffDashboardContentView view;
    private final SlotInstanceDao siDao = new SlotInstanceDao();

    public SlotGridController(StaffDashboardContentView v) {
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

    private void buildTabs() throws SQLException {
        // Clear existing tabs except legend
        JTabbedPane tabs = view.getTabLevels();
        while (tabs.getTabCount() > 1) tabs.remove(1);

        Set<Integer> levels = siDao.findLevels();
        for (Integer lvl : levels) {
            List<SlotInstanceData> bays = siDao.findByLevel(lvl);
            JScrollPane scroll = new JScrollPane(buildGrid(bays));
            tabs.addTab("Level " + lvl, scroll);
        }
    }

    private JPanel buildGrid(List<SlotInstanceData> bays) {
        final int COLS = 10;
        int rows = (int) Math.ceil(bays.size() / (double) COLS);

        JPanel grid = new JPanel(new GridLayout(rows, COLS, 6, 6));
        for (SlotInstanceData bay : bays) {
            SlotButton btn = new SlotButton(bay);
            btn.addActionListener(e -> handleClick(btn));
            grid.add(btn);
        }
        return grid;
    }

    private void handleClick(SlotButton btn) {
        SlotInstanceData bay = btn.getBay();
        switch (bay.getStatus()) {
            case "free" -> changeStatus(bay, btn, "occupied", "Slot marked as occupied.");
            case "reserved" -> JOptionPane.showMessageDialog(view, "Slot is reserved.");
            case "occupied" -> JOptionPane.showMessageDialog(view, "Slot already occupied.");
            case "disabled" -> JOptionPane.showMessageDialog(view, "Slot is disabled.");
        }
    }

    private void changeStatus(SlotInstanceData bay, SlotButton btn, String newStatus, String message) {
        try {
            boolean ok = siDao.updateStatus(bay.getInstanceId(), newStatus);
            if (ok) {
                btn.setStatus(newStatus);
                JOptionPane.showMessageDialog(view, message);
            } else {
                JOptionPane.showMessageDialog(view,
                        "Status change failed (DB returned 0 rows).",
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
