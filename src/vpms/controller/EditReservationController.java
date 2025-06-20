/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import vpms.dao.ReservationDao;
import vpms.model.ReservationData;
import vpms.view.EditReservationView;

/**
 *
 * @author PRABHASH
 */
public class EditReservationController {
    private EditReservationView view;
    private ReservationDao dao;
    private ReservationData selected;

    public EditReservationController(EditReservationView view, ReservationData selected) {
        this.view = view;
        this.dao = new ReservationDao();
        this.selected = selected;

        populateFields();
        view.getSubmitButton().addActionListener(new SubmitAction());
        view.getCancelButton().addActionListener(e -> view.dispose());
    }

    private void populateFields() {
        view.setVehicleId(String.valueOf(selected.getVehicleId()));
        view.setVehicleType(selected.getVehicleType());
        view.setContact(selected.getContact());
        view.setSlotId(String.valueOf(selected.getSlotId()));
        view.setEntryTime(selected.getEntryTime());
        view.setExitTime(selected.getExitTime());
        view.setStatus(selected.getStatus());
        view.setPaymentStatus(selected.getPaymentStatus());
        view.setDurationLabel(selected.getDuration());
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
                String status = view.getStatus();
                String paymentStatus = view.getPaymentStatus();

                // Recalculate duration
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime start = LocalTime.parse(entryTime, formatter);
                LocalTime end = LocalTime.parse(exitTime, formatter);
                long minutes = Duration.between(start, end).toMinutes();
                String duration = (minutes >= 60)
                        ? (minutes / 60) + " hr " + (minutes % 60) + " min"
                        : minutes + " min";
                view.setDurationLabel(duration);

                // Update data
                ReservationData updated = new ReservationData(
                        selected.getId(), vehicleId, slotId, vehicleType, contact,
                        entryTime, exitTime, duration, status, paymentStatus
                );

                dao.updateReservation(updated);
                JOptionPane.showMessageDialog(view, "Reservation updated successfully.");
                view.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Please fill all fields correctly.");
            }
        }
    }
}
