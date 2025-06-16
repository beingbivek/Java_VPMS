/*
 *  AddVehiclesController.java – revised so the row records
 *  WHO added the vehicle (userId comes from the logged-in user).
 */
package vpms.controller;

import vpms.dao.VehicleDao;
import vpms.model.VehicleData;
import vpms.model.UserData;
import vpms.view.AddVehiclesView;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class AddVehiclesController {

    /* ---------- fields ---------- */
    private final AddVehiclesView view;
    private final UserData        user;          // logged-in user (passed by caller)
    private final VehicleDao      dao;

    /* ---------- ctor ---------- */
    public AddVehiclesController(AddVehiclesView view, UserData user) {
        this.dao = new VehicleDao();
        this.view = view;
        this.user = user;
        // If the Save button is in the form, register here, e.g.
        this.view.addSaveButtonListener().addActionListener(e -> handleSaveVehicle());
    }

    public void open()  { view.setVisible(true); }
    public void close() { view.dispose();        }

    /* ========================================================= *
     *  P U B L I C   A P I                                      *
     * ========================================================= */
    public boolean addVehicle(String type, String vehicleNumber,
                              String ownerName, String ownerContact,
                              String createdAt, String updatedAt) throws Exception {

        VehicleData data = new VehicleData(
                type, vehicleNumber, ownerName, ownerContact,
                createdAt, updatedAt);  
        return dao.registerVehicle(data);
    }

    /* ========================================================= *
     *  U I   S A V E   H A N D L E R                            *
     * ========================================================= */
    public void handleSaveVehicle() {

        try {
            /* ---------- read & trim form fields ---------- */
            String type          = view.getTxtType()         .getText().trim();
            String vehicleNumber = view.getTxtVehicleNumber().getText().trim();
            String ownerName     = view.getTxtOwnerName()    .getText().trim();
            String ownerContact  = view.getTxtOwnerContact() .getText().trim();

            /* ---------- validate ---------- */
            List<String> allowed = Arrays.asList("Car", "Bike", "Van", "EV");
            if (!allowed.contains(type)) {
                JOptionPane.showMessageDialog(view,
                        "Vehicle Type must be one of: Car, Bike, Van or EV");
                return;
            }
            if (!vehicleNumber.matches("^[A-Za-z0-9 -]{6,20}$")) {
                JOptionPane.showMessageDialog(view,
                        "Vehicle number must be 6–20 characters."); return;
            }
            if (!ownerContact.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(view,
                        "Contact must be a 10-digit number."); return;
            }

            /* ---------- timestamps ---------- */
            String ts = LocalDateTime.now()
                                     .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            /* ---------- persist ---------- */
            VehicleData data = new VehicleData(
                    type, vehicleNumber, ownerName, ownerContact,
                    ts, ts);                // userId included

            boolean ok = dao.registerVehicle(data);

            JOptionPane.showMessageDialog(view,
                    ok ? "Vehicle added successfully!"
                       : "Failed to add vehicle.");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                    "Error: " + ex.getMessage());
        }
    }
}
