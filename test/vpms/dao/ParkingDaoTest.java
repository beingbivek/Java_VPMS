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

import vpms.model.ParkingDetails;
import vpms.model.ParkedDetails;

public class ParkingDaoTest {
    // Set these to valid IDs in your test database!
    private static final int testVehicleId = 1;
    private static final int testInstanceId = 1;
    private static final String entryDateTime = "2025-06-23 10:00:00";
    private static final String entryNote = "JUnit entry";
    private static final String parkingStatus = "Parked";
    private static final String parkingType = "Normal";
    private static int createdParkingId = -1;

    private ParkingDao dao = new ParkingDao();

    @Test
    public void testRegisterParkingUser() {
        ParkingDetails parking = new ParkingDetails();
        parking.setVehicleId(testVehicleId);
        parking.setSlotId(testInstanceId);
        parking.setEntryDateTime(entryDateTime);
        parking.setEntryNote(entryNote);
        parking.setParkingStatus(parkingStatus);
        parking.setParkingtype(parkingType);

        boolean result = dao.registerParkingUser(parking);
        assertTrue("Should register parking entry", result);

        // Find the registered parking entry for further tests
        ParkingDetails found = dao.getActiveParkingDetailsBySlotInstanceId(testInstanceId);
        assertNotNull("Should find active parking details after registration", found);
        assertEquals(testVehicleId, found.getVehicleId());
        assertEquals(testInstanceId, found.getSlotInstanceId());
        assertEquals(entryDateTime, found.getEntryDateTime());
        assertEquals(entryNote, found.getEntryNote());
        assertEquals(parkingStatus, found.getParkingStatus());
        assertEquals(parkingType, found.getParkingtype());
        createdParkingId = found.getParkingId();
    }

    @Test
    public void testGetTotalVehicleEntryCount() {
        int count = dao.getTotalVehicleEntryCount();
        assertTrue("Total vehicle entry count should be >= 1", count >= 1);
    }

    @Test
    public void testGetCurrentlyParkedCount() {
        int count = dao.getCurrentlyParkedCount();
        assertTrue("Currently parked count should be >= 0", count >= 0);
    }

    @Test
    public void testGetExitedVehicleCount() {
        int count = dao.getExitedVehicleCount();
        assertTrue("Exited vehicle count should be >= 0", count >= 0);
    }

    @Test
    public void testGetActiveParkedBySlotInstanceId() {
        ParkedDetails details = dao.getActiveParkedBySlotInstanceId(testInstanceId);
        assertNotNull("Should find ParkedDetails for active parking", details);
        assertNotNull("Vehicle number should not be null", details.getVehicleNumber());
        assertNotNull("Owner name should not be null", details.getOwnerName());
        assertNotNull("Owner contact should not be null", details.getOwnerContact());
        assertNotNull("Entry date/time should not be null", details.getEntryDateTime());
    }

    @Test
    public void testGetActiveParkingDetailsBySlotInstanceId() {
        ParkingDetails details = dao.getActiveParkingDetailsBySlotInstanceId(testInstanceId);
        assertNotNull("Should find ParkingDetails for active parking", details);
        assertEquals(testInstanceId, details.getSlotInstanceId());
        assertEquals(parkingStatus, details.getParkingStatus());
    }

    @Test
    public void testVehicleExit() {
        ParkingDetails details = dao.getActiveParkingDetailsBySlotInstanceId(testInstanceId);
        assertNotNull("Should find active parking details before exit", details);

        details.setExitDateTime("2025-06-23 11:00:00");
        details.setParkingStatus("Exited");
        details.setExitNote("JUnit exit");
        details.setPenaltyApplied(false);

        boolean result = dao.vehicleExit(details);
        assertTrue("Vehicle exit should succeed", result);

        // After exit, should not find as active
        ParkingDetails afterExit = dao.getActiveParkingDetailsBySlotInstanceId(testInstanceId);
        assertNull("Should not find active parking details after exit", afterExit);
    }
}
