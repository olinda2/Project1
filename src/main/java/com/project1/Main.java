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

            // QuickChat Menu Loop
            if (isLoggedIn) {
                System.out.println("\nWelcome to QuickChat");

                // Part 3: load any previously stored messages from JSON
                Message.storedMessages = Message.loadStoredMessages();

                boolean appRunning = true;

                while (appRunning) {
                    System.out.println("\nChoose an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Your Choice: ");
                    String menuChoice = sc.nextLine();

                    switch (menuChoice) {
                        case "1":
                            handleSendMessages(sc, phone);
                            break;

                        case "2":
                            System.out.println("Coming Soon.");
                            break;

                        case "3":
                            System.out.println("Exiting Application. Goodbye!");
                            appRunning = false;
                            break;

                        case "4":
                            handleStoredMessagesMenu(sc);
                            break;

                        default:
                            System.out.println("Invalid input, please enter 1, 2, 3 or 4.");
                            break;
                    }
                }
            }
        }
        sc.close();
    }

    // -----------------------------------------------------------
    // Option 1: Send Messages (Part 2, unchanged logic, kept tidy)
    // -----------------------------------------------------------
    private static void handleSendMessages(Scanner sc, String senderPhone) {
        System.out.print("How many messages do you wish to enter? ");
        int count = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("\n- Message Entry " + (i + 1) + " -");
            System.out.print("Enter Recipient Number: ");
            String recipient = sc.nextLine();
            System.out.print("Enter Message Content: ");
            String content = sc.nextLine();

            Message tempMsg = new Message(recipient, content);
            tempMsg.setSenderNumber(senderPhone);

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
    }

    // -----------------------------------------------------------
    // Option 4: Stored Messages (Part 3, Task 2 a-f)
    // -----------------------------------------------------------
    private static void handleStoredMessagesMenu(Scanner sc) {
        boolean inStoredMenu = true;

        while (inStoredMenu) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message by Message ID");
            System.out.println("d) Search for messages by recipient");
            System.out.println("e) Delete a message using its Message Hash");
            System.out.println("f) Display full report of stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Your Choice: ");
            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("\n" + Message.displaySenderAndRecipient());
                    break;

                case "b":
                    System.out.println("\nLongest stored message:");
                    System.out.println(Message.displayLongestStoredMessage());
                    break;

                case "c":
                    System.out.print("Enter Message ID to search for: ");
                    String id = sc.nextLine().trim();
                    System.out.println("\n" + Message.searchByMessageID(id));
                    break;

                case "d":
                    System.out.print("Enter recipient number to search for: ");
                    String recipient = sc.nextLine().trim();
                    System.out.println("\n" + Message.searchByRecipient(recipient));
                    break;

                case "e":
                    System.out.print("Enter Message Hash of the message to delete: ");
                    String hash = sc.nextLine().trim();
                    System.out.println("\n" + Message.deleteByHash(hash));
                    break;

                case "f":
                    System.out.println("\n--- Stored Messages Report ---");
                    System.out.println(Message.displayReport());
                    break;

                case "g":
                    inStoredMenu = false;
                    break;

                default:
                    System.out.println("Invalid input, please enter a, b, c, d, e, f or g.");
                    break;
            }
        }
    }
}