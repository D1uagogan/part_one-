/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartthree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class messageBackup {
     ArrayList<String> sentMessages = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    HashSet<String> messageIds = new HashSet<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();
    ArrayList<String> disregardedMessages = new ArrayList<>();
    ArrayList<String> storedMessages = new ArrayList<>();
    ArrayList<String> senders = new ArrayList<>();

    Random random = new Random();
    Scanner input = new Scanner(System.in);

    int numMessageSent = 0;

    public void sendingMessages() {

        System.out.print("How many messages do you want to send? ");
        int numberOfMessages = input.nextInt();

        input.nextLine();

        for (int i = 1; i <= numberOfMessages; i++) {

            System.out.println("\n==============================");
            System.out.println("MESSAGE " + i);
            System.out.println("==============================");

            String messageID = generateMessageID();

            System.out.print("Enter recipient cell number: ");
            String recipient = input.nextLine();

            String recipientResult = checkRecipientCell(recipient);

            if (!recipientResult.equals("Valid number")) {
                System.out.println(recipientResult);
                i--;
                continue;
            }

            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            if (messageText.length() > 250 || messageText.trim().isEmpty()) {
                System.out.println("Please enter a message of 1 to 250 characters.");
                i--;
                continue;
            }

            int messageNumber = sentMessages.size() + 1;

            String messageHash = createMessageHash(messageID, messageNumber, messageText);

            msgHash.add(messageHash);
            sentMessages.add(messageText);
            messageIdsList.add(messageID);
            messageIds.add(messageID);
            recipientNumber.add(recipient);

            System.out.print("Enter sender name: ");
            String sender = input.nextLine();
            senders.add(sender);

            String status = SentMessage();

            messageStatus.add(status);

            if (status.equals("Sent")) {
                numMessageSent++;
            }
        }

        printMessages();

        System.out.println(
                "Total messages sent: " + returnTotalMessages()
        );
    }

    public int returnTotalMessages() {

        return numMessageSent;
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {

        String firstTwoNumbers = messageID.substring(0, 2);

        String cleanMessage = messageText.trim();

        String[] words = cleanMessage.split("\\s+");

        String firstWord = words[0];

        String lastWord = words[words.length - 1];

        String hash = firstTwoNumbers + ":" + messageNumber + ":" + firstWord + lastWord;

        return hash.toUpperCase();
    }

    public String generateMessageID() {

        String messageID;

        do {

            long number = 1000000000L + random.nextLong(9000000000L);

            messageID = String.valueOf(number);

        } while (messageIds.contains(messageID));

        return messageID;
    }

    public String checkRecipientCell(String cellphone) {

        if (cellphone.matches("^\\+27[0-9]{9}$") || cellphone.matches("^0[0-9]{9}$")) {

            return "Valid number";

        } else {

            return "Invalid number";
        }
    }

    public String SentMessage() {

        System.out.println("\nChoose what you want to do with this message!");

        System.out.println("1. Send");
        System.out.println("2. Disregard");
        System.out.println("3. Store");

        System.out.println("Enter your choice: ");
        int choice = input.nextInt();

        input.nextLine();

        switch (choice) {

            case 1 -> {
                System.out.println("Message successfully sent");
                return "Sent";
            }

            case 2 -> {

                System.out.println("Press 0 to delete the message");
                int deleteChoice = input.nextInt();

                input.nextLine();

                if (deleteChoice == 0) {

                    System.out.println("Message deleted");

                    int lastIndex = sentMessages.size() - 1;

                    String messageID = messageIdsList.get(lastIndex);
                    messageIds.remove(messageID);

                    recipientNumber.remove(lastIndex);
                    sentMessages.remove(lastIndex);
                    msgHash.remove(lastIndex);
                    messageIdsList.remove(lastIndex);
                    senders.remove(lastIndex);

                    return "Disregarded";

                } else {

                    System.out.println("Invalid option!");

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

    public String printMessages() {

        StringBuilder allMessages = new StringBuilder();

        System.out.println("\n===== All Messages =====");

        for (int i = 0; i < sentMessages.size(); i++) {

            int messageNumber = i + 1;

            allMessages.append("\nMessage Number: ").append(messageNumber);
            allMessages.append("\nMessage ID: ").append(messageIdsList.get(i));
            allMessages.append("\nRecipient: ").append(recipientNumber.get(i));
            allMessages.append("\nMessage: ").append(sentMessages.get(i));
            allMessages.append("\nMessage Hash: ").append(msgHash.get(i));
            allMessages.append("\nStatus: ").append(messageStatus.get(i));

            System.out.println("\n==============================");
            System.out.println("\nMessage Number: " + messageNumber);
            System.out.println("Message ID: " + messageIdsList.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
            System.out.println("Message: " + sentMessages.get(i));
            System.out.println("Message Hash: " + msgHash.get(i));
            System.out.println("Status: " + messageStatus.get(i));
        }

        return allMessages.toString();
    }
    public void messageManagementMenu(){
        
        while(true){
            System.out.println("\n");
            System.out.println("1. Show disregarded messages");
            System.out.println("2. Show all stored messages");
            System.out.println("3. Delete a message by Hash ID");
            System.out.println("4. Display full report");
            System.out.println("5. Search for a message");
            System.out.println("6. Display the longest message");
            System.out.println("7. Back to main menu");
            
            System.out.println("Choose an option(1-7): ");
            
            String option = input.nextLine();
            
            switch(option){
                case "1" -> showDisregardedMessages();
                case "2" -> showAllStoredMesages();
                case "3" -> deleteMessageByHash();
                case "4" -> displayFullReport();
                case "5" -> searchForMessageByHashId();
                case "6" -> findLongestMessgae();
                case "7" -> {
                    return; 
                }
                
                default -> System.out.println("Invalid choice.");
            }
            
        }
    }
    
    
    
   public void showDisregardedMessages(){
        
       if(disregardedMessages.isEmpty()){
           System.out.println("No deleted messages yet.");
           return;
       }
       
       System.out.println("\n");
       System.out.println("====== DELETED MESSAGES ======\n\n");
       
       for(int i = 0; i < disregardedMessages.size(); i++){
           System.out.println((i + 1) + "." + disregardedMessages.get(i));
       }
    }
   public void  showAllStoredMesages(){
       
       if(storedMessages.isEmpty()){
           System.out.println("No stored messages to display");
           return;
       }
       
       System.out.println();
       System.out.println("========== All Stored Messages ==========\n");
       
       for(int i = 0; i < storedMessages.size(); i++){
           System.out.println((i + 1) + "." + storedMessages.get(i));
           
       }
   }
   public void  deleteMessageByHash(){
       
       System.out.println("Enter Hash ID to delete: ");
       
       String hash = input.nextLine();
       
       int index = msgHash.indexOf(hash.trim());
       
       if(index != -1){
           disregardedMessages.add(sentMessages.get(index));
           messageIds.remove(messageIdsList.get(index));
           sentMessages.remove(index);
           senders.remove(index);
           recipientNumber.remove(index);
           messageIdsList.remove(index);
           msgHash.remove(index);
           messageStatus.remove(index);
           
           System.out.println("Message deleted and moved to disregarded");
           
       }else{
           System.out.println("Message not found");
       }
   }
   public void displayFullReport(){
       
       if(sentMessages.isEmpty()){
           System.out.println("No sent messages to display");
           return;
           
       }
       
       System.out.println();
       System.out.println("=========== Full Message Report ===========\n");
       
       System.out.printf("| %-5s | %-10s | %-15s | %-30s | %-10s | %-6s | %n", 
               "No. ",
               "Sender",
               "Recipient",
               "Message",
               "Message ID",
               "hash ID"
             );
       for(int i = 0; i < sentMessages.size(); i++){
           String message = sentMessages.get(i);
           
           if(message.length() > 20){
               message = message.substring(0, 17) + ". . .";
           }
           System.out.printf("| %-5d | %-10s | %-15s | %-30s | %-10s | %-6s | %n", 
               i + 1,
               senders.get(i),
               recipientNumber.get(i),
               message,
               messageIdsList.get(i),
               msgHash.get(i)
             );
       }
       System.out.println();
       System.out.println("Total sent messages: " + sentMessages.size());
       System.out.println("Total disregarded messages: " + disregardedMessages.size());
       System.out.println("Total stored messages: " + storedMessages.size());
       
   }
   public void searchForMessageByHashId(){
       
       if(sentMessages.isEmpty()){
           System.out.println("No sent messages to search");
           return;
       }
       System.out.println("Enter the HashID to search: ");
       String hashId = input.nextLine();
       
       if(hashId.trim().isEmpty()){
           System.out.println("Hash ID cannot be empty. ");
           return;
           
       }
      int index =  msgHash.indexOf(hashId);
      
      if(index == -1){
          System.out.println("No meessage exists with this Hash ID: hashId");
          return;
      }
       System.out.println();
       System.out.println("======== Message Details ========\n");
       System.out.println("Hash ID: " + msgHash.get(index));
       System.out.println("Message ID: " + messageIdsList.get(index));
       System.out.println("From: " + senders.get(index));
       System.out.println("To: " + recipientNumber.get(index));
       System.out.println("Message: " + sentMessages.get(index));
       System.out.println("Length: " + sentMessages.get(index).length() + " charaters long ");
   }
   public void findLongestMessgae(){
       if(sentMessages.isEmpty()){
           System.out.println("No sent message to analyze the longest");
           
           return;
       }
       int maxLength = -1;
       int index = -1;
       
       for(int i = 0; i < sentMessages.size(); i++){
           int currentLength = sentMessages.get(i).length();
           
           if(currentLength > maxLength){
               maxLength = currentLength;
               index = i;
           }
       }
       if(index != -1){
           System.out.println();
           System.out.println("======== LONGEST MESSAGE SENT ========\n\n");
           
           System.out.println("Length:" + maxLength + " characters long" );
           System.out.println("Hash ID: " + msgHash.get(index));
           System.out.println("Hash ID: " + messageIdsList.get(index));
           System.out.println("Hash ID: " + senders.get(index));
           System.out.println("Hash ID: " + recipientNumber.get(index));
           System.out.println("Hash ID: " + sentMessages.get(index));
      }
   }
}
