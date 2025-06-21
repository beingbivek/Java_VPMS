/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

import java.time.LocalDateTime;


/**
 *
 * @author Chandani
 */
public class ReportModel {
    private LocalDateTime paymentTime;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalFee;
    
    public ReportModel(LocalDateTime paymentTime, String vehicleNumber, LocalDateTime entryTime,LocalDateTime exitTime,double totalFee){
        this.paymentTime = paymentTime;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalFee = totalFee;
        
    }
    public LocalDateTime getPaymentTime(){
        return paymentTime;
    }
    public String getVehicleNumber(){
        return vehicleNumber;
    }
    public LocalDateTime getEntryTime(){
        return entryTime;
    }
    public LocalDateTime getExitTime(){
        return exitTime;
    }
    public double getTotalFee(){
        return totalFee;
    }
    
}
