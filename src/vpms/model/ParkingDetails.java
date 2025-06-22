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
    private int vehicleId;
    private String entryDateTime;
    private String exitDateTime;
    private String parkingStatus;
    private String entryNote;
    private String exitNote;
    private int slotInstanceId;
    private String parkingtype;
    private boolean penaltyApplied;
    

public ParkingDetails(int parkingId, int vehicleId, String entryDateTime, String exitDateTime,String entryNote,String exitNote,int slotInstanceId, String status,String type, boolean penaltyApplied) {
        this.parkingId = parkingId;
        this.vehicleId = vehicleId;
        this.slotInstanceId = slotInstanceId;
        this.entryDateTime = entryDateTime;
        this.exitDateTime = exitDateTime;
        this.parkingStatus = status; 
        this.parkingtype = type;
        this.entryNote = exitNote;
        this.exitNote = exitNote;
        this.penaltyApplied = penaltyApplied;
        
    }


    public void ParkingEntryDetails(int vehicleId, int slotInstanceId,String entryDateTime, String entryNote, String status, String parkingtype) {
        this.vehicleId = vehicleId;
        this.slotInstanceId = slotInstanceId;
        this.entryDateTime = entryDateTime;
        this.entryNote = entryNote;
        this.parkingStatus = status;
        this.parkingtype = parkingtype;
    }

    public void ParkingExitDetails(String exitDateTime,String exitNote,String status, boolean penaltyApplied){
        this.exitDateTime = exitDateTime;
        this.exitNote = exitNote;
        this.parkingStatus = status;
        this.penaltyApplied = penaltyApplied;
    }
    
    public ParkingDetails(){
        
    }
    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
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

    public int getSlotInstanceId() {
        return slotInstanceId;
    }

    public void setSlotId(int slotInstanceId) {
        this.slotInstanceId = slotInstanceId;
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
    
    