package delsocorroshop;

import java.util.Scanner;

public class DelSocorroShop {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Customer c = new Customer();
        Product p = new Product();
        Order o = new Order();
        Reciept rc = new Reciept();
        
        String transaction;

        System.out.println("------Del Socorro SHOP------\n");
        
        do {
            System.out.println("----------------------");
            System.out.println("| 1. Customer Info   |");
            System.out.println("| 2. Product Info    |");  
            System.out.println("| 3. Order Details    |");
            System.out.println("----------------------");
            System.out.print("Enter selection: ");
            
            int select;
            
            while (true) {
                try {
                    select = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number (1-3).");
                    sc.next();
                }
            }

            switch (select) {
                case 1:
                    c.getCustomerID();
                    break;
                case 2: 
                    p.getProductID();
                    break;
                case 3:
                    o.getOrderD();
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

            System.out.print("Make another transaction? (y/n): ");
            transaction = sc.next();
        } while (transaction.equalsIgnoreCase("y"));
        
        sc.close(); // Close the scanner
        System.out.println("Thank you for visiting Del Socorro Shop!");
    }
}