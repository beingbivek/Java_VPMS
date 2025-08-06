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
        attachStaffSearchListeners();
        
        view.searchStaffField().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (view.searchStaffField().getText().equalsIgnoreCase("search")) {
                    view.setValueInSearchTextField("");
                }
            }
        });
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

    private void loadStaffTable(String nameFilter) {
        DefaultTableModel m = new DefaultTableModel(){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{ "Name", "Staff ID", "Status" });

        for (UserData u : uDao.showUsers()) {
            if (!"Staff".equalsIgnoreCase(u.getType())) continue;
            if (nameFilter != null && !nameFilter.trim().isEmpty()) {
                if (!u.getName().toLowerCase().contains(nameFilter.trim().toLowerCase())) continue;
            }
            m.addRow(new Object[]{ u.getName(), u.getId(), u.getStatus() });
        }
        JTable tbl = view.getStaffTable();
        tbl.setModel(m);
        TableEnhancer.beautifyTable(tbl, new int[]{180,70,80});
    }
    
    private void attachStaffSearchListeners() {
        // Live search on typing/ENTER in the staff search field
        view.searchStaffField().addActionListener(e -> doStaffSearch());
        view.searchStaffField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                doStaffSearch();
            }
        });

        // Cancel icon-click: clear and reload all staff
        view.cancelSearchButton().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view.setValueInSearchTextField("");
                loadStaffTable();
            }
        });
    }

    // The search logic itself:
    private void doStaffSearch() {
        String q = view.searchStaffField().getText().trim();
        if (q.equalsIgnoreCase("search") || q.isEmpty()) {
            loadStaffTable();
            return;
        }
        loadStaffTable(q);
    }
    
    private void loadStaffTable() {
        loadStaffTable(""); // loads all staff
    }
}
