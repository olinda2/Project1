package com.project1;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipientNumber;
    private String messageDescription;
    private String messageHash;
    private String senderNumber; // Part 3 addition: who sent the message

    private static int totalMessagesSent = 0;

    // ---------------------------------------------------------------
    // Part 3 arrays (Task 1)
    // ---------------------------------------------------------------
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();

    private static final String JSON_FILE = "stored_messages.json";

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    // Used for normal app flow (auto-generates ID + hash)
    public Message(String recipientNumber, String messageDescription) {
        this.recipientNumber = recipientNumber;
        this.messageDescription = messageDescription;
        this.messageID = generateRandomMessageID();
        this.numMessagesSent = totalMessagesSent;
        this.messageHash = createMessageHash();
        this.senderNumber = "Unknown";
    }

    // Used for unit testing and for re-loading messages from JSON
    public Message(String messageID, int numMessagesSent, String recipientNumber, String messageDescription) {
        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipientNumber = recipientNumber;
        this.messageDescription = messageDescription;
        this.messageHash = createMessageHash();
        this.senderNumber = "Unknown";
    }

    // ---------------------------------------------------------------
    // Assessment Methods (Part 2 - unchanged)
    // ---------------------------------------------------------------

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
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

    // ---------------------------------------------------------------
    // SentMessage - now populates the Part 3 arrays
    // ---------------------------------------------------------------
    public String SentMessage(int choice) {
        switch (choice) {
            case 1: // Send
                totalMessagesSent++;
                sentMessages.add(this.messageDescription);
                messageIDs.add(this.messageID);
                messageHashes.add(this.messageHash);
                return "Message successfully sent.";

            case 2: // Disregard
                disregardedMessages.add(this.messageDescription);
                messageIDs.add(this.messageID);
                messageHashes.add(this.messageHash);
                return "Message successfully disregarded.";

            case 3: // Store
                totalMessagesSent++;
                storeMessage();
                messageIDs.add(this.messageID);
                messageHashes.add(this.messageHash);
                return "Message successfully stored.";

            default:
                return "Invalid selection.";
        }
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

    // ---------------------------------------------------------------
    // JSON Storage (Part 3 - hand-written, no external libraries)
    // ---------------------------------------------------------------

    /**
     * Adds this message to stored_messages.json and refreshes the
     * in-memory storedMessages array.
     */
    public void storeMessage() {
        ArrayList<Message> existing = loadStoredMessages();
        existing.add(this);
        writeStoredMessages(existing);
        storedMessages = existing;
    }

    /**
     * Reads stored_messages.json from disk and converts it into an
     * ArrayList of Message objects. If the file does not exist yet,
     * an empty list is returned.
     */
    public static ArrayList<Message> loadStoredMessages() {
        ArrayList<Message> list = new ArrayList<>();
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            return list;
        }

        try {
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            String json = content.toString();

            // Find every {...} object in the JSON array
            Matcher blockMatcher = Pattern.compile("\\{[^{}]*\\}", Pattern.DOTALL).matcher(json);
            while (blockMatcher.find()) {
                String obj = blockMatcher.group();

                String id = extractStringValue(obj, "messageID");
                String numStr = extractNumberValue(obj, "numMessagesSent");
                String recipient = extractStringValue(obj, "recipientNumber");
                String desc = extractStringValue(obj, "messageDescription").replace("\\\"", "\"");
                String sender = extractStringValue(obj, "senderNumber");

                int num = numStr.isEmpty() ? 0 : Integer.parseInt(numStr);

                Message m = new Message(id, num, recipient, desc);
                if (!sender.isEmpty()) {
                    m.senderNumber = sender;
                }
                list.add(m);
            }
        } catch (IOException e) {
            System.out.println("Error reading stored messages: " + e.getMessage());
        }
        return list;
    }

    /**
     * Rewrites stored_messages.json from scratch using the given list.
     */
    private static void writeStoredMessages(ArrayList<Message> messages) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(m.messageID).append("\",\n");
            json.append("    \"numMessagesSent\": ").append(m.numMessagesSent).append(",\n");
            json.append("    \"recipientNumber\": \"").append(m.recipientNumber).append("\",\n");
            json.append("    \"senderNumber\": \"").append(m.senderNumber == null ? "Unknown" : m.senderNumber).append("\",\n");
            json.append("    \"messageDescription\": \"").append(m.messageDescription.replace("\"", "\\\"")).append("\",\n");
            json.append("    \"messageHash\": \"").append(m.messageHash).append("\"\n");
            json.append("  }");
            if (i < messages.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");

        try (FileWriter fileWriter = new FileWriter(JSON_FILE)) {
            fileWriter.write(json.toString());
        } catch (IOException e) {
            System.out.println("Error saving JSON message details: " + e.getMessage());
        }
    }

    // Extracts a "key": "value" string field
    private static String extractStringValue(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"");
        Matcher m = p.matcher(obj);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    // Extracts a "key": number field
    private static String extractNumberValue(String obj, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)");
        Matcher m = p.matcher(obj);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    // ---------------------------------------------------------------
    // Part 3, Task 2 - "Stored Messages" menu functionality
    // ---------------------------------------------------------------

    /** 2a) Display the sender and recipient of all stored messages. */
    public static String displaySenderAndRecipient() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(m.senderNumber)
                    .append(" | Recipient: ").append(m.recipientNumber).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * General-purpose helper: returns the longest message from a list of
     * message texts. Used both by displayLongestStoredMessage() and by
     * the unit tests.
     */
    public static String getLongestMessage(ArrayList<String> messages) {
        if (messages == null || messages.isEmpty()) {
            return "";
        }
        String longest = messages.get(0);
        for (String msg : messages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    /** 2b) Display the longest stored message. */
    public static String displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        ArrayList<String> descriptions = new ArrayList<>();
        for (Message m : storedMessages) {
            descriptions.add(m.messageDescription);
        }
        return getLongestMessage(descriptions);
    }

    /** 2c) Search for a message ID and display the corresponding recipient and message. */
    public static String searchByMessageID(String id) {
        for (Message m : storedMessages) {
            if (m.messageID.equals(id)) {
                return "Recipient: " + m.recipientNumber + "\nMessage: " + m.messageDescription;
            }
        }
        return "No stored message found with Message ID: " + id;
    }

    /** 2d) Search for all the messages stored for a particular recipient. */
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            if (m.recipientNumber.equals(recipient)) {
                sb.append(m.messageDescription).append("\n");
            }
        }
        if (sb.length() == 0) {
            return "No stored messages found for recipient: " + recipient;
        }
        return sb.toString().trim();
    }

    /** 2e) Delete a message using the message hash. */
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.messageHash.equals(hash)) {
                String text = m.messageDescription;
                storedMessages.remove(i);
                writeStoredMessages(storedMessages);
                return "Message \"" + text + "\" successfully deleted.";
            }
        }
        return "No stored message found with hash: " + hash;
    }

    /** 2f) Display a report that lists the full details of all the stored messages. */
    public static String displayReport() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages to report.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipientNumber).append("\n");
            sb.append("Message: ").append(m.messageDescription).append("\n");
            sb.append("--------------------------------\n");
        }
        return sb.toString().trim();
    }

    // ---------------------------------------------------------------
    // Misc helpers
    // ---------------------------------------------------------------

    private String generateRandomMessageID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getSenderNumber() { return senderNumber; }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageDescription() { return messageDescription; }
}