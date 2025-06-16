/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.controller;

/**
 *
 * @author being
 */
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vpms.model.StripePaymentModel;
import vpms.view.StripePaymentView;

public class StripePaymentController {

    private StripePaymentView view;
    private StripePaymentModel model;

    public StripePaymentController(StripePaymentView view, StripePaymentModel model) {
        this.view = view;
        this.model = model;

        // Set action listener for the charge button
        view.getChargeButton().addActionListener(new HandlePaymentStripe());
    }

    public void open() {
        view.setVisible(true); 
    }

    public void close() {
        view.dispose();
    }


    class HandlePaymentStripe implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Show processing message
            view.displayMessage("Processing payment...");

            // Get amount from proper input field
String amountText = view.getAmountField().getText().trim();

if (amountText.isEmpty()) {
    view.displayMessage("Please enter an amount.");
    return;
}

try {
    // Validate and convert amount
    double amount = Double.parseDouble(amountText);
    if (amount <= 0) {
        view.displayMessage("Please enter a valid amount greater than 0.");
        return;
    }
    
    // Convert to cents for Stripe (multiply by 100)
    long amountInCents = Math.round(amount * 100);
    
    // Now call with correct parameter
    String sessionUrl = model.createCheckoutSession(amountInCents);
    
} catch (NumberFormatException ex) {
    view.displayMessage("Please enter a valid numeric amount.");
    return;
}
        }
    }
        }
    

    
        
