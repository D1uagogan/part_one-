/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepartthree;

import javax.swing.JOptionPane;

/**
 *
 * @author Student
 */
public class PoePartThree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Login register = new Login();
   
        Message msg = new Message();
        JOptionPane.showMessageDialog(null,"\n===== Register =====", "Register", JOptionPane.INFORMATION_MESSAGE);

       
        String name = JOptionPane.showInputDialog(null,"Please enter the username:" );
       
        String password = JOptionPane.showInputDialog(null, "Enter password:" );
        
        String cellphone = JOptionPane.showInputDialog(null,"Enter cellphone:" );
        

        JOptionPane.showMessageDialog(null, register.registerUser(name,
                password, cellphone));
        
       if (!register.registerStatus(name,
                password, cellphone)) {
            return;
        }
        JOptionPane.showMessageDialog(null, "\n===== Login =====", "Login",JOptionPane.INFORMATION_MESSAGE );
        
        String loginUsername = JOptionPane.showInputDialog(null,"Enter the username to login" );
        
        String loginPassword = JOptionPane.showInputDialog(null, "Enter password to login" );
        

        
        boolean status = register.loginUser(loginUsername, loginPassword);
        
        JOptionPane.showMessageDialog(null, register.returnLoginStatus(status));


        if (status == true) {
            JOptionPane.showInputDialog(null,"Enter your first name:" );
            
             JOptionPane.showInputDialog(null,"Enter your surname name:" );
            
           
            int option;

            do {
                String menu = """
                              
                              ===== Select any of the option below =====
                              1. Send message
                              2. Message management
                              3. Exit Select an option: """;
                
                String input = JOptionPane.showInputDialog(null, menu, "Main menu",JOptionPane.QUESTION_MESSAGE);
                
                if(input == null){
                    option = 3;
                    
                }else{
                    try{
                        option = Integer.parseInt(input);
                        switch (option) {
                    case 1 ->
                        msg.sendingMessages();
                    case 2 ->
                        msg.messageManagementMenu();
                    case 3 ->
                         JOptionPane.showMessageDialog(null,"Good bye!!" );
                    default ->
                        JOptionPane.showMessageDialog(null,"Invalid option!!", "Error",JOptionPane.ERROR_MESSAGE);
                        
                }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null,"Please enter a vaid number", "Input Error", JOptionPane.WARNING_MESSAGE);
                            
                        option = 0;
                        
                    }
                }
               
            } while (option != 3);
        }

    }

}

    
    

