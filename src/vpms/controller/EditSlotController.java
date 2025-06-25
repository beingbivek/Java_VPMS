package vpms.controller;

import vpms.dao.SlotDao;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.SlotData;
import vpms.model.VehicleTypeAndPriceData;
import vpms.view.EditSlotView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import vpms.dao.ActivityLogDao;
import vpms.model.ActivityLog;

public class EditSlotController {
    private final EditSlotView view;
    private final SlotData slotData;
    private final SlotDao slotDao = new SlotDao();
    private final VehicleTypeAndPriceDao vtDao = new VehicleTypeAndPriceDao();
    private final SlotManagementController parent;
    private final int userId;

    public EditSlotController(EditSlotView view, SlotData slotData, SlotManagementController parent, int userId) {
        this.view = view;
        this.slotData = slotData;
        this.parent = parent;
        this.userId = userId;
        fillVehicleTypeComboBox();
        populateFields();
        view.addSaveButtonListener().addActionListener(new SaveListener());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    

    private void fillVehicleTypeComboBox() {
        List<VehicleTypeAndPriceData> types = vtDao.showVehicleTypeAndPrices();
        DefaultComboBoxModel<VehicleTypeAndPriceData> model = new DefaultComboBoxModel<>();
        for (VehicleTypeAndPriceData type : types) {
            model.addElement(type);
        }
        view.getVehicleTypeCombo().setModel(model);
    }

    private void populateFields() {
        // Set vehicle type selection
        ComboBoxModel<VehicleTypeAndPriceData> model = view.getVehicleTypeCombo().getModel();
        for (int i = 0; i < model.getSize(); i++) {
            VehicleTypeAndPriceData data = model.getElementAt(i);
            if (data.getId() == slotData.getVehicletandp()) {
                view.getVehicleTypeCombo().setSelectedIndex(i);
                break;
            }
        }
        view.getTxtTotal().setText(String.valueOf(slotData.getNumber_of_slot()));
        view.getTxtLevel().setText(String.valueOf(slotData.getLevel_number()));
    }

    class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                VehicleTypeAndPriceData selectedType = (VehicleTypeAndPriceData) view.getVehicleTypeCombo().getSelectedItem();
                int vehicletandpId = selectedType.getId();
                int numberOfSlots = Integer.parseInt(view.getTxtTotal().getText().trim());
                int levelNumber = Integer.parseInt(view.getTxtLevel().getText().trim());

                SlotData updatedSlot = new SlotData(
                        slotData.getSlot_id(),
                        vehicletandpId,
                        numberOfSlots,
                        levelNumber
                );

                boolean updated = slotDao.update(updatedSlot);
                if (updated) {
                    JOptionPane.showMessageDialog(view, "Slot updated successfully!");
                    ActivityLog log = new ActivityLog(userId,"Slot updated, id: "+slotData.getSlot_id());
                    new ActivityLogDao().logActivity(log);
                    parent.refresh();
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update slot.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
