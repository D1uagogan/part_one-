/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepartthree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Student
 */
public class Message {
    ArrayList<String> sentMessages = new ArrayList<>();
    ArrayList<String> messageIdsList = new ArrayList<>();
    HashSet<String> messageIds = new HashSet<>();
    ArrayList<String> msgHash = new ArrayList<>();
    ArrayList<String> recipientNumber = new ArrayList<>();
    ArrayList<String> messageStatus = new ArrayList<>();
    ArrayList<String> disregardedMessages = new ArrayList<>();
    ArrayList<String> storedMessages = new ArrayList<>();
    ArrayList<String> senders = new ArrayList<>();

    String firstName = "";
    String surname = "";

    Random random = new Random();
   

    int numMessageSent = 0;

    public void setUserDate(String fName, String sName) {
        this.firstName = fName;
        
        this.surname = sName;
        JOptionPane.showMessageDialog(null,"Welcome " + firstName + " " + surname + " to Quick Chat" );
        
    }

    public void sendingMessages() {
       
        int numberOfMessages = 0; 
        
        while(true){
            String input = JOptionPane.showInputDialog(null,"How many messages do you want to send?",
                   "Send Messages",
                   JOptionPane.QUESTION_MESSAGE);
            
            if(input == null){
                return;
                
            }
            try{
                numberOfMessages = Integer.parseInt(input);
                if(numberOfMessages < 1){
                    JOptionPane.showMessageDialog(null, "Please enter a number greater than 0",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                    
                }
                
                break;
                
            }catch(NumberFormatException e){
                
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid number",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }  
            
        }
       

        for (int i = 1; i <= numberOfMessages; i++) {
            
            String messageID = generateMessageID();
            
            String recipient;
            
            while(true){
                recipient = JOptionPane.showInputDialog(null,
                        "Message" + i + "of" + numberOfMessages 
                                +"\n\nEnter recipient cell number:", 
                        "Recipient", JOptionPane.QUESTION_MESSAGE);
                if(recipient == null){
                    return;
                }
                String recipientResult = checkRecipientCell(recipient);
                if(recipientResult.equals("Valid number")){
                    recipient = recipient.trim();
                    break;
                }
                JOptionPane.showMessageDialog(null,
                        recipientResult,
                        "Invalid number", 
                        JOptionPane.ERROR_MESSAGE);
            }
            
            String messageText;
            while(true){
                
                messageText = JOptionPane.showInputDialog(null,
                        "Enter your message(1 to 250 characters long):",
                        "Message", JOptionPane.QUESTION_MESSAGE);
                
                if(messageText == null){
                    return;
                }
                if(messageText.length() > 250 || messageText.trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null,
                            "Please enter a message of 1 - 250 charcters long", 
                            "Invalid message", JOptionPane.ERROR_MESSAGE);
                    
                }else{
                    break;
                }
            }
            
            int messageNumber = sentMessages.size() + 1;
            
            String messageHash =  createMessageHash(messageID, messageNumber, messageText);
            
            msgHash.add(messageHash);
            sentMessages.add(messageText);
            messageIdsList.add(messageID);
            recipientNumber.add(recipient);
            
            String sender = JOptionPane.showInputDialog(null, " Enter sender name:", "Sender", JOptionPane.QUESTION_MESSAGE);
            
            if(sender == null || sender.trim().isEmpty()){
                sender = "unknown";
              }
            senders.add(sender.trim());
            
            String status = SentMessage();
            
            messageStatus.add(status);
            
            if(status.equals("sent")){
                numMessageSent++;
            }else if(status.equals("Disreagarded")){
                
            }else if(status.equals("Stored")){
                
            }

        }

        printMessages();
        JOptionPane.showMessageDialog(null,
                "Total messages sent: " 
                        + returnTotalMessages(), 
                "Total", JOptionPane.INFORMATION_MESSAGE );
       
    }
      public int returnTotalMessages(){
          return numMessageSent;
      }
      
     public String SentMessage(){
         Object[] options = {"Send", "Disregard", "Store"};
         
        int choice =  JOptionPane.showOptionDialog(null, 
                "Choose what you want to do with this message!",
                "Message Options",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
        switch(choice){
            case 0 -> {
                JOptionPane.showMessageDialog(null, 
                        "Message sent successfully",
                        "Sent", JOptionPane.INFORMATION_MESSAGE);
                return "sent";
            }
            case 1 -> {
                int deleteChoice = JOptionPane.showConfirmDialog(null, 
                        "Do you want to delete this message?", 
                        "Disregard Message", JOptionPane.YES_NO_OPTION);
                
                if(deleteChoice == JOptionPane.YES_OPTION){
                      JOptionPane.showMessageDialog(null, 
                              "Message Deleted", "Deleted", 
                              JOptionPane.INFORMATION_MESSAGE);
                      int lastIndex = sentMessages.size() - 1;
                      String messageID = messageIdsList.get(lastIndex);
                      
                      disregardedMessages.add(messageID);
                      
                      messageIds.remove(messageID);
                      recipientNumber.remove(lastIndex);
                      sentMessages.remove(lastIndex);
                      msgHash.remove(lastIndex);
                      senders.remove(lastIndex);
                      messageIdsList.remove(lastIndex);
                      
                      return "Disregarded";
                 } else{
                       JOptionPane.showMessageDialog(null,"Invalid option!");
                         
                         return "Disregarded";
               }
            }
            case 2 ->  {
               JOptionPane.showMessageDialog(null, "Message successfully stored");
            
            return "stored";
           
          }
        default -> {
        JOptionPane.showMessageDialog(null, "Invalid option");
        
        return "disregarded";
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
            System.out.println("Message Number: " + messageNumber);
            System.out.println("Message ID: " + messageIdsList.get(i));
            System.out.println("Recipient: " + recipientNumber.get(i));
            System.out.println("Message: " + sentMessages.get(i));
            System.out.println("Message Hash: " + msgHash.get(i));
            System.out.println("Status: " + messageStatus.get(i));
        }

        return allMessages.toString();
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

    

    public void messageManagementMenu() {
       

        while (true) {
            
            String menu = """ 
                          ===== Select any of the options below =====
                                1. Show disregarded messages
                                2. Show all stored messages
                                3. Delete a message by Hash ID
                                4. Display full report
                                5. Search for a message
                                6. Display the longest message
                                7. Back to main menu Select option: """; 
            
            String input = JOptionPane.showInputDialog(null, menu, "menu",JOptionPane.QUESTION_MESSAGE);
            int option = 0;
            
            if(input == null){
                    option = 7;
                    
                }else{
                    try{
                        option = Integer.parseInt(input);
                        switch (option) {
                    case 1 ->
                        showDisregardedMessages();
                    case 2 ->
                        showAllStoredMesages();
                    case 3 ->
                         deleteMessageByHash();
                    case 4 ->
                        displayFullReport();
                    case 5 -> 
                        searchForMessageByHashId();
                    case 6 ->
                        findLongestMessage();
                    case 7 -> {
                        return;
                    }
                    default ->
                        JOptionPane.showMessageDialog(null,"Invalid option!!", "Error",JOptionPane.ERROR_MESSAGE);
                }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Please enter a vaid number", "Input Error", JOptionPane.WARNING_MESSAGE);
                            
                        option = 0;
                        
                    }
                }
            }  
        }

    public void showDisregardedMessages() {
        if (disregardedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No deleted messages yet.", "Disregarded messages", JOptionPane.INFORMATION_MESSAGE);
            
            return;
        }
        String display = "====== DELETED MESSAGES ======\n";  
        
        for (int i = 0; i < disregardedMessages.size(); i++) {
           display += (i + 1) + "." +disregardedMessages.get(i) + "\n";
           } 
            JOptionPane.showMessageDialog(null, display);
            System.out.println(display);
        
    }

    public void showAllStoredMesages() {
        if (storedMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No stored messages to display", "Stored messages", JOptionPane.INFORMATION_MESSAGE);
            
            return;
        }
        String display = "====== All Stored Messages ======\n";
       
        for (int i = 0; i < storedMessages.size(); i++) {
             display += (i + 1) + "." +storedMessages.get(i) + "\n";
          }  
            JOptionPane.showMessageDialog(null, display);
            System.out.println(display);
        
    }

    public void deleteMessageByHash() {
       String hash = JOptionPane.showInputDialog(null,"Enter Hash ID to delete: ", "Message by hash", JOptionPane.QUESTION_MESSAGE );
        if(hash == null){
            return;
        }
        
        int index = msgHash.indexOf(hash.trim());
        
        if (index != -1) {
            disregardedMessages.add(sentMessages.get(index));
            messageIds.remove(messageIdsList.get(index));
            sentMessages.remove(index);
            senders.remove(index);
            recipientNumber.remove(index);
            messageIdsList.remove(index);
            msgHash.remove(index);
            messageStatus.remove(index);
            
            JOptionPane.showMessageDialog(null, "Message deleted and moved to disregarded", "disregarded", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            JOptionPane.showMessageDialog(null,"Message not found","Error", JOptionPane.ERROR_MESSAGE ); 
            System.out.println("Message not found");
        }
    }

    public void displayFullReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No sent messages to display", "Full report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String report = "\n=========== Full Message Report ===========\n\n";
        report += String.format("| %-5s | %-10s | %-15s | %-30s | %-10s | %-6s | %n", 
                "No. ", "Sender", "Recipient", "Message", "Message ID", "hash ID");
         report += "-------------------------------\n";
                
        for (int i = 0; i < sentMessages.size(); i++) {
            String message = sentMessages.get(i);
            
            if (message.length() > 20) {
                message = message.substring(0, 17) + ". . .";
            }
            report += String.format("| %-5d | %-10s | %-15s | %-30s | %-10s | %-6s | %n", 
                    i + 1, senders.get(i), recipientNumber.get(i), message, messageIdsList.get(i), msgHash.get(i));
        }
        report += "-------------------------\n";
        report += "Total sent messages: " + sentMessages.size();
        report += "Total disregarded messages: " + disregardedMessages.size();
        report += "Total stored messages: " + storedMessages.size();
        JOptionPane.showMessageDialog(null, report, "Full message report", JOptionPane.INFORMATION_MESSAGE);
    }

    public void searchForMessageByHashId() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No sent messages to search", "Search message", JOptionPane.INFORMATION_MESSAGE );
             return;
        }
        String hashId = JOptionPane.showInputDialog(null,"Enter the HashID to search: ", "Search by hash", JOptionPane.QUESTION_MESSAGE);
        if (hashId == null || hashId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Hash ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int index = msgHash.indexOf(hashId.trim());
      
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "No message exists with this Hash ID:" + hashId, "Not Found", JOptionPane.ERROR_MESSAGE);
           return;
        }
        String details = "---------- Message Details -----------\n";
        details += "Hash ID: " + msgHash.get(index);
        details += "Message ID: " + messageIdsList.get(index);
        details += "From: " + senders.get(index);
        details += "To: " + recipientNumber.get(index);
        details += "Message: " + sentMessages.get(index);
        details += "Length: " + sentMessages.get(index).length() + " characters long ";
        
        JOptionPane.showMessageDialog(null, details, "Message found", JOptionPane.INFORMATION_MESSAGE);
    }

    public void findLongestMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No sent message to analyze the longest", "Longest message", JOptionPane.INFORMATION_MESSAGE);
             return;
        }
        int maxLength = -1;
        int index = -1;
        
        for (int i = 0; i < sentMessages.size(); i++) {
            int currentLength = sentMessages.get(i).length();
            if (currentLength > maxLength) {
                maxLength = currentLength;
                index = i;
            }
        }
        if (index != -1) {
            String longest = "----------- LONGEST MESSAGE SENT ------------\n";
            longest += "Length: " + maxLength + " characters long";
            longest += "Hash ID: " + msgHash.get(index);
            longest += "Message ID: " + messageIdsList.get(index);
            longest += "Sender: " + senders.get(index);
            longest += "Recipient: " + recipientNumber.get(index);
            longest += "Message: " + sentMessages.get(index);
            
            JOptionPane.showMessageDialog(null,longest,"Longest message",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}