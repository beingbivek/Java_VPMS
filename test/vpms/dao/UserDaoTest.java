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
import vpms.model.LoginRequest;
import vpms.model.ResetPasswordRequest;
import vpms.model.UserData;

import java.util.List;

public class UserDaoTest {
    private static final String TEST_NAME = "Test User";
    private static final String TEST_TYPE = "Staff";
    private static final String TEST_EMAIL = "testuser@vpms.com";
    private static final String TEST_PASSWORD = "TestPassword123!";
    private static final String TEST_PHONE = "1234567890";
    private static final String TEST_STATUS = "Active";
    private static final byte[] TEST_IMAGE = new byte[]{1,2,3,4};

    private static int createdUserId = -1;

    private UserDao dao = new UserDao();

    @BeforeClass
    public static void setupClass() {
        // Optionally: clean up test users if needed
    }

    @AfterClass
    public static void tearDownClass() {
        // Optionally: clean up test users if needed
    }

    @Test
    public void testRegisterUserNew() {
        UserData user = new UserData(
                TEST_NAME, TEST_TYPE, TEST_EMAIL, TEST_PASSWORD, TEST_PHONE, TEST_IMAGE, TEST_STATUS
        );
        boolean result = dao.registerUser(user);
        assertTrue("Should register new user", result);

        // Save ID for later tests
        UserData dbUser = dao.loginUser(new LoginRequest(TEST_EMAIL, TEST_PASSWORD));
        assertNotNull("User should be found after registration", dbUser);
        createdUserId = dbUser.getId();
    }

    @Test
    public void testRegisterUserDuplicate() {
        UserData user = new UserData(
                TEST_NAME, TEST_TYPE, TEST_EMAIL, TEST_PASSWORD, TEST_PHONE, TEST_IMAGE, TEST_STATUS
        );
        boolean result = dao.registerUser(user);
        assertFalse("Should not register duplicate email", result);
    }

    @Test
    public void testLoginUserCorrect() {
        UserData user = dao.loginUser(new LoginRequest(TEST_EMAIL, TEST_PASSWORD));
        assertNotNull("Login should succeed with correct credentials", user);
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_NAME, user.getName());
    }

    @Test
    public void testLoginUserWrongPassword() {
        UserData user = dao.loginUser(new LoginRequest(TEST_EMAIL, "wrongpassword"));
        assertNull("Login should fail with wrong password", user);
    }

    @Test
    public void testLoginUserWrongEmail() {
        UserData user = dao.loginUser(new LoginRequest("notfound@vpms.com", TEST_PASSWORD));
        assertNull("Login should fail with wrong email", user);
    }

    @Test
    public void testCheckEmailExists() {
        boolean exists = dao.checkEmail(TEST_EMAIL);
        assertTrue("Email should exist", exists);
    }

    @Test
    public void testCheckEmailNotExists() {
        boolean exists = dao.checkEmail("notfound@vpms.com");
        assertFalse("Email should not exist", exists);
    }

    @Test
    public void testShowUsers() {
        List<UserData> users = dao.showUsers();
        assertNotNull(users);
        assertTrue("Should have at least one user", users.size() > 0);
    }

    @Test
    public void testSearchUsers() {
        List<UserData> users = dao.searchUsers("%" + TEST_NAME + "%");
        assertNotNull(users);
        assertTrue(users.stream().anyMatch(u -> TEST_EMAIL.equals(u.getEmail())));
    }

    @Test
    public void testResetPassword() {
        String newPass = "NewPass123!";
        ResetPasswordRequest req = new ResetPasswordRequest(TEST_EMAIL, newPass);
        boolean ok = dao.resetPassword(req);
        assertTrue("Password reset should succeed", ok);
        UserData user = dao.loginUser(new LoginRequest(TEST_EMAIL, newPass));
        assertNotNull("Should be able to login with new password", user);

        // Reset back for other tests
        dao.resetPassword(new ResetPasswordRequest(TEST_EMAIL, TEST_PASSWORD));
    }

    @Test
    public void testGetUserFromId() {
        if (createdUserId == -1) {
            UserData user = dao.loginUser(new LoginRequest(TEST_EMAIL, TEST_PASSWORD));
            assertNotNull(user);
            createdUserId = user.getId();
        }
        UserData user = dao.getUserFromId(createdUserId);
        assertNotNull("Should find user by ID", user);
        assertEquals(TEST_EMAIL, user.getEmail());
    }

    @Test
    public void testUpdateUser() {
        UserData user = dao.loginUser(new LoginRequest(TEST_EMAIL, TEST_PASSWORD));
        assertNotNull(user);
        user.setName("Updated Name");
        boolean ok = dao.updateUser(user);
        assertTrue("Update should succeed", ok);
        UserData updated = dao.getUserFromId(user.getId());
        assertEquals("Updated Name", updated.getName());

        // Reset name
        user.setName(TEST_NAME);
        dao.updateUser(user);
    }

    @Test
    public void testGetTotalUserCount() {
        int count = dao.getTotalUserCount();
        assertTrue("Total user count should be >= 1", count >= 1);
    }

    @Test
    public void testGetTotalStaffCount() {
        int count = dao.getTotalStaffCount();
        assertTrue("Total staff count should be >= 0", count >= 0);
    }

    @Test
    public void testGetActiveStaffCount() {
        int count = dao.getActiveStaffCount();
        assertTrue("Active staff count should be >= 0", count >= 0);
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Register a throwaway user
        String email = "todelete@vpms.com";
        UserData user = new UserData("Del User", "Staff", email, "delpass", "0000", new byte[]{1}, "Active");
        dao.registerUser(user);
        UserData dbUser = dao.loginUser(new LoginRequest(email, "delpass"));
        assertNotNull(dbUser);
        boolean ok = dao.deleteUser(dbUser.getId());
        assertTrue("Delete should succeed", ok);
        assertNull("User should not be found after delete", dao.getUserFromId(dbUser.getId()));
    }
}
