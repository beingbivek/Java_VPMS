/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package vpms.dao;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author being
 */

import vpms.model.PaymentData;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentDaoTest {
    // Set these to valid IDs in your test DB
    private static final int TEST_PARKING_ID = 1;
    private static final int TEST_VEHICLE_ID = 1;
    private static final int TEST_USER_ID = 1;

    private static final String REGULAR_PRICE = "20.0";
    private static final String DEMAND_PRICE = "0.0";
    private static final String RESERVATION_PRICE = "0.0";
    private static final String EXTRA_CHARGE = "0.0";
    private static final String PAYMENT_STATUS = "Cash";
    private static LocalDateTime PAYMENT_TIME = LocalDateTime.now();

    private static int createdPaymentId = -1;

    private PaymentDao dao = new PaymentDao();

    @Test
    public void testAddPayment() {
        PaymentData payment = new PaymentData(
                TEST_PARKING_ID,
                TEST_VEHICLE_ID,
                TEST_USER_ID,
                REGULAR_PRICE,
                DEMAND_PRICE,
                RESERVATION_PRICE,
                EXTRA_CHARGE,
                PAYMENT_STATUS,
                PAYMENT_TIME
        );
        boolean result = dao.addPayment(payment);
        assertTrue("Payment should be added", result);

        // Find the inserted payment for further tests
        List<PaymentData> payments = dao.showPayments();
        assertNotNull("Payments list should not be null", payments);
        PaymentData found = payments.stream()
                .filter(p -> p.getParking_id() == TEST_PARKING_ID
                        && p.getVehicle_id() == TEST_VEHICLE_ID
                        && p.getUser_id() == TEST_USER_ID
                        && p.getRegularPrice().equals(REGULAR_PRICE)
                        && p.getPaymentStatus().equals(PAYMENT_STATUS))
                .findFirst()
                .orElse(null);
        assertNotNull("Inserted payment should be found", found);
        createdPaymentId = found.getPayment_id();
    }

    @Test
    public void testShowPayments() {
        List<PaymentData> payments = dao.showPayments();
        assertNotNull("Payments list should not be null", payments);
        assertTrue("Should have at least one payment", payments.size() > 0);
    }

    @Test
    public void testUpdatePayment() {
        // Ensure payment exists
        if (createdPaymentId == -1) {
            testAddPayment();
        }
        List<PaymentData> payments = dao.showPayments();
        PaymentData payment = payments.stream()
                .filter(p -> p.getPayment_id() == createdPaymentId)
                .findFirst()
                .orElse(null);
        assertNotNull("Payment to update should exist", payment);

        // Update fields
        payment.setRegularPrice("30.0");
        payment.setPaymentStatus("Online");
        payment.setPaymentTime(LocalDateTime.now());

        boolean result = dao.updatePayment(payment);
        assertTrue("Update should succeed", result);

        // Verify update
        List<PaymentData> updatedList = dao.showPayments();
        PaymentData updated = updatedList.stream()
                .filter(p -> p.getPayment_id() == createdPaymentId)
                .findFirst()
                .orElse(null);
        assertNotNull("Updated payment should exist", updated);
        assertEquals("30.0", updated.getRegularPrice());
        assertEquals("Online", updated.getPaymentStatus());
    }

    @Test
    public void testDeletePayment() {
        // Ensure payment exists
        if (createdPaymentId == -1) {
            testAddPayment();
        }
        boolean result = dao.deletePayment(createdPaymentId);
        assertTrue("Delete should succeed", result);

        // Verify deletion
        List<PaymentData> payments = dao.showPayments();
        boolean exists = payments.stream().anyMatch(p -> p.getPayment_id() == createdPaymentId);
        assertFalse("Deleted payment should not exist", exists);
    }

    @Test
    public void testGetTotalRevenue() {
        double revenue = dao.getTotalRevenue();
        assertTrue("Total revenue should be >= 0", revenue >= 0);
    }

    @Test
    public void testGetTotalPaymentCount() {
        int count = dao.getTotalPaymentCount();
        assertTrue("Total payment count should be >= 0", count >= 0);
    }

    @Test
    public void testSearchPayments() {
        List<PaymentData> payments = dao.searchPayments(PAYMENT_STATUS);
        assertNotNull("Search payments should not return null", payments);
        assertTrue("Should find at least one payment with status", payments.stream()
                .anyMatch(p -> PAYMENT_STATUS.equals(p.getPaymentStatus())));
    }
}

