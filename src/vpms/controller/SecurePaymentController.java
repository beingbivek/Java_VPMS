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
        view.getChargeButton().addActionListener(new HandleSecurePayment());
    }
    
    public void open() {
        view.setVisible(true);
    }
    
    public void close() {
        view.dispose();
    }
    
    // Inner class to handle secure payment processing
    private class HandleSecurePayment implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // Show processing message
            view.displayMessage("Processing payment...");
            
            // Get amount from input field (NOT from output area)
            String amountText = view.getAmountField().getText().trim();
            
            if (amountText.isEmpty()) {
                view.showPaymentFailure("Please enter an amount.");
                return;
            }
            
            try {
                // Validate and convert amount
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    view.showPaymentFailure("Please enter a valid amount greater than 0.");
                    return;
                }
                
                // Convert to cents for Stripe (multiply by 100)
                long amountInCents = Math.round(amount * 100);
                
                // Call model to create checkout session with correct parameter
                String sessionUrl = model.createCheckoutSession(amountInCents);
                System.out.println("Session URL: " + sessionUrl);
                
                if (sessionUrl != null && !sessionUrl.isEmpty()) {
                    view.displayMessage("Redirecting to payment page...");
                    
                    // Open the payment URL in browser
                    try {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            Desktop.getDesktop().browse(new URI(sessionUrl));
                            
                            // Show success message
                            JOptionPane.showMessageDialog(view, 
                                "Payment page opened in browser.\nPlease complete your payment there.", 
                                "Payment Initiated", 
                                JOptionPane.INFORMATION_MESSAGE);
                            
                            // Clear the amount field
                            view.getAmountField().setText("");
                            view.displayMessage("Payment initiated successfully.");
                            
                        } else {
                            view.showPaymentFailure("Cannot open browser automatically. Please copy this URL: " + sessionUrl);
                        }
                        
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(SecurePaymentController.class.getName()).log(Level.SEVERE, null, ex);
                        view.showPaymentFailure("Failed to open payment page: " + ex.getMessage());
                        return;
                    }
                    
                    // Check payment status after opening browser
                    checkPaymentStatus(sessionUrl);
                    
                } else {
                    view.showPaymentFailure("Failed to create checkout session.");
                }
                
            } catch (NumberFormatException ex) {
                view.showPaymentFailure("Please enter a valid numeric amount.");
            } catch (Exception ex) {
                Logger.getLogger(SecurePaymentController.class.getName()).log(Level.SEVERE, null, ex);
                view.showPaymentFailure("Payment processing error: " + ex.getMessage());
            }
        }
        
        // Helper method to check payment status
        private void checkPaymentStatus(String sessionUrl) {
            // Background thread to check payment status
            new Thread(() -> {
                try {
                    Thread.sleep(3000); // Wait 3 seconds
                    
                    // Extract session ID from URL for status checking
                    String sessionId = extractSessionIdFromUrl(sessionUrl);
                    
                    boolean paymentStatus = model.checkPaymentStatus(sessionId);
                    
                    // Update UI on Event Dispatch Thread
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (paymentStatus) {
                            view.showPaymentSuccess();
                        } else {
                            view.showPaymentFailure("Payment was not completed or failed.");
                        }
                    });
                    
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(SecurePaymentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(SecurePaymentController.class.getName()).log(Level.SEVERE, null, ex);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        view.showPaymentFailure("Error checking payment status: " + ex.getMessage());
                    });
                }
            }).start();
        }
        
        // Helper method to extract session ID from URL
        private String extractSessionIdFromUrl(String sessionUrl) {
            // Extract session ID from the Stripe checkout URL
            if (sessionUrl.contains("session_id=")) {
                return sessionUrl.substring(sessionUrl.indexOf("session_id=") + 11);
            }
            // If can't extract, return the full URL
            return sessionUrl;
        }
    }
}