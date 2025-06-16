/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vpms.controller.StripePaymentController.HandlePaymentStripe;
import vpms.model.StripePaymentModel;
import vpms.view.StripePaymentView;

/**
 *
 * @author Chandani
 */
public class SecurePaymentController {
    
    private StripePaymentView view;
    private StripePaymentModel model;
    
    public SecurePaymentController(StripePaymentView view, StripePaymentModel model) {
        this.view = view;
        this.model = model;
        
        // Set action listener for the charge button
        //view.getChargeButton().addActionListener(new HandleSecurePayment());
    }
    
    public void open() {
        view.setVisible(true);
    }
    
    public void close() {
        view.dispose();
    }
    
    
}