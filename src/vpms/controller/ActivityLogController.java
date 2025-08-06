package vpms.controller;

import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;
import vpms.utils.TableEnhancer;
import vpms.view.ActivityLogView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ActivityLogController {

    private final ActivityLogView view;
    private final ActivityLogDao  dao = new ActivityLogDao();

    public ActivityLogController(ActivityLogView view) {
        this.view = view;

        loadAllLogs();                                  

        view.getSearchTextField().addActionListener(e -> searchLogs());
        view.getCancelButton().addActionListener(e -> {
            view.getSearchTextField().setText("");
            loadAllLogs();
        });
    }

    public void open() { view.setVisible(true); }


    private void loadAllLogs() {
        populateTable(dao.showActivities());
    }

    private void searchLogs() {
        String kw = view.getSearchTextField().getText().trim();
        if (kw.isEmpty()) { loadAllLogs(); return; }
        populateTable(dao.searchActivities(kw));
    }


    private void populateTable(List<ActivityLog> logs) {

        DefaultTableModel m = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "Log ID","User ID","Action","Timestamp"
        });

        for (ActivityLog log : logs) {
            m.addRow(new Object[]{
                log.getLog_id(),
                log.getUser_id(),
                log.getAction(),
                log.getTimestamp()
            });
        }

        JTable tbl = view.getLogTable();
        tbl.setModel(m);

        TableEnhancer.beautifyTable(
                tbl,
                new int[]{60,60,320,160});
    }
}
