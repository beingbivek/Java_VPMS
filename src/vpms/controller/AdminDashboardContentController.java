package vpms.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import vpms.dao.*;
import vpms.model.*;
import vpms.utils.TableEnhancer;                
import vpms.view.AdminDashboardContentView;

public class AdminDashboardContentController {

    /* ---------- fields ---------- */
    private final AdminDashboardContentView view;
    private final UserDao         uDao = new UserDao();
    private final SlotInstanceDao sDao = new SlotInstanceDao();
    private final ParkingDao      pDao = new ParkingDao();
    private final PaymentDao      paDao= new PaymentDao();
    private final ActivityLogDao  aDao = new ActivityLogDao();

    public AdminDashboardContentController(AdminDashboardContentView view){
        this.view = view;
        insertDashboardData();
        loadRecentActivities();
        loadStaffTable();
    }

    public void open(){ view.setVisible(true); }
    public void close(){ view.dispose();       }

    /* ---------- dashboard counters ---------- */
    public void insertDashboardData(){
        view.settotalActiveStffsLabel( String.valueOf(uDao.getActiveStaffCount()));
        view.setcurrentlyOccupiedSpacejLabel(
            (sDao.getTotalSlotCount()-sDao.getAvailableSlotCount()) + "/" +
             sDao.getTotalSlotCount());
        view.setvehicleEnteredTodayjLabel( String.valueOf(pDao.getTotalVehicleEntryCount()));
        view.vehicleExitedTodayjLabel    ( String.valueOf(pDao.getExitedVehicleCount()));
        view.totalEarningsTodayjLabel    ( String.valueOf(paDao.getTotalRevenue()));
    }

    /* =============================================================== *
     *  ACTIVITY TABLE                                                 *
     * =============================================================== */
    private void loadRecentActivities() {
        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{ "Timestamp","Action","User ID" });

        for (ActivityLog l : aDao.fetchLast(30)) {
            m.addRow(new Object[]{
                l.getTimestamp(), l.getAction(), l.getUser_id()
            });
        }
        JTable tbl = view.getActivityTable();
        tbl.setModel(m);

        /* nice header / zebra / widths */
        TableEnhancer.beautifyTable(tbl, new int[]{160,420,70});
    }

    /* =============================================================== *
     *  STAFF TABLE                                                    *
     * =============================================================== */
    private void loadStaffTable() {
        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{ "Name","Staff ID","Status" });

        for (UserData u : uDao.showUsers()) {
            if (!"Staff".equalsIgnoreCase(u.getType())) continue;
            m.addRow(new Object[]{ u.getName(), u.getId(), u.getStatus() });
        }
        JTable tbl = view.getStaffTable();
        tbl.setModel(m);

        TableEnhancer.beautifyTable(tbl, new int[]{180,70,80});
    }
}
