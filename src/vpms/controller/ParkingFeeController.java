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
//public BigDecimal generateBill(int ticketId) throws SQLException {
//        Parking ticket = dao.getTicketById(ticketId);
//        if (ticket == null || ticket.getExitTime() == null) {
//            throw new RuntimeException("Invalid ticket or vehicle not exited yet.");
//        }
//        ParkingRate rate = dao.getCurrentRate();
//        return FeeCalculator.calculateFee(ticket, rate);
//    }


}



   

