package com.project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        // Test Data: Ch&&sec@ke99!
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        // Test Data: password
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneNumberCorrectlyFormatted() {
        // Test Data: +27838968976
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testPhoneNumberIncorrectlyFormatted() {
        // Test Data: 08966553
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccess() {
        login.registerUser("ky_1", "Ch&&sec@ke99!", "John", "Doe", "+271234567");
        assertTrue(login.loginUser("ky_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("ky_1", "Ch&&sec@ke99!", "John", "Doe", "+271234567");
        assertFalse(login.loginUser("wrong", "password"));
    }
}