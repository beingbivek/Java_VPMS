/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author being
 */
public class ParkedDetails {
    private String entryDateTime;
    private String entryNote;
    private String vehicleNumber;
    private String ownerName;
    private String ownerContact;

    public String getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(String entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public String getEntryNote() {
        return entryNote;
    }

    public void setEntryNote(String entryNote) {
        this.entryNote = entryNote;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }
}
