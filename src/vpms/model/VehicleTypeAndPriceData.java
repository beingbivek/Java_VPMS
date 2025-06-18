/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author Rupes
 */
public class VehicleTypeAndPriceData {
    private int id;
    private String vehicleType;
    private String reservationPrice;
    private String regularPrice;
    private String demandPrice;
    private String extraCharge;
    private String status;
    
    public VehicleTypeAndPriceData(int id,String vehicleType, String reservationPrice, String regularPrice,String demandPrice, String extraCharge,String status){
        this.id = id;
        this.vehicleType = vehicleType;
        this.reservationPrice = reservationPrice;
        this.regularPrice = regularPrice;
        this.demandPrice = demandPrice;
        this.extraCharge = extraCharge;
        this.status = status;
    }
    public VehicleTypeAndPriceData(String vehicleType, String reservationPrice, String regularPrice,String demandPrice, String extraCharge,String status){
        this.vehicleType = vehicleType;
        this.reservationPrice = reservationPrice;
        this.regularPrice = regularPrice;
        this.demandPrice = demandPrice;
        this.extraCharge = extraCharge;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(String reservationPrice) {
        this.reservationPrice = reservationPrice;
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

    public String getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(String extraCharge) {
        this.extraCharge = extraCharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}