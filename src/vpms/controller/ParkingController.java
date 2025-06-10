/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import vpms.dao.ParkingDao;
import vpms.view.EntryView;

/**
 *
 * @author Chandani
 */
public class ParkingController {
   private final EntryView view;
    private final ParkingDao parkingDao;

    public ParkingController(EntryView view) { //constructor
        this.view = view;
        this.parkingDao = new ParkingDao();
}
    
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
}