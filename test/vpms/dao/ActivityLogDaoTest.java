/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package vpms.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vpms.model.ActivityLog;

/**
 *
 * @author being
 */
public class ActivityLogDaoTest {
    
    public ActivityLogDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLogActivity() {
        System.out.println("logActivity");
        ActivityLog log = null;
        ActivityLogDao instance = new ActivityLogDao();
        boolean expResult = false;
        boolean result = instance.logActivity(log);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testShowActivities() {
        System.out.println("showActivities");
        ActivityLogDao instance = new ActivityLogDao();
        List<ActivityLog> expResult = null;
        List<ActivityLog> result = instance.showActivities();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFetchLast() {
        System.out.println("fetchLast");
        int number = 0;
        ActivityLogDao instance = new ActivityLogDao();
        List<ActivityLog> expResult = null;
        List<ActivityLog> result = instance.fetchLast(number);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
