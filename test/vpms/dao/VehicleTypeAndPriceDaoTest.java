/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package vpms.dao;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import vpms.model.VehicleTypeAndPriceData;

public class VehicleTypeAndPriceDaoTest {
    VehicleTypeAndPriceDao dao;

    @Before
    public void setUp() {
        dao = new VehicleTypeAndPriceDao();
        // Optionally clean or prepare your test db here
    }

    @After
    public void tearDown() {
        // Optionally clean up inserted test data
    }

    @Test
    public void testAddVehicleTypeAndPrice() {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "TestType", "100", "200", "300", "50", "Active");
        boolean result = dao.addVehicleTypeAndPrice(vehicle);
        assertTrue(result);
        // Clean up (optional)
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("TestType");
        if (list != null && !list.isEmpty()) {
            dao.deleteVehicleTypeAndPrice(list.get(0).getId());
        }
    }

    @Test
    public void testShowVehicleTypeAndPrices() {
        List<VehicleTypeAndPriceData> list = dao.showVehicleTypeAndPrices();
        assertNotNull(list);
        // Possibly add specific assertions if your DB has predefined content
    }

    @Test
    public void testUpdateVehicleTypeAndPrice() {
        // Insert a record first
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "UpdatableType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("UpdatableType");
        assertNotNull(list);
        VehicleTypeAndPriceData toUpdate = list.get(0);
        toUpdate.setRegularPrice("250");
        boolean updated = dao.updateVehicleTypeAndPrice(toUpdate);
        assertTrue(updated);

        // Clean up
        dao.deleteVehicleTypeAndPrice(toUpdate.getId());
    }

    @Test
    public void testDeleteVehicleTypeAndPrice() {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "DeleteType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("DeleteType");
        assertNotNull(list);
        int id = list.get(0).getId();
        boolean deleted = dao.deleteVehicleTypeAndPrice(id);
        assertTrue(deleted);
    }

    @Test
    public void testSearchVehicleTypes() {
        // Insert dummy data (if necessary)
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "SearchType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List<VehicleTypeAndPriceData> found = dao.searchVehicleTypes("SearchType");
        assertNotNull(found);
        assertFalse(found.isEmpty());
        // Clean up
        dao.deleteVehicleTypeAndPrice(found.get(0).getId());
    }

    @Test
    public void testFindById() throws Exception {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "FindByIdType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("FindByIdType");
        assertNotNull(list);
        int id = list.get(0).getId();
        VehicleTypeAndPriceData result = dao.findById(id);
        assertNotNull(result);
        assertEquals("FindByIdType", result.getVehicleType());
        // Clean up
        dao.deleteVehicleTypeAndPrice(id);
    }

    @Test
    public void testGetVehicleTypeByNumber() {
        // This test assumes the vehicle number and association is already in DB.
        // You may need to insert test data into `vehicles` and `vehicle_type_and_price`.
        String fakeVehicleNumber = "TEST-123";
        String type = dao.getVehicleTypeByNumber(fakeVehicleNumber);
        // assert result as per your data, otherwise:
        assertNotNull(type);
    }

    @Test
    public void testGetBasePriceForVehicleType() {
        // Insert dummy
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "BasePriceType", "100", "999", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        double price = dao.getBasePriceForVehicleType("BasePriceType");
        assertEquals(999, price, 0.01);
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("BasePriceType");
        dao.deleteVehicleTypeAndPrice(list.get(0).getId());
    }

    @Test
    public void testGetIdByVehicleType() {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "GetIdType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        int id = dao.getIdByVehicleType("GetIdType");
        assertTrue(id > 0);
        dao.deleteVehicleTypeAndPrice(id);
    }

    @Test
    public void testGetVehicleTypeById() {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "GetByIdType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("GetByIdType");
        int id = list.get(0).getId();
        String type = dao.getVehicleTypeById(id);
        assertEquals("GetByIdType", type);
        dao.deleteVehicleTypeAndPrice(id);
    }

    @Test
    public void testGetAllVehicleTypeNames() {
        VehicleTypeAndPriceData vehicle = new VehicleTypeAndPriceData(
                0, "ComboBoxType", "100", "200", "300", "50", "Active");
        dao.addVehicleTypeAndPrice(vehicle);
        List names = dao.getAllVehicleTypeNames();
        assertNotNull(names);
        assertTrue(names.contains("ComboBoxType"));
        // Clean up
        List<VehicleTypeAndPriceData> list = dao.searchVehicleTypes("ComboBoxType");
        dao.deleteVehicleTypeAndPrice(list.get(0).getId());
    }
}
