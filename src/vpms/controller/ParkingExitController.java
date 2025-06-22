/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vpms.dao.ParkingDao;
import vpms.dao.PaymentDao;
import vpms.dao.VehicleTypeAndPriceDao;
import vpms.model.ParkedDetails;
import vpms.model.ParkingDetails;
import vpms.model.PaymentData;
import vpms.model.SlotInstanceData;
import vpms.model.StripePaymentModel;
import vpms.view.ParkingExitView;


/**
 *
 * @author Chandani
 */
public class ParkingExitController {
    private final ParkingExitView view;
    private final ParkingDao parkingDao;
    private final PaymentDao paymentDao;
    private final SlotInstanceData bay;
    private ParkedDetails parkedDetails;
    private String entryDate; // yyyy-MM-dd
    private String entryTime; // HH:mm:ss
    private String sessionUrl;
    int id;

    public ParkingExitController(ParkingExitView view, SlotInstanceData bay, int id) { //constructor
        this.view = view;
        this.bay = bay;
        this.id = id;
        this.parkingDao = new ParkingDao();
        this.paymentDao = new PaymentDao();
        initializeContents();        
}
    
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    
    private void initializeContents() {
        parkedDetails = parkingDao.getActiveParkedBySlotInstanceId(bay.getInstanceId());
        if (parkedDetails == null) {
            JOptionPane.showMessageDialog(view, "No active parking found for this slot.");
            view.dispose();
            return;
        }

        // Split entry datetime into date and time
        String[] entryParts = vpms.utils.DateAndTimeMethods.splitDateAndTime(parkedDetails.getEntryDateTime());
        entryDate = entryParts[0];
        entryTime = entryParts[1];

        // Set entry info
        view.setEntryDateValueLabel(entryDate);
        view.setEntryTimeValueLabel(entryTime);

        // Set exit (now) info
        String[] exitParts = vpms.utils.DateAndTimeMethods.splitDateAndTime(vpms.utils.DateAndTimeMethods.getDateAndTime());
        view.setExitDateValue(exitParts[0]);
        view.setExitTimeValue(exitParts[1]);

        // Calculate and display price
        calculateAndDisplayPrice();
        view.getVerifyPaymentButton().setVisible(false);

        // Add listeners for payment and extra price
        view.getVerifyPaymentButton().addActionListener(e -> verifyPayment());
        view.getPayCashButton().addActionListener(e -> handlePayCash());
        view.getPayOnlineButton().addActionListener(e -> handlePayOnline());
        view.getExtraPrice().addActionListener(e -> calculateAndDisplayPrice());
        view.getCancelButton().addActionListener(e -> view.dispose());
    }
    
    private double totalPrice = 0.0;
    private double grand = 0.0;

    private void calculateAndDisplayPrice() {
        try {
            // Parse entry and exit datetimes
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime entryDT = LocalDateTime.parse(parkedDetails.getEntryDateTime(), dtf);
            LocalDateTime exitDT = LocalDateTime.now();

            // Calculate total minutes parked
            long totalMinutes = java.time.Duration.between(entryDT, exitDT).toMinutes();
            long intervals = totalMinutes / 15; // Each 15 min interval
            if (totalMinutes % 15 != 0) intervals++; // Partial interval counts as full

            // Get vehicle type and base price
            String vehicleNumber = parkedDetails.getVehicleNumber();
            VehicleTypeAndPriceDao vDao = new VehicleTypeAndPriceDao();
            String vehicleType = vDao.getVehicleTypeByNumber(vehicleNumber); // You may need to add this DAO method
            double basePrice = vDao.getBasePriceForVehicleType(vehicleType); // You may need to add this DAO method

            // Calculate price: base × 2^intervals
            totalPrice = basePrice * Math.pow(2, intervals - 1); // intervals-1 because first interval is base price

            // Update view
            view.setTotalPriceLabel(String.format("%.2f", totalPrice));
            updateGrandTotal();
        } catch (Exception ex) {
            view.setTotalPriceLabel("0.00");
            view.setGrandTotal("0.00");
            ex.printStackTrace();
        }
    }

    private void updateGrandTotal() {
        try {
            String extraStr = view.getExtraPrice().getText();
            double extra = extraStr.isEmpty() ? 0.0 : Double.parseDouble(extraStr);
            this.grand = totalPrice + extra;
            view.setGrandTotal(String.format("%.2f", grand));
        } catch (Exception ex) {
            view.setGrandTotal("0.00");
        }
    }

    private void handlePayCash() {
        updateGrandTotal();        
        int ans = JOptionPane.showConfirmDialog(view, "Do you want to pay with cash?");
        if(ans == 0){
            paymentSuccessful("Cash");
        }
    }
    
    private void handlePayOnline() {
        
        // Call model to create the checkout session
        StripePaymentModel stripeModel = new StripePaymentModel();
        this.sessionUrl = stripeModel.createCheckoutSession((long) grand);
        System.out.println("Session URL: " + sessionUrl);
        view.setPayOnlineStatusLabel("Session URL Generated!");
        
        if (sessionUrl != null) {
                view.setPayOnlineStatusLabel("Redirecting to payment page...");

                // Directly open the session URL
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                    }
                } catch (IOException | URISyntaxException ex) {
                    view.setPayOnlineStatusLabel("Failed to open payment page.");
                    return;
                }
                boolean paymentStatus;
                try {
                    view.setPayOnlineStatusLabel("After Payment, Press Check Button to Verify Payment!");
                    view.getVerifyPaymentButton().setVisible(true);
                    paymentStatus = stripeModel.checkPaymentStatus(sessionUrl); // Pass the session URL to check status
                    if (paymentStatus) {
                        // If payment is successful
                        paymentSuccessful("Online");
                    } else {
                        // If payment failed or was not completed
                        view.setPayOnlineStatusLabel("Payment failed or not completed.");
                    }
                } catch (InterruptedException ex) {
                     ex.printStackTrace();
                }
            } else {
                view.setPayOnlineStatusLabel("Failed to create checkout session.");
            }
    }
    
    private void paymentSuccessful(String method){
        ParkingDetails parkingDetails = parkingDao.getActiveParkingDetailsBySlotInstanceId(bay.getInstanceId());
        // Save to payments table
        PaymentData payment = new PaymentData(
            /* parking_id */ parkingDetails.getParkingId(),//getCurrentParkingId(), // get from parkedDetails or DAO
            /* vehicle_id */ parkingDetails.getVehicleId(),//getCurrentVehicleId(), // get from parkedDetails or DAO
            /* staff_id */ this.id,     // set as needed
            String.valueOf(totalPrice), // regular price
            "0", // demand price
            "0", // reservation price
            view.getExtraPrice().getText(), // extra charge
            method,
            java.time.LocalDateTime.now()
        );
        paymentDao.addPayment(payment);

        // Update parking exit
        ParkingDetails exitDetails = new ParkingDetails();
        exitDetails.setParkingId(parkingDetails.getParkingId());
        exitDetails.setExitDateTime(vpms.utils.DateAndTimeMethods.getDateAndTime());
        exitDetails.setExitNote(view.getExitNote().getText());
        exitDetails.setParkingStatus("Exited");
        exitDetails.setPenaltyApplied(false); // or true if you want
        parkingDao.vehicleExit(exitDetails);
        
        JOptionPane.showMessageDialog(view, "Payment successful. Parking exited.");
        close();
    }
    
    private void verifyPayment(){
        try {
            if(new StripePaymentModel().checkPaymentStatus(sessionUrl)){
                paymentSuccessful("Online");
            } else {
                view.setPayOnlineStatusLabel("Try Again!");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
       
    
