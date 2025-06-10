/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;
import vpms.dao.ParkingDao;
import vpms.view.ParkingPaymentView;



/**
 *
 * @author Chandani
 */
public class ParkingFeeController {
     private ParkingPaymentView view;
    private ParkingDao parkingDao;
public ParkingFeeController (ParkingPaymentView view) {
    this.view = view;
    this.parkingDao = new ParkingDao();
    
}
public void open(){
    this.view.setVisible(true);
   
}
public void close(){
    this.view.dispose();
}



}



   

