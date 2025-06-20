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
    private int vehicleId;
    private int slotId;
    private String vehicleType;
    private String contact;
    private String entryTime;
    private String exitTime;
    private String duration;
    private String status;
    private String paymentStatus;
    
    public ReservationData(int id, int vehicleId, int slotId, String vehicleType, String contact, String entryTime, String exitTime, String duration, String status, String paymentStatus) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.vehicleType = vehicleType;
        this.contact = contact;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.duration = duration;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
    
    public ReservationData(int vehicleId, int slotId, String vehicleType, String contact, String entryTime, String exitTime, String duration, String status, String paymentStatus) {
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.vehicleType = vehicleType;
        this.contact = contact;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.duration = duration;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    
    
}
