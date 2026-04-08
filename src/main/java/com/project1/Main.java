package com.project1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login authSystem = new Login();

        System.out.println("--- Registration ---");
        System.out.print("Enter First Name: ");
        String fName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lName = sc.nextLine();
        System.out.print("Enter Username: ");
        String user = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        System.out.print("Enter Phone (e.g. +27838968976): ");
        String phone = sc.nextLine();

        // Register
        String registrationResult = authSystem.registerUser(user, pass, fName, lName, phone);
        System.out.println("\n" + registrationResult);

        // Only proceed to login if registration was successful
        if (registrationResult.contains("successfully captured")) {
            System.out.println("\n--- Login ---");
            System.out.print("Enter Username: ");
            String loginUser = sc.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = sc.nextLine();

            boolean status = authSystem.loginUser(loginUser, loginPass);
            System.out.println(authSystem.returnLoginStatus(status));
        }
    }
}