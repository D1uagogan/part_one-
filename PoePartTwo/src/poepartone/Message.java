/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartone;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Message {

    ArrayList<String> messageIds = new ArrayList<>();
    ArrayList<String> recipentNumber = new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>(); 
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();
     
    Scanner input = new Scanner(System.in);
    Random random = new Random();

    int numMessageSent = 0;

    public void sendingMessages() {
        System.out.println("How many messages do you want to send?");
        
        // Safe input handling to prevent crashes
        int numberOfMessages = 0;
        try {
            numberOfMessages = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please restart message entry.");
            return;
        }

        for (int i = 1; i <= numberOfMessages; i++) {
            System.out.println("\n--- MESSAGE " + i + " ---");

            String messageID = generateMessageID();

            System.out.println("Enter recipient cell number (e.g., +27718693002 or 08575975889):");
            String recipient = input.nextLine();

            String recipentResult = checkRecipentCell(recipient);

            if (!recipentResult.equals("Valid number.")) {
                System.out.println(recipentResult);
                i--; // Retry this iteration
                continue;
            }

            System.out.println("Please enter your message (max 250 characters): ");
            String messageText = input.nextLine();

            if (messageText.length() > 250 || messageText.trim().isEmpty()) {
                System.out.println("Your message length should be between 1 and 250 characters.");
                i--; // Retry this iteration
                continue;
            }

            int messageNumber = messages.size() + 1;

            String messageHash = createMessageHash(messageID, messageNumber, messageText);

            String status = SentMessage();
            
            // If message was disregarded and deleted, restart loop iteration for this slot
            if (status.equals("DisregardedDeleted")) {
                i--;
                continue;
            }

            // Store valid message data in parallel lists
            recipentNumber.add(recipient);
            messages.add(messageText);
            msgHash.add(messageHash);
            messageIdsList.add(messageID);
            messageStatus.add(status);

            if (status.equals("Sent") || status.equals("Stored")) {
                if (status.equals("Sent")) {
                    numMessageSent++;
                }
                // Call JSON store method
                storeMessage(messageID, recipient, messageText, messageHash, status);
            }
        }
        
        printMessages();
        System.out.println("Total messages successfully sent: " + returnTotalMessages());
    }

    public int returnTotalMessages() {
        return numMessageSent;
    }

    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() <= 10;
    }

    public String generateMessageID() {
        String messageID;
        do {
            long number = 1000000000L + random.nextLong(9000000000L);
            messageID = String.valueOf(number);
        } while (messageIds.contains(messageID));

        messageIds.add(messageID);
        return messageID;
    }

    public String checkRecipentCell(String cellphone) {
        if (cellphone.matches(".*\\+27[0-9]{9}$") || cellphone.matches("^0[0-9]{9}$")) {
            return "Valid number.";
        } else {
            return "Invalid number. Please enter valid South African cellphone number.";
        }
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String firstTwoNumbers = messageID.substring(0, 2);
        String trimmedMessage = messageText.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = trimmedMessage.split("\\s+");
        
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        String hash = firstTwoNumbers + ";" + messageNumber + ";" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage() {
        System.out.println("\nChoose what you want to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to Send Later");
        System.out.print("Enter your choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid option. Defaulting to disregarded.");
            return "Disregarded";
        }

        switch (choice) {
            case 1 -> {
                System.out.println("Message successfully sent");
                return "Sent";
            }
            case 2 -> {
                System.out.println("Press 0 to delete the message.");
                int deleteChoice = 0;
                try {
                    deleteChoice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    deleteChoice = -1;
                }

                if (deleteChoice == 0) {
                    System.out.println("Message Deleted");
                    return "DisregardedDeleted";
                } else {
                    System.out.println("Invalid option for deletion.");
                    return "Disregarded";
                }
            }
            case 3 -> {
                System.out.println("Message successfully stored");
                return "Stored";
            }
            default -> {
                System.out.println("Invalid option");
                return "Disregarded";
            }
        }
    }

    // Required JSON storage method
    public void storeMessage(String id, String recipient, String text, String hash, String status) {
        String jsonEntry = String.format(
            "{\"messageId\":\"%s\", \"recipient\":\"%s\", \"message\":\"%s\", \"hash\":\"%s\", \"status\":\"%s\"}\n",
            id, recipient, text.replace("\"", "\\\""), hash, status
        );

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error saving message to JSON: " + e.getMessage());
        }
    }

    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();

        System.out.println("\n==== All Messages Sent Summary ====");

        for (int i = 0; i < messages.size(); i++) {
            String msgDetails = "\nMessage Number: " + (i + 1) +
                                "\nMessage ID: " + messageIdsList.get(i) +
                                "\nRecipient: " + recipentNumber.get(i) +
                                "\nMessage: " + messages.get(i) +
                                "\nMessage Hash: " + msgHash.get(i) +
                                "\nStatus: " + messageStatus.get(i) +
                                "\n-----------------------------------";
            
            System.out.println(msgDetails);
            allMessages.append(msgDetails).append("\n");
        }
        
        return allMessages.toString();
    }
}