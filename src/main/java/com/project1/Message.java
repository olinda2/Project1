package com.project1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipientNumber;
    private String messageDescription;
    private String messageHash;

    private static int totalMessagesSent = 0;

    public Message(String recipientNumber, String messageDescription) {
        this.recipientNumber = recipientNumber;
        this.messageDescription = messageDescription;
        this.messageID = generateRandomMessageID();
        this.numMessagesSent = totalMessagesSent;
        this.messageHash = createMessageHash();
    }

    public Message(String messageID, int numMessagesSent, String recipientNumber, String messageDescription) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipientNumber = recipientNumber;
        this.messageDescription = messageDescription;
        this.messageHash = createMessageHash();
    }

    // Assessment Methods

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        // Updated regex to allow up to 11 digits
        String regex = "^\\+\\d{1,11}$";
        if (this.recipientNumber != null && this.recipientNumber.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (this.messageDescription.length() <= 250) {
            return "Message ready to send.";
        } else {
            int overage = this.messageDescription.length() - 250;
            return "Message exceeds 250 characters by " + overage + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messageDescription == null || this.messageDescription.isEmpty()) {
            return "00:0:INVALID";
        }

        String firstTwoID = this.messageID.substring(0, 2);
        String[] words = this.messageDescription.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");

        String hash = firstTwoID + ":" + this.numMessagesSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            totalMessagesSent++;
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid selection.";
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipientNumber + "\n" +
                "Message: " + messageDescription;
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // JSON Storage

    public void storeMessage() {
        String json = "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"numMessagesSent\": " + numMessagesSent + ",\n" +
                "  \"recipientNumber\": \"" + recipientNumber + "\",\n" +
                "  \"messageDescription\": \"" + messageDescription.replace("\"", "\\\"") + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\"\n" +
                "}";

        try (FileWriter fileWriter = new FileWriter("messages.json", true)) {
            fileWriter.write(json + ",\n");
        } catch (IOException e) {
            System.out.println("Error saving JSON message details: " + e.getMessage());
        }
    }

    private String generateRandomMessageID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}