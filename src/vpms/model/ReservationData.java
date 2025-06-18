/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author PRABHASH
 */
public class ReservationData {
    private int id;
    private int userId;
    private int vehicleId;
    private int slotId;
    private String reservationTime;
    private String status;
    private String duration;        
    private String paymentStatus;
    
    public ReservationData(int id, int userId, int vehicleId, int slotId, String reservationTime, String status, String duration, String paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.reservationTime = reservationTime;
        this.status = status;
        this.duration = duration;
        this.paymentStatus = paymentStatus;
        
    }
    
    public ReservationData(int userId, int vehicleId, int slotId, String reservationTime, String status, String duration, String paymentStatus) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.reservationTime = reservationTime;
        this.status = status;
        this.duration = duration;
        this.paymentStatus = paymentStatus;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    
}
