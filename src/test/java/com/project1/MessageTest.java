package com.project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        // Build a string with 255 characters to trigger failure limits
        String oversizedMessage = "A".repeat(255);
        Message msg = new Message("+27718693002", oversizedMessage);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msg.checkMessageLength());
    }

    @Test
    public void testRecipientFormattingSuccess() {
        Message msg = new Message("", "Hello World");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFormattingFailure() {
        Message msg = new Message("08575975889", "Hello World");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageHashIsCorrect() {
        // Test case using the parameters and expectations matching page 15 requirements
        // ID input: "00" prefixed, message index "0", first word "Hi", last word "tonight?"
        Message msg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }

    @Test
    public void testMessageIDIsCreated() {
        Message msg = new Message("+27718693002", "Hi Keegan, did you receive the payment?");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testSentMessageStatusReturns() {
        Message msg = new Message("+27718693002", "Simple text message test");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }
}