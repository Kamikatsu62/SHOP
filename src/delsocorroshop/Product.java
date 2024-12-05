package delsocorroshop;

import java.util.Scanner;

public class Product {
    private final Scanner sc = new Scanner(System.in);
    private final config conf = new config();
    
    public void getProductID() {
        String resp;
        do {
            System.out.println("--------PRODUCT--------");
            System.out.println("| 1. ADD Product      |");
            System.out.println("| 2. VIEW Product     |");
            System.out.println("| 3. UPDATE Product   |");
            System.out.println("| 4. DELETE Product   |");
            System.out.println("| 5. EXIT Product     |");
            System.out.println("----------------------");

            System.out.print("Enter Action: ");
            int action;
           
            while (true) {
                try {
                    action = sc.nextInt();
                    sc.nextLine(); 
                    break; 
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number (1-5).");
                    sc.next();
                }
            }

            switch (action) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    System.out.println("Returning to main selection...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.print("Continue? Y/N: ");
            resp = sc.nextLine();

        } while (resp.equalsIgnoreCase("Y"));
        System.out.println("Thank You!");
    }

    public void addProduct() {
        System.out.print("Product Name: ");
        String name = sc.nextLine();
        
        int allTimeStock;
        while (true) {
            try {
                System.out.print("Product All Time Stock: ");
                allTimeStock = sc.nextInt();
                break; 
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }

        int price;
        while (true) {
            try {
                System.out.print("Product Price: ");
                price = sc.nextInt();
                break; 
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }

        String sql = "INSERT INTO Product (p_Name, Astock, CStock, Price) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, allTimeStock, allTimeStock, price);
    }

    private void viewProduct() {
        String qry = "SELECT p_id, p_Name, Astock, CStock, Price FROM Product";
        String[] hdrs = {"ID", "Name", "All Time Stock", "Current Stock", "Price"};
        String[] clms = {"p_id", "p_Name", "Astock", "CStock", "Price"};

        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateProduct() {
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter new Name: ");
        String newName = sc.nextLine();
        
        int newAllTimeStock;
        while (true) {
            try {
                System.out.print("Enter new All Time Stock: ");
                newAllTimeStock = sc.nextInt();
                break; 
            } catch (Exception e) {
                System .out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }

        int newPrice;
        while (true) {
            try {
                System.out.print("Enter new Price: ");
                newPrice = sc.nextInt();
                break; 
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); 
            }
        }

        String qry = "UPDATE Product SET p_Name = ?, Astock = ?, CStock = ?, Price = ? WHERE p_id = ?";
        conf.updateRecord(qry, newName, newAllTimeStock, newAllTimeStock, newPrice, id);
    }

    private void deleteProduct() {
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM Product WHERE p_id = ?";
        conf.deleteRecord(qry, id);
    }

    public void updateStock(int productId, int quantity) {
        String qry = "UPDATE Product SET CStock = CStock - ? WHERE p_id = ?";
        conf.updateRecord(qry, quantity, productId);
    }
}