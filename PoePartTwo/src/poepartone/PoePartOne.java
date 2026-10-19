/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poepartone;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class PoePartOne {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        login register = new login();
        Scanner scan = new Scanner(System.in);
        Message msg = new Message();
        int option;

        System.out.println("\n===== Register =====");

        System.out.println(" Please enter the username: ");
        String name = scan.nextLine();

        System.out.println("Enter password");
        String password = scan.nextLine();

        System.out.println("Enter cellphone number");
        String cellphone = scan.nextLine();

        System.out.println(register.registerUser(name, password, cellphone));

        if (!register.registerStatus(name, password, cellphone)) {
            return;
        }
        System.out.println("\n===== login =====");

        System.out.println(" Enter username: ");
        String loginname = scan.nextLine();

        System.out.println("Enter password: ");
        String loginpassword = scan.nextLine();

//        boolean status = register.loginUser(loginname, loginpassword);
//        System.out.println(register.returnLoginStatus(status));
        if (name.equals(loginname) && password.equals(loginpassword)) {

//        msg.numMessageSent = 8;
            

            do {
                System.out.println("\n==== Select any of the option below ====");
                System.out.println("1. Send message");

                System.out.println("2. coming soon");
                System.out.println("3. Exit");

                option = scan.nextInt();

                switch (option) {

                    case 1 -> {
                        msg.sendingMessages();
                    }
                    case 2 -> {
                        System.out.println("coming soon");
                    }
                    case 3 -> {
                        System.out.println("Goodbye!!");
                    }
                    default -> {
                        System.out.println("Invalid option");
                    }
                }
            } while (option != 3);
        
           
        }

    }

}
