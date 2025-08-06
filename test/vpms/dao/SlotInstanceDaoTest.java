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
import vpms.model.SlotInstanceData;

import java.util.List;
import java.util.Set;

public class SlotInstanceDaoTest {
    // Set these to valid values for your test database
    private static final int TEST_SLOT_ID = 1; // slot_id from slots table
    private static final int TEST_LEVEL_NUMBER = 1; // level_number from slots table
    private static final String TEST_PREFIX = "TST";
    private static final int BULK_TOTAL = 3;

    private SlotInstanceDao dao = new SlotInstanceDao();

    @Test
    public void testBulkInsertAndFindByLevel() {
        int initialCount = dao.getTotalSlotCount();
        dao.bulkInsert(TEST_SLOT_ID, BULK_TOTAL, TEST_PREFIX, TEST_LEVEL_NUMBER);

        List<SlotInstanceData> slots = dao.findByLevel(TEST_LEVEL_NUMBER);
        assertNotNull("findByLevel should not return null", slots);
        assertTrue("Should find at least BULK_TOTAL slots for test level", slots.size() >= BULK_TOTAL);

        // Check that codes with the test prefix exist
        boolean found = slots.stream().anyMatch(s -> s.getCode().startsWith(TEST_PREFIX));
        assertTrue("At least one slot with the test prefix should exist", found);

        // Optionally, get an instance_id for status update test
        SlotInstanceData testSlot = slots.stream().filter(s -> s.getCode().startsWith(TEST_PREFIX)).findFirst().orElse(null);
        assertNotNull("Should find a slot to update", testSlot);

        // Test updateStatus
        boolean updated = dao.updateStatus(testSlot.getInstanceId(), "occupied");
        assertTrue("updateStatus should succeed", updated);

        // Verify status update
        List<SlotInstanceData> updatedSlots = dao.findByLevel(TEST_LEVEL_NUMBER);
        SlotInstanceData updatedSlot = updatedSlots.stream()
                .filter(s -> s.getInstanceId() == testSlot.getInstanceId())
                .findFirst().orElse(null);
        assertNotNull(updatedSlot);
        assertEquals("occupied", updatedSlot.getStatus());
    }

    @Test
    public void testFindLevels() {
        Set<Integer> levels = dao.findLevels();
        assertNotNull("findLevels should not return null", levels);
        assertTrue("Should have at least one level", levels.size() > 0);
        assertTrue("Test level should be present", levels.contains(TEST_LEVEL_NUMBER));
    }

    @Test
    public void testGetTotalSlotCount() {
        int count = dao.getTotalSlotCount();
        assertTrue("Total slot count should be >= 0", count >= 0);
    }

    @Test
    public void testGetAvailableSlotCount() {
        int count = dao.getAvailableSlotCount();
        assertTrue("Available slot count should be >= 0", count >= 0);
    }
}
