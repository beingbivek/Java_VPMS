/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;
/**
 *
 * @author Chandani
 */
public class ParkingDetails {
   private int parkingId;
    private String vehicleId;
    private String entryTime;
    private String exitTime;
    private String parkingStatus;
    private String entryNote;
    private String exitNote;
    private String slotId;
    private String parkingtype;
    private boolean penaltyApplied;

public ParkingDetails(int parkingId, String vehicleId, String entryTime, String exitTime,String entryNote,String exitNote,String slotId, String status,String type, boolean penaltyApplied) {
        this.parkingId = parkingId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.parkingStatus = status; 
        this.parkingtype = type;
        this.entryNote = exitNote;
        this.exitNote = exitNote;
        this.penaltyApplied = penaltyApplied;
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String status) {
        this.parkingStatus = status;
    }

    public String getEntryNote() {
        return entryNote;
    }

    public void setEntryNote(String entryNote) {
        this.entryNote = entryNote;
    }

    public String getExitNote() {
        return exitNote;
    }

    public void setExitNote(String exitNote) {
        this.exitNote = exitNote;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getParkingType() {
        return parkingtype;
    }

    public void setType(String type) {
        this.parkingtype = type;
    }

    public boolean isPenaltyApplied() {
        return penaltyApplied;
    }

    public void setPenaltyApplied(boolean penaltyApplied) {
        this.penaltyApplied = penaltyApplied;
    }

    }
