package vpms.controller;

import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;
import vpms.view.ActivityLogView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ActivityLogController {
    private final ActivityLogView view;
    private final ActivityLogDao dao = new ActivityLogDao();

    public ActivityLogController(ActivityLogView view) {
        this.view = view;
        setupTable();
        loadAllLogs();

        view.getSearchTextField().addActionListener(e -> searchLogs());
        view.getCancelButton().addActionListener(e -> loadAllLogs());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    private void setupTable() {
        view.getLogTable().setDefaultEditor(Object.class, null);
        view.getLogTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadAllLogs() {
        List<ActivityLog> logs = dao.showActivities();
        populateTable(logs);
    }

    private void searchLogs() {
        String keyword = view.getSearchTextField().getText().trim();
        if (keyword.isEmpty()) {
            loadAllLogs();
            return;
        }
        List<ActivityLog> logs = dao.searchActivities(keyword);
        populateTable(logs);
    }

    private void populateTable(List<ActivityLog> logs) {
        DefaultTableModel model = (DefaultTableModel) view.getLogTable().getModel();
        model.setRowCount(0);
        for (ActivityLog log : logs) {
            model.addRow(new Object[]{
                log.getLog_id(),
                log.getUser_id(),
                log.getAction(),
                log.getTimestamp()
            });
        }
    }
}
