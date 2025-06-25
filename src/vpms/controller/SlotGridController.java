package vpms.controller;

import vpms.dao.SlotInstanceDao;
import vpms.model.SlotInstanceData;
import vpms.utils.SlotButton;
import vpms.view.StaffDashboardContentView;
import vpms.view.VehicleNumberCheckView;
import vpms.view.ParkingExitView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import vpms.dao.ParkingDao;
import vpms.model.ParkedDetails;

public class SlotGridController {

    private final StaffDashboardContentView view;
    private final SlotInstanceDao siDao = new SlotInstanceDao();
    ParkingDao parkingDao = new ParkingDao();
    private final VehicleManagementController vmController;
    private StaffDashboardContentController s;
    int id;

    public SlotGridController(StaffDashboardContentView v,int id,VehicleManagementController vmController,StaffDashboardContentController s) {
        this.view = v;
        this.id = id;
        this.vmController = vmController;
        this.s = s;
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
            case "free" -> {
                //-> changeStatus(bay, btn, "occupied", "Slot marked as occupied.");
                String[] options = {"Park", "Cancel"};
                int choice = JOptionPane.showOptionDialog(
                        view,
                        "What would you like to do with this slot?",
                        "Slot Action",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (choice == 0) {
                    // Park
                    VehicleNumberCheckView numberCheck = new VehicleNumberCheckView();
                    new VehicleNumberCheckController(numberCheck,bay,id,vmController,s).open();
                }
                // else: Cancel, do nothing
            }

//            case "reserved" -> JOptionPane.showMessageDialog(view, "Slot is reserved.");
            case "occupied" -> {
                
                ParkedDetails details = parkingDao.getActiveParkedBySlotInstanceId(bay.getInstanceId());
                String info = String.format(
                    "Entry Time: %s\nOwner: %s\nContact: %s\nVehicle: %s\nEntry Note: %s",
                    details.getEntryDateTime(),
                    details.getOwnerName(),
                    details.getOwnerContact(),
                    details.getVehicleNumber(),
                    details.getEntryNote()
                );
                int exitParking = JOptionPane.showConfirmDialog(view, info, "Exit Parking?", JOptionPane.INFORMATION_MESSAGE);
                if(exitParking == 0){
                    ParkingExitView peView = new ParkingExitView();
                    new ParkingExitController(peView,bay,id,s).open();
                }
            }

//            case "disabled" -> JOptionPane.showMessageDialog(view, "Slot is disabled.");
        }
    }

    public void changeStatus(SlotInstanceData bay, SlotButton btn, String newStatus, String message) {
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
