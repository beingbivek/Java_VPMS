/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import vpms.dao.ActivityLogDao;
import vpms.dao.SlotDao;
import vpms.dao.SlotInstanceDao;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.ActivityLog;
import vpms.model.SlotData;
import vpms.model.VehicleTypeAndPriceData;
import vpms.view.AddSlotView;

/**
 *
 * @author being
 */
public class AddSlotController {
    private final SlotDao slotDao;
    private final SlotInstanceDao siDao;
    private final VehicleTypeAndPriceDao vtDao = new VehicleTypeAndPriceDao();
    private final SlotManagementController parent;
    private final AddSlotView view;
    int id;

    public AddSlotController(AddSlotView v, SlotManagementController parent,int id){
        this.slotDao = new SlotDao();
        this.siDao = new SlotInstanceDao();
        this.view   = v;
        this.parent = parent;
        this.id = id;
        fillVehicleTypes();
        view.addSaveButtonListener().addActionListener(e -> save());
    }

    private void save(){
        try{
            int vtId        = ((VehicleTypeAndPriceData)view.getVehicleTypeCombo()
                                .getSelectedItem()).getId();
            int totalSlots  = Integer.parseInt(view.getTxtTotal().getText());
            int levelNumber = Integer.parseInt(view.getTxtLevel().getText());

            int slotId = slotDao.insertReturnId(
                          new SlotData(vtId,totalSlots,levelNumber));

            VehicleTypeAndPriceData vt = vtDao.findById(vtId);
            siDao.bulkInsert(slotId,totalSlots,
                             vt.getVehicleType().substring(0,3).toUpperCase(),
                             levelNumber);

            JOptionPane.showMessageDialog(view,"Slots created.");
            parent.refresh();        // refresh JTable
            ActivityLog log = new ActivityLog(id,"Slot Added");
            new ActivityLogDao().logActivity(log);
            close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(view,"Error: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void open() {
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.dispose();
    }
    
    private void fillVehicleTypes() {
        try {
            var list = vtDao.showVehicleTypeAndPrices(); // SELECT *
            DefaultComboBoxModel<VehicleTypeAndPriceData> model;

            if (list == null || list.isEmpty()) {
                // Show a placeholder if no data
                model = new DefaultComboBoxModel<>();
                model.addElement(new VehicleTypeAndPriceData() {
                    @Override
                    public String toString() {
                        return "No vehicle types available";
                    }
                });
                view.getVehicleTypeCombo().setModel(model);
                view.getVehicleTypeCombo().setEnabled(false); // Optionally disable
            } else {
                model = new DefaultComboBoxModel<>(list.toArray(new VehicleTypeAndPriceData[0]));
                view.getVehicleTypeCombo().setModel(model);
                view.getVehicleTypeCombo().setEnabled(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Could not load vehicle types:\n" + ex.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            // Optionally clear and disable combo box on error
            DefaultComboBoxModel<VehicleTypeAndPriceData> model = new DefaultComboBoxModel<>();
            model.addElement(new VehicleTypeAndPriceData() {
                @Override
                public String toString() {
                    return "No vehicle types available";
                }
            });
            view.getVehicleTypeCombo().setModel(model);
            view.getVehicleTypeCombo().setEnabled(false);
        }
    }

}
