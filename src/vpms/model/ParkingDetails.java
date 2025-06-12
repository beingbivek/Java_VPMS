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
    private String entryDateTime;
    private String exitDateTime;
    private String parkingStatus;
    private String entryNote;
    private String exitNote;
    private String slotId;
    private String parkingtype;
    private boolean penaltyApplied;

public ParkingDetails(int parkingId, String vehicleId, String entryDateTime, String exitDateTime,String entryNote,String exitNote,String slotId, String status,String type, boolean penaltyApplied) {
        this.parkingId = parkingId;
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryDateTime = entryDateTime;
        this.exitDateTime = exitDateTime;
        this.parkingStatus = status; 
        this.parkingtype = type;
        this.entryNote = exitNote;
        this.exitNote = exitNote;
        this.penaltyApplied = penaltyApplied;
        
    }


public ParkingDetails(String vehicleId, String slotId,String entryDateTime, String entryNote, boolean penaltyApplied) {
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryDateTime = entryDateTime;
        this.entryNote = entryNote;
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

    public String getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(String entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public String getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(String exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
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

    public String getParkingtype() {
        return parkingtype;
    }

    public void setParkingtype(String parkingtype) {
        this.parkingtype = parkingtype;
    }

    public boolean isPenaltyApplied() {
        return penaltyApplied;
    }

    public void setPenaltyApplied(boolean penaltyApplied) {
        this.penaltyApplied = penaltyApplied;
    }
    }
    
    