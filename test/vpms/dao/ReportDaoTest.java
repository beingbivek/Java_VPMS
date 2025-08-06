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

import vpms.model.ReportModel;

import java.time.LocalDateTime;
import java.util.List;

public class ReportDaoTest {
    private ReportDao dao = new ReportDao();

    // Adjust these to match your test data!
    private static final LocalDateTime FROM = LocalDateTime.now().minusDays(30);
    private static final LocalDateTime TO = LocalDateTime.now().plusDays(1);

    @Test
    public void testGetReportByDate() {
        List<ReportModel> reports = dao.getReportByDate(FROM, TO);
        assertNotNull("Report list should not be null", reports);
        // If your DB has data, you can check for size > 0
        // assertTrue("Should have at least one report", reports.size() > 0);

        // If there are reports, check fields
        if (!reports.isEmpty()) {
            ReportModel report = reports.get(0);
            assertNotNull("Payment time should not be null", report.getPaymentTime());
            assertNotNull("Vehicle number should not be null", report.getVehicleNumber());
            assertNotNull("Entry time should not be null", report.getEntryTime());
            assertNotNull("Exit time should not be null", report.getExitTime());
            // totalFee can be 0, but should not be null
        }
    }

    @Test
    public void testGetTotalRevenueByDate() {
        double total = dao.getTotalRevenueByDate(FROM, TO);
        assertTrue("Total revenue should be >= 0", total >= 0);
    }
}
