package com.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Part 3, Task 4.
 *
 * Test data used (from the assignment brief):
 *
 * Message 1: Recipient +27834557896, "Did you get the cake?",            Sent
 * Message 2: Recipient +27838884567,  "Where are you? You are late! "
 *                                      "I have asked you to be on time.", Stored
 * Message 3: Recipient +27834484567, "Yohoooo, I am at your gate.",       Disregard
 * Message 4: Developer 0838884567,   "It is dinner time !",               Sent
 * Message 5: Recipient +27838884567, "Ok, I am leaving without you.",     Stored
 */
class MessageTest {

    @BeforeEach
    void setUp() {
        // Make sure every test starts with a clean slate
        Message.sentMessages.clear();
        Message.disregardedMessages.clear();
        Message.messageHashes.clear();
        Message.messageIDs.clear();
        Message.storedMessages.clear();
    }

    // -----------------------------------------------------------
    // Test 1: Sent Messages array correctly populated
    // -----------------------------------------------------------
    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        Message msg1 = new Message("1000000001", 0, "+27834557896", "Did you get the cake?");
        Message msg4 = new Message("0838884567", 1, "0838884567", "It is dinner time !");

        msg1.SentMessage(1); // Sent
        msg4.SentMessage(1); // Sent

        assertEquals(2, Message.sentMessages.size());
        assertTrue(Message.sentMessages.contains("Did you get the cake?"));
        assertTrue(Message.sentMessages.contains("It is dinner time !"));
    }

    // -----------------------------------------------------------
    // Test 2: Display the longest Message
    // -----------------------------------------------------------
    @Test
    void testDisplayLongestMessage() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Did you get the cake?");
        messages.add("Where are you? You are late! I have asked you to be on time.");
        messages.add("Yohoooo, I am at your gate.");
        messages.add("It is dinner time !");

        String longest = Message.getLongestMessage(messages);

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // -----------------------------------------------------------
    // Test 3: Search for messageID
    // -----------------------------------------------------------
    @Test
    void testSearchForMessageID() {
        Message msg4 = new Message("0838884567", 0, "0838884567", "It is dinner time !");
        Message.storedMessages.add(msg4);

        String result = Message.searchByMessageID("0838884567");

        assertTrue(result.contains("It is dinner time !"));
        assertTrue(result.contains("0838884567"));
    }

    // -----------------------------------------------------------
    // Test 4: Search all the messages sent or stored for a recipient
    // -----------------------------------------------------------
    @Test
    void testSearchAllMessagesForRecipient() {
        Message msg2 = new Message("2000000002", 0, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        Message msg5 = new Message("5000000005", 1, "+27838884567",
                "Ok, I am leaving without you.");

        Message.storedMessages.add(msg2);
        Message.storedMessages.add(msg5);

        String result = Message.searchByRecipient("+27838884567");

        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    // -----------------------------------------------------------
    // Test 5: Delete a message using a message hash
    // -----------------------------------------------------------
    @Test
    void testDeleteMessageUsingHash() {
        Message msg2 = new Message("2000000002", 0, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        Message.storedMessages.add(msg2);

        String hash = msg2.getMessageHash();
        String result = Message.deleteByHash(hash);

        assertTrue(result.contains("successfully deleted"));
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertEquals(0, Message.storedMessages.size());
    }

    // -----------------------------------------------------------
    // Test 6: Display Report
    // -----------------------------------------------------------
    @Test
    void testDisplayReport() {
        Message msg1 = new Message("1000000001", 0, "+27834557896", "Did you get the cake?");
        Message msg2 = new Message("2000000002", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");

        Message.storedMessages.add(msg1);
        Message.storedMessages.add(msg2);

        String report = Message.displayReport();

        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("Where are you? You are late! I have asked you to be on time."));
    }
}