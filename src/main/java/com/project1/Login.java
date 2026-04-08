package com.project1;

import java.util.regex.Pattern;

public class Login {
    // Private variables to store user data
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    // --- Validation Methods ---

    /**
     * Ensures username contains an underscore and is no more than 5 characters long.
     */
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Ensures password meets complexity rules:
     * 8+ characters, a capital letter, a number, and a special character.
     */
    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (password.length() < 8) return false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(String.valueOf(c)).find()) hasSpecial = true;
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Research-based regex for cell phone validation.
     * Criteria: International code (+) followed by no more than 10 characters.
     */
    public boolean checkCellPhoneNumber(String phoneNumber) {
        // Regex: starts with +, followed by 1 to 10 digits
        String regex = "^\\+\\d{1,12}$";
        return Pattern.matches(regex, phoneNumber);
    }

    // --- Registration Messaging ---

    public String registerUser(String username, String password, String firstName, String lastName, String phone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phone)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // Save data if all checks pass
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;

        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully added.";
    }

    // --- Login Logic ---

    public boolean loginUser(String username, String password) {
        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}