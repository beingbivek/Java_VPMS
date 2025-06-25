/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package vpms.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author being
 */
import vpms.model.ActivityLog;

import java.util.List;

public class ActivityLogDaoTest {
    private static final int TEST_USER_ID = 101;
    private static final String TEST_ACTION = "JUnit Test Action";
    private static int createdLogId = -1;

    private ActivityLogDao dao = new ActivityLogDao();

    @BeforeClass
    public static void setupClass() {
        // Optionally: Clean up test logs if needed
    }

    @AfterClass
    public static void tearDownClass() {
        // Optionally: Clean up test logs if needed
    }

    @Test
    public void testLogActivity() {
        ActivityLog log = new ActivityLog(TEST_USER_ID, TEST_ACTION);
        boolean result = dao.logActivity(log);
        assertTrue("Should log activity successfully", result);

        // Find the log in the DB for further tests
        List<ActivityLog> logs = dao.searchActivities(TEST_ACTION);
        assertFalse("At least one log should match the test action", logs.isEmpty());
        ActivityLog found = logs.get(0);
        createdLogId = found.getLog_id();
        assertEquals(TEST_USER_ID, found.getUser_id());
        assertEquals(TEST_ACTION, found.getAction());
        assertNotNull(found.getTimestamp());
    }

    @Test
    public void testShowActivities() {
        List<ActivityLog> logs = dao.showActivities();
        assertNotNull(logs);
        assertTrue("Should have at least one activity log", logs.size() > 0);
    }

    @Test
    public void testSearchActivities() {
        List<ActivityLog> logs = dao.searchActivities(TEST_ACTION);
        assertNotNull(logs);
        assertTrue("Should find logs matching the test action", logs.stream().anyMatch(l -> TEST_ACTION.equals(l.getAction())));
    }

    @Test
    public void testFetchLast() {
        int N = 3;
        List<ActivityLog> logs = dao.fetchLast(N);
        assertNotNull(logs);
        assertTrue("Should fetch at most N logs", logs.size() <= N);
        // If there are logs, check that the most recent is present
        if (!logs.isEmpty()) {
            assertNotNull(logs.get(0).getTimestamp());
        }
    }
}

