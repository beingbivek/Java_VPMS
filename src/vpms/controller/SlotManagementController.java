package vpms.controller;

import vpms.dao.ActivityLogDao;
import vpms.dao.SlotDao;
import vpms.model.ActivityLog;
import vpms.model.SlotData;
import vpms.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import vpms.utils.TableEnhancer;

public class SlotManagementController {

    /* ------------ fields ------------ */
    private final SlotManagementView view;
    private final SlotDao            slotDao;
    private final int                userId;           // staff / admin id

    /* ------------ ctor ------------ */
    public SlotManagementController(SlotManagementView view, int userId) throws SQLException {
        this.view    = view;
        this.slotDao = new SlotDao();
        this.userId  = userId;

        loadSlotTable();

        view.getSlotTable().setDefaultEditor(Object.class, null);
        view.getSlotTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* Toolbar label-buttons → MouseListener */
        view.addAddButtonListener()   .addMouseListener(new AddListener());
        view.addEditButtonListener()  .addMouseListener(new EditListener());
        view.addDeleteButtonListener().addMouseListener(new DeleteListener());
        view.addCancelButtonListener().addMouseListener(new CancelListener());

        /* Search real JButton → ActionListener */
        view.addSearchButtonListener().addActionListener(new SearchListener());
    }

    public void open() { view.setVisible(true); }

    /* ===================================================== *
     *  TABLE REFRESH                                        *
     * ===================================================== */
    private void loadSlotTable() {
        List<SlotData> rows = slotDao.findAll();

        DefaultTableModel m = new DefaultTableModel(){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        m.setColumnIdentifiers(new String[]{
            "Slot ID","VT-ID","Total Slots","Level"
        });
        for (SlotData s : rows) {
            m.addRow(new Object[]{
                s.getSlot_id(), s.getVehicletandp(),
                s.getNumber_of_slot(), s.getLevel_number()
            });
        }
        JTable t = view.getSlotTable();
        t.setModel(m);

        TableEnhancer.beautifyTable(t,new int[]{60,60,90,60});
    }


    /* ===================================================== *
     *  ADD                                                  *
     * ===================================================== */
    private class AddListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            AddSlotView popup = new AddSlotView();
            try {
                new AddSlotController(popup, SlotManagementController.this, userId).open();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    /* ===================================================== *
     *  EDIT                                                 *
     * ===================================================== */
    private class EditListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getSlotTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Select a slot first."); return; }

            SlotData s = new SlotData(
                    (int) view.getSlotTable().getValueAt(row,0),
                    (int) view.getSlotTable().getValueAt(row,1),
                    (int) view.getSlotTable().getValueAt(row,2),
                    (int) view.getSlotTable().getValueAt(row,3)
            );
            EditSlotView popup = new EditSlotView();
            new EditSlotController(popup, s, SlotManagementController.this, userId).open();
        }
    }

    /* ===================================================== *
     *  DELETE                                               *
     * ===================================================== */
    private class DeleteListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            int row = view.getSlotTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view,"Pick a row."); return; }

            int slotId = (int) view.getSlotTable().getValueAt(row,0);
            if (JOptionPane.showConfirmDialog(view,"Delete slot "+slotId+"?","Confirm",
                    JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION) return;

            try {
                if (slotDao.delete(slotId)) {
                    JOptionPane.showMessageDialog(view,"Slot deleted.");
                    new ActivityLogDao().logActivity(new ActivityLog(userId,"Slot deleted, id: "+slotId));
                    loadSlotTable();
                } else {
                    JOptionPane.showMessageDialog(view,"Delete failed – row not found.");
                }
            } catch (Exception ex) { showError("Delete failed.", ex); }
        }
    }

    /* ===================================================== *
     *  CANCEL (reload)                                      *
     * ===================================================== */
    private class CancelListener extends MouseAdapter {
        @Override public void mouseClicked(MouseEvent e) {
            view.getSearchText().setText("");
            loadSlotTable();
        }
    }

    /* ===================================================== *
     *  SEARCH                                               *
     * ===================================================== */
    private class SearchListener implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            String kw = view.getSearchText().getText().trim().toLowerCase();
            if (kw.isEmpty()) { loadSlotTable(); return; }

            try {
                List<SlotData> all = slotDao.findAll();
                DefaultTableModel m = (DefaultTableModel) view.getSlotTable().getModel();
                m.setRowCount(0);
                for (SlotData s : all) {
                    if ( String.valueOf(s.getSlot_id()).contains(kw) ||
                         String.valueOf(s.getVehicletandp()).contains(kw) ||
                         String.valueOf(s.getLevel_number()).contains(kw)) {
                        m.addRow(new Object[]{
                                s.getSlot_id(), s.getVehicletandp(),
                                s.getNumber_of_slot(), s.getLevel_number()
                        });
                    }
                }
            } catch (Exception ex) { showError("Search failed.", ex); }
        }
    }

    /* ===================================================== *
     *  UTIL                                                 *
     * ===================================================== */
    private void showError(String msg, Exception ex){
        JOptionPane.showMessageDialog(view, msg+"\n"+ex.getMessage(),
                                      "DB error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    /* Called by Add / Edit controllers after successful save */
    public void refresh() { loadSlotTable(); }
}
