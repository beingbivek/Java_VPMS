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
import vpms.model.SlotData;

import java.util.List;

public class SlotDaoTest {
    // Set this to a valid vehicle_type_and_price id in your database
    private static final int TEST_VEHICLETANDP_ID = 1;
    private static final int TEST_NUMBER_OF_SLOT = 5;
    private static final int TEST_LEVEL_NUMBER = 2;

    private static int createdSlotId = -1;

    private SlotDao dao = new SlotDao();

    @Test
    public void testInsertReturnId() {
        SlotData slot = new SlotData(TEST_VEHICLETANDP_ID, TEST_NUMBER_OF_SLOT, TEST_LEVEL_NUMBER);
        int id = dao.insertReturnId(slot);
        assertTrue("Inserted slot ID should be positive", id > 0);
        createdSlotId = id;
    }

    @Test
    public void testFindById() {
        if (createdSlotId == -1) testInsertReturnId();
        SlotData slot = dao.findById(createdSlotId);
        assertNotNull("Should find slot by ID", slot);
        assertEquals(TEST_VEHICLETANDP_ID, slot.getVehicletandp());
        assertEquals(TEST_NUMBER_OF_SLOT, slot.getNumber_of_slot());
        assertEquals(TEST_LEVEL_NUMBER, slot.getLevel_number());
    }

    @Test
    public void testFindAll() {
        List<SlotData> slots = dao.findAll();
        assertNotNull("Slots list should not be null", slots);
        assertTrue("Should have at least one slot", slots.size() > 0);
    }

    @Test
    public void testUpdate() {
        if (createdSlotId == -1) testInsertReturnId();
        SlotData slot = dao.findById(createdSlotId);
        assertNotNull("Slot to update should exist", slot);

        // Change number_of_slot and level_number
        slot.setNumber_of_slot(TEST_NUMBER_OF_SLOT + 1);
        slot.setLevel_number(TEST_LEVEL_NUMBER + 1);
        boolean updated = dao.update(slot);
        assertTrue("Update should succeed", updated);

        // Verify update
        SlotData updatedSlot = dao.findById(createdSlotId);
        assertNotNull(updatedSlot);
        assertEquals(TEST_NUMBER_OF_SLOT + 1, updatedSlot.getNumber_of_slot());
        assertEquals(TEST_LEVEL_NUMBER + 1, updatedSlot.getLevel_number());
    }

    @Test
    public void testDelete() {
        if (createdSlotId == -1) testInsertReturnId();
        boolean deleted = dao.delete(createdSlotId);
        assertTrue("Delete should succeed", deleted);

        // Verify deletion
        SlotData slot = dao.findById(createdSlotId);
        assertNull("Slot should not be found after delete", slot);
    }
}
