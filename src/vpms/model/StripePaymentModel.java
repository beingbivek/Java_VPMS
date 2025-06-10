/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author being
 */
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.exception.StripeException;
import vpms.APIKeys;

public class StripePaymentModel {

//    private static final String STRIPE_API_KEY = "";

    public StripePaymentModel() {
        // Set Stripe API key
        Stripe.apiKey = new APIKeys().getStripeAPI();
    }

    public String createCheckoutSession() {
        try {
            // Create the checkout session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("npr")
                                .setUnitAmount(20000L)  // $20.00 in cents
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Sample Product")  // Product name
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity(1L)  // Quantity of the product
                        .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)  // Payment mode (one-time payment)
                .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")  // Redirect URL after successful payment
                .setCancelUrl("http://localhost:8080/cancel")    // Redirect URL if payment is canceled
                .build();

            // Create the session
            Session session = Session.create(params);

            // Return the session URL (this is the URL you should redirect the user to)
            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkPaymentStatus(String sessionUrl) throws InterruptedException {
    try {
        // Extract session ID from the URL
        String sessionId = extractSessionIdFromUrl(sessionUrl);

        if (sessionId == null || sessionId.isEmpty()) {
            System.out.println("Invalid session ID extracted.");
            return false;
        }
        System.out.println("Waiting 60 seconds before checking payment status...");
            Thread.sleep(60000);
        // Retrieve the session details from Stripe using session ID
        Session session = Session.retrieve(sessionId);
        System.out.println("ID"+session);
        System.out.println("Payment"+session.getPaymentStatus());
        // Check if payment status is 'paid'
        return "paid".equals(session.getPaymentStatus());
    } catch (StripeException e) {
        e.printStackTrace();
        return false;
    }
}

// Helper method to extract the session ID from the full URL
private String extractSessionIdFromUrl(String sessionUrl) {
    try {
        String[] parts = sessionUrl.split("/pay/");
        if (parts.length < 2) return null;

        String afterPay = parts[1];
        // Remove anything after '#'
        int hashIndex = afterPay.indexOf('#');
        if (hashIndex != -1) {
            afterPay = afterPay.substring(0, hashIndex);
        }

        return afterPay.trim(); // This is the session ID
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

}