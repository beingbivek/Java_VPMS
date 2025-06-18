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
    
    public ReservationData(int id, int userId, int vehicleId, int slotId, String reservationTime, String status) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.reservationTime = reservationTime;
        this.status = status;
    }
    
    public ReservationData(int userId, int vehicleId, int slotId, String reservationTime, String status) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.reservationTime = reservationTime;
        this.status = status;
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
    
    
}
