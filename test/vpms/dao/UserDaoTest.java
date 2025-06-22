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
import vpms.model.LoginRequest;
import vpms.model.ResetPasswordRequest;
import vpms.model.UserData;

/**
 *
 * @author being
 */
public class UserDaoTest {
    
    public UserDaoTest() {
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
