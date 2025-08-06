/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package vpms.dao;

/**
 *
 * @author being
 */

import org.junit.*;
import static org.junit.Assert.*;
import vpms.model.VehicleData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VehicleDaoTest {
    // Set this to a valid vehicletandp_id in your database
    private static final String TEST_VEHICLETANDP_ID = "1";
    private static final String TEST_VEHICLE_NUMBER = "TEST-1234";
    private static final String TEST_OWNER_NAME = "JUnit Owner";
    private static final String TEST_OWNER_CONTACT = "9876543210";
    private static final String TEST_CREATED_AT = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private static final String TEST_UPDATED_AT = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private static int createdVehicleId = -1;

    private VehicleDao dao = new VehicleDao();

    @Test
    public void testRegisterVehicle() {
        VehicleData vehicle = new VehicleData(
                TEST_VEHICLETANDP_ID,
                TEST_VEHICLE_NUMBER,
                TEST_OWNER_NAME,
                TEST_OWNER_CONTACT,
                TEST_CREATED_AT,
                TEST_UPDATED_AT
        );
        boolean result = dao.registerVehicle(vehicle);
        assertTrue("Vehicle should be registered", result);

        // Find the registered vehicle for further tests
        List<VehicleData> found = dao.findByNumberLike(TEST_VEHICLE_NUMBER);
        assertNotNull("Should find vehicles by number", found);
        assertTrue("Should find at least one vehicle", found.size() > 0);
        VehicleData v = found.get(0);
        createdVehicleId = v.getId();
        assertEquals(TEST_VEHICLE_NUMBER, v.getVehicleNumber());
        assertEquals(TEST_OWNER_NAME, v.getOwnerName());
        assertEquals(TEST_OWNER_CONTACT, v.getOwnerContact());
    }

    @Test
    public void testGetVehicleById() {
        if (createdVehicleId == -1) testRegisterVehicle();
        VehicleData v = dao.getVehicleById(createdVehicleId);
        assertNotNull("Should find vehicle by ID", v);
        assertEquals(TEST_VEHICLE_NUMBER, v.getVehicleNumber());
        assertEquals(TEST_OWNER_NAME, v.getOwnerName());
    }

    @Test
    public void testUpdateVehicle() {
        if (createdVehicleId == -1) testRegisterVehicle();
        VehicleData v = dao.getVehicleById(createdVehicleId);
        assertNotNull("Vehicle to update should exist", v);

        v.setOwnerName("Updated Owner");
        v.setOwnerContact("1231231234");
        v.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        boolean updated = dao.updateVehicle(v);
        assertTrue("Update should succeed", updated);

        VehicleData updatedV = dao.getVehicleById(createdVehicleId);
        assertNotNull(updatedV);
        assertEquals("Updated Owner", updatedV.getOwnerName());
        assertEquals("1231231234", updatedV.getOwnerContact());
    }

    @Test
    public void testShowVehicleNumbers() {
        String[] numbers = dao.showVehicleNumbers();
        assertNotNull("Vehicle numbers array should not be null", numbers);
        assertTrue("Should contain at least one vehicle number", numbers.length > 0);
    }

    @Test
    public void testFindByNumberLike() {
        List<VehicleData> found = dao.findByNumberLike(TEST_VEHICLE_NUMBER);
        assertNotNull("Should find vehicles by number", found);
        assertTrue("Should find at least one vehicle", found.size() > 0);
        assertEquals(TEST_VEHICLE_NUMBER, found.get(0).getVehicleNumber());
    }

    @Test
    public void testDeleteVehicleById() {
        if (createdVehicleId == -1) testRegisterVehicle();
        boolean deleted = dao.deleteVehicleById(createdVehicleId);
        assertTrue("Delete should succeed", deleted);

        VehicleData v = dao.getVehicleById(createdVehicleId);
        assertNull("Vehicle should not be found after delete", v);
    }
}
