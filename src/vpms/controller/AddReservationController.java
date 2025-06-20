/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import vpms.dao.ReservationDao;
import vpms.model.ReservationData;
import vpms.view.AddReservationView;
/**
 *
 * @author PRABHASH
 */
public class AddReservationController {
    private AddReservationView view;
    private ReservationDao dao;

    public AddReservationController(AddReservationView view) {
        this.view = view;
        this.dao = new ReservationDao();

        view.getSubmitButton().addActionListener(new SubmitAction());
        view.getCancelButton().addActionListener(new CancelAction());
    }

    class SubmitAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int vehicleId = Integer.parseInt(view.getVehicleId());
                String vehicleType = view.getVehicleType();
                String contact = view.getContact();
                int slotId = Integer.parseInt(view.getSlotId());
                String entryTime = view.getEntryTime();
                String exitTime = view.getExitTime();
                String status = view.getStatus();  // should be 'Reserved'
                String paymentStatus = view.getPaymentStatus();

                // Calculate duration
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime start = LocalTime.parse(entryTime, formatter);
                LocalTime end = LocalTime.parse(exitTime, formatter);
                long minutes = Duration.between(start, end).toMinutes();

                String duration = (minutes >= 60)
                        ? (minutes / 60) + " hr " + (minutes % 60) + " min"
                        : minutes + " min";

                view.setDurationLabel(duration);

                // Build data object
                ReservationData data = new ReservationData(vehicleId, slotId, vehicleType, contact,
                        entryTime, exitTime, duration, status, paymentStatus);

                dao.addReservation(data);
                JOptionPane.showMessageDialog(view, "Reservation added successfully.");
                view.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Please enter all fields correctly.");
            }
        }
    }

    class CancelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
}
