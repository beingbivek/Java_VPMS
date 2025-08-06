package vpms.dao;

import java.util.List;
import vpms.model.LoginRequest;
import vpms.model.ResetPasswordRequest;
import vpms.model.UserData;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDaoTest {
    private final String name     = "JUnit User";
    private final String type     = "Staff";
    private final String email    = "junittestuser@vpms.com";
    private final String password = "testPassword!";
    private final String phone    = "9800000000";
    private final String status   = "Active";
    private final byte[] image    = new byte[] { 1,2,3 };

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerWithNewDetails() {
        UserData user = new UserData(name, type, email, password, phone, image, status);
        UserDao dao = new UserDao();
        boolean result = dao.registerUser(user);
        Assert.assertTrue("Register should be successful for new user", result);
    }

    @Test
    public void registerWithExistingDetails() {
        UserData user = new UserData(name, type, email, password, phone, image, status);
        UserDao dao = new UserDao();
        boolean result = dao.registerUser(user);
        Assert.assertFalse("Register should fail for duplicate email", result);
    }

    @Test
    public void loginWithCorrectCredentials() {
        LoginRequest req = new LoginRequest(email, password);
        UserDao dao = new UserDao();
        UserData user = dao.loginUser(req);
        Assert.assertNotNull("User should be returned for valid credentials", user);
        Assert.assertEquals("Name should match", name, user.getName());
    }

    @Test
    public void loginWithWrongCredentials() {
        LoginRequest req = new LoginRequest(email, "wrongPassword123!");
        UserDao dao = new UserDao();
        UserData user = dao.loginUser(req);
        Assert.assertNull("User should be null for wrong password", user);
    }

    @Test
    public void checkEmailExists() {
        UserDao dao = new UserDao();
        Assert.assertTrue("Email check should return true for existing email", dao.checkEmail(email));
    }

    @Test
    public void checkEmailDoesNotExist() {
        UserDao dao = new UserDao();
        Assert.assertFalse("Email check should return false for non-existing email", dao.checkEmail("notarealemai1@nomail.com"));
    }

    @Test
    public void resetPasswordAndLogin() {
        String newPassword = "newJUnitPassword123!";
        ResetPasswordRequest reset = new ResetPasswordRequest(email, newPassword);
        UserDao dao = new UserDao();
        Assert.assertTrue("Password reset should succeed", dao.resetPassword(reset));

        // Confirm the new password works for login
        LoginRequest loginWithNew = new LoginRequest(email, newPassword);
        Assert.assertNotNull("User should be able to login with new password", dao.loginUser(loginWithNew));

        // Restore original password for other tests
        dao.resetPassword(new ResetPasswordRequest(email, password));
    }

    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        UserData userData = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.registerUser(userData);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        LoginRequest req = null;
        UserDao instance = new UserDao();
        UserData expResult = null;
        UserData result = instance.loginUser(req);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testShowUsers() {
        System.out.println("showUsers");
        UserDao instance = new UserDao();
        List<UserData> expResult = null;
        List<UserData> result = instance.showUsers();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSearchUsers() {
        System.out.println("searchUsers");
        String data = "";
        UserDao instance = new UserDao();
        List<UserData> expResult = null;
        List<UserData> result = instance.searchUsers(data);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCheckEmail() {
        System.out.println("checkEmail");
        String email = "";
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testResetPassword() {
        System.out.println("resetPassword");
        ResetPasswordRequest resetReq = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.resetPassword(resetReq);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUserFromId() {
        System.out.println("getUserFromId");
        int id = 0;
        UserDao instance = new UserDao();
        UserData expResult = null;
        UserData result = instance.getUserFromId(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        UserData userData = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.updateUser(userData);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        int id = 0;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.deleteUser(id);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTotalUserCount() {
        System.out.println("getTotalUserCount");
        UserDao instance = new UserDao();
        int expResult = 0;
        int result = instance.getTotalUserCount();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTotalStaffCount() {
        System.out.println("getTotalStaffCount");
        UserDao instance = new UserDao();
        int expResult = 0;
        int result = instance.getTotalStaffCount();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetActiveStaffCount() {
        System.out.println("getActiveStaffCount");
        UserDao instance = new UserDao();
        int expResult = 0;
        int result = instance.getActiveStaffCount();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
