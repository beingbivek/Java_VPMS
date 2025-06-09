/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

import java.time.LocalDateTime;

/**
 *
 * @author PRABHASH
 */ 
public class PaymentData {
    private int payment_id;
    private int parking_id;
    private int vehicle_id;
    private int user_id;
    private String regularPrice; 
    private String demandPrice;
    private String reservationPrice;
    private String extraCharge;
    private String paymentStatus;
    private String paymentTime;

public PaymentData (int payment_id, int parking_id, int vehicle_id, int staff_id, String regularPrice, String demandPrice, String reservationPrice, String extraCharge, String paymentStatus, String paymentTime){
   this.payment_id = payment_id;
   this.parking_id = parking_id;
   this.vehicle_id = vehicle_id;
   this.user_id = staff_id;
   this.regularPrice = regularPrice;
   this.demandPrice = demandPrice;
   this.reservationPrice = reservationPrice;
   this.extraCharge = extraCharge;
   this.paymentStatus = paymentStatus;
   this.paymentTime = paymentTime;  
}
public PaymentData (int parking_id, int vehicle_id, int staff_id, String regularPrice, String demandPrice, String reservationPrice, String extraCharge, String paymentStatus, String paymentTime){
   this.parking_id = parking_id;
   this.vehicle_id = vehicle_id;
   this.user_id = staff_id;
   this.regularPrice = regularPrice;
   this.demandPrice = demandPrice;
   this.reservationPrice = reservationPrice;
   this.extraCharge = extraCharge;
   this.paymentStatus = paymentStatus;
   this.paymentTime = paymentTime;
}

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getDemandPrice() {
        return demandPrice;
    }

    public void setDemandPrice(String demandPrice) {
        this.demandPrice = demandPrice;
    }

    public String getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(String reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public String getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(String extraCharge) {
        this.extraCharge = extraCharge;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    }

    


