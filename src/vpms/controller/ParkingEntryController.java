/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vpms.dao.ParkingDao;
import vpms.view.EntryView;

/**
 *
 * @author Chandani
 */
public class ParkingEntryController {
   private final EntryView view;
    private final ParkingDao parkingDao;

    public ParkingEntryController(EntryView view) { //constructor
        this.view = view;
        this.parkingDao = new ParkingDao();
}
    
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    
    
    class LoginHandler implements ActionListener { 

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }
}