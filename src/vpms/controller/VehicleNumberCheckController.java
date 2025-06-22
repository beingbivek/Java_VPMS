package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.model.VehicleData;
import vpms.view.VehicleNumberCheckView;
import vpms.view.ParkingEntryView;
import vpms.view.AddVehiclesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import vpms.model.SlotInstanceData;

public class VehicleNumberCheckController {
    private final VehicleNumberCheckView view;
    private final VehicleDao vehicleDao = new VehicleDao();
    private List<VehicleData> foundVehicles;
    private SlotInstanceData bay;
    int id;

    public VehicleNumberCheckController(VehicleNumberCheckView view,SlotInstanceData bay,int id) {
        this.view = view;
        this.bay = bay;
        this.id = id;
        this.view.addSearchButtonListener(new SearchListener());
        this.view.addSelectButtonListener(new SelectListener());
        this.view.addAddVehicleButtonListener(new AddVehicleListener());
    }
    
    public void open(){
        this.view.setVisible(true);
    }
    
    public void close(){
        this.view.dispose();
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = view.getSearchText().trim();
            if (input.isEmpty()) {
                view.setVehicleCheckInfoLabel("Enter a vehicle number.");
                view.setVehicleListData(new String[0]);
                return;
            }
            foundVehicles = vehicleDao.findByNumberLike(input);
            if (foundVehicles.isEmpty()) {
                view.setVehicleCheckInfoLabel("Not found! Now add vehicle.");
                view.setVehicleListData(new String[0]);
            } else {
                String[] display = foundVehicles.stream()
                        .map(v -> v.getVehicleNumber() + " (" + v.getOwnerName() + ")")
                        .toArray(String[]::new);
                view.setVehicleListData(display);
                view.setVehicleCheckInfoLabel(foundVehicles.size() + " result(s) found.");
            }
        }
    }

    class SelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = view.getSelectedVehicleIndex();
            if (foundVehicles == null || idx < 0 || idx >= foundVehicles.size()) {
                JOptionPane.showMessageDialog(view, "Select a vehicle from the list.");
                return;
            }
            VehicleData selected = foundVehicles.get(idx);
            ParkingEntryView entryView = new ParkingEntryView();
            new ParkingEntryController(entryView, selected, bay,id).open();
            view.dispose();
        }
    }

    class AddVehicleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddVehiclesView addView = new AddVehiclesView();
            new AddVehiclesController(addView,id).open();
            view.dispose();
        }
    }
}
