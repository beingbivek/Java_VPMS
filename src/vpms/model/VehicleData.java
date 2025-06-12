/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author PRABHASH
 */
public class VehicleData {
    private String id;
    private String type;
    private String vehicleNumber;
    private String ownerName;
    private String ownerContact;
    private String createdAt;
    private String updatedAt;
    
    public void VehicleData(String id,String type,String vehicleNumber,String ownerName,String ownerContact, String createdAt,String updatedAt){
        this.id = id;
        this.type = type;
        this.vehicleNumber = vehicleNumber;
        this.ownerContact = ownerContact;
        this.ownerName = ownerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public VehicleData(String type,String vehicleNumber,String ownerName,String ownerContact, String createdAt,String updatedAt){

        this.type = type;
        this.vehicleNumber = vehicleNumber;
        this.ownerContact = ownerContact;
        this.ownerName = ownerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
    
    public void setVehicleNumber(String vehicleNumber){
        this.vehicleNumber = vehicleNumber;
    }
    
    public String getVehicleNumber(){
        return this.vehicleNumber;
    }
    
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }
    
    public String getOwnerName(){
        return this.ownerName;
    }
    
    public void setOwnerContact(String ownerContact){
        this.ownerContact = ownerContact;
    }
    public String getOwnerContact(){
        return this.ownerContact;
    }
    
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    
    public String getCreatedAt(){
        return this.createdAt;
    }
    
    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt(){
        return this.updatedAt;
    }
}
