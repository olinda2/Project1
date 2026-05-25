package com.project1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login authSystem = new Login();

        System.out.println("- Registration -");
        System.out.print("Enter First Name: ");
        String fName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lName = sc.nextLine();
        System.out.print("Enter Username: ");
        String user = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        // Register User
        String registrationResult = authSystem.registerUser(user, pass, fName, lName, phone);
        System.out.println("\n" + registrationResult);

        // Run Login Flow only if Registration passes
        if (registrationResult.contains("successfully captured") || registrationResult.contains("successfully added")) {
            System.out.println("\n- Login -");
            System.out.print("Enter Username: ");
            String loginUser = sc.nextLine();
            System.out.print("Enter Password: ");
            String loginPass = sc.nextLine();

            boolean isLoggedIn = authSystem.loginUser(loginUser, loginPass);
            System.out.println(authSystem.returnLoginStatus(isLoggedIn));

            // --- Menu Loop (QuickChat App Flow) ---
            if (isLoggedIn) {
                System.out.println("\nWelcome to QuickChat");
                boolean appRunning = true;

                while (appRunning) {
                    System.out.println("\nChoose an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Your Choice: ");
                    String menuChoice = sc.nextLine();

                    if (menuChoice.equals("1")) {
                        System.out.print("How many messages do you wish to enter? ");
                        int count = Integer.parseInt(sc.nextLine());

                        for (int i = 0; i < count; i++) {
                            System.out.println("\n- Message Entry " + (i + 1) + " -");
                            System.out.print("Enter Recipient Number: ");
                            String recipient = sc.nextLine();
                            System.out.print("Enter Message Content: ");
                            String content = sc.nextLine();

                            Message tempMsg = new Message(recipient, content);

                            // Run validations
                            String sizeStatus = tempMsg.checkMessageLength();
                            String cellStatus = tempMsg.checkRecipientCell();

                            System.out.println(cellStatus);
                            System.out.println(sizeStatus);

                            // Proceed if criteria are met
                            if (sizeStatus.equals("Message ready to send.") &&
                                    cellStatus.equals("Cell phone number successfully captured.")) {

                                System.out.println("Message ID generated: " + tempMsg.getMessageID());
                                System.out.println("Message Hash: " + tempMsg.getMessageHash());

                                System.out.println("\nOptions:");
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message");
                                System.out.print("Action choice: ");
                                int action = Integer.parseInt(sc.nextLine());

                                String actionResult = tempMsg.SentMessage(action);
                                System.out.println(actionResult);

                                if (action == 1 || action == 3) {
                                    System.out.println("\n- Captured Message Details -");
                                    System.out.println(tempMsg.printMessages());
                                }
                            }
                        }
                        System.out.println("\nTotal Messages Sent: " + Message.returnTotalMessagess());

                    } else if (menuChoice.equals("2")) {
                        System.out.println("Coming Soon.");
                    } else if (menuChoice.equals("3")) {
                        System.out.println("Exiting Application. Goodbye!");
                        appRunning = false;
                    } else {
                        System.out.println("Invalid input, please enter 1, 2, or 3.");
                    }
                }
            }
        }
        sc.close();
    }
}