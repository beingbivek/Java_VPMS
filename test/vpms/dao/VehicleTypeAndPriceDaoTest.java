/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
/**
 *
 * @author being
 */
package vpms.dao;

import org.junit.*;
import static org.junit.Assert.*;
import vpms.model.VehicleTypeAndPriceData;

import java.util.List;

public class VehicleTypeAndPriceDaoTest {
    private static final String TEST_VEHICLE_TYPE = "JUnitType";
    private static final String TEST_RESERVATION_PRICE = "10";
    private static final String TEST_REGULAR_PRICE = "20";
    private static final String TEST_DEMAND_PRICE = "30";
    private static final String TEST_EXTRA_CHARGE = "5";
    private static final String TEST_STATUS = "Active";

    private static int createdId = -1;

    private VehicleTypeAndPriceDao dao = new VehicleTypeAndPriceDao();

    @Test
    public void testAddVehicleTypeAndPrice() {
        VehicleTypeAndPriceData data = new VehicleTypeAndPriceData(
                0,
                TEST_VEHICLE_TYPE,
                TEST_RESERVATION_PRICE,
                TEST_REGULAR_PRICE,
                TEST_DEMAND_PRICE,
                TEST_EXTRA_CHARGE,
                TEST_STATUS
        );
        boolean result = dao.addVehicleTypeAndPrice(data);
        assertTrue("Should add vehicle type and price", result);

        // Find the inserted type for further tests
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        assertNotNull("List should not be null", list);
        VehicleTypeAndPriceData found = list.stream()
                .filter(v -> TEST_VEHICLE_TYPE.equals(v.getVehicleType()))
                .findFirst().orElse(null);
        assertNotNull("Inserted type should be found", found);
        createdId = found.getId();
        assertEquals(TEST_REGULAR_PRICE, found.getRegularPrice());
    }

    @Test
    public void testShowVehicleTypeAndPrices() {
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        assertNotNull("List should not be null", list);
        assertTrue("Should have at least one vehicle type", list.size() > 0);
    }

    @Test
    public void testUpdateVehicleTypeAndPrice() {
        if (createdId == -1) testAddVehicleTypeAndPrice();
        VehicleTypeAndPriceData data = dao.showVehicleTypeAndPrices().stream()
                .filter(v -> v.getId() == createdId)
                .findFirst().orElse(null);
        assertNotNull("Type to update should exist", data);

        data.setRegularPrice("99");
        data.setStatus("Inactive");
        boolean updated = dao.updateVehicleTypeAndPrice(data);
        assertTrue("Update should succeed", updated);

        VehicleTypeAndPriceData updatedData = null;
        try {
            updatedData = dao.findById(createdId);
        } catch (Exception e) {
            fail("findById should not throw");
        }
        assertNotNull(updatedData);
        assertEquals("99", updatedData.getRegularPrice());
        assertEquals("Inactive", updatedData.getStatus());
    }

    @Test
    public void testSearchVehicleTypes() {
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes(TEST_VEHICLE_TYPE);
        assertNotNull("Search result should not be null", list);
        assertTrue("Should find at least one matching type", list.stream()
                .anyMatch(v -> TEST_VEHICLE_TYPE.equals(v.getVehicleType())));
    }

    @Test
    public void testGetIdByVehicleType() {
        if (createdId == -1) testAddVehicleTypeAndPrice();
        int id = dao.getIdByVehicleType(TEST_VEHICLE_TYPE);
        assertEquals("ID by vehicle type name should match created ID", createdId, id);
    }

    @Test
    public void testGetVehicleTypeById() {
        if (createdId == -1) testAddVehicleTypeAndPrice();
        String type = dao.getVehicleTypeById(createdId);
        assertEquals("Vehicle type by ID should match", TEST_VEHICLE_TYPE, type);
    }

    @Test
    public void testGetAllVehicleTypeNames() {
        List<String> names = dao.getAllVehicleTypeNames();
        assertNotNull("Vehicle type names list should not be null", names);
        assertTrue("Should contain the test type", names.contains(TEST_VEHICLE_TYPE));
    }

    @Test
    public void testGetBasePriceForVehicleType() {
        double price = dao.getBasePriceForVehicleType(TEST_VEHICLE_TYPE);
        assertEquals(Double.parseDouble(TEST_REGULAR_PRICE), price, 0.001);
    }

    @Test
    public void testDeleteVehicleTypeAndPrice() {
        if (createdId == -1) testAddVehicleTypeAndPrice();
        boolean deleted = dao.deleteVehicleTypeAndPrice(createdId);
        assertTrue("Delete should succeed", deleted);

        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        boolean exists = list != null && list.stream().anyMatch(v -> v.getId() == createdId);
        assertFalse("Deleted type should not exist", exists);
    }
}
