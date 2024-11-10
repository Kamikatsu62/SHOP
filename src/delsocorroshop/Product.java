package delsocorroshop;

import java.util.Scanner;

public class Product {
    private final Scanner sc = new Scanner(System.in);
    private final config conf = new config();

    public void getProductID() {
        String resp = null;
        do {
            System.out.println("1. ADD Product");
            System.out.println("2. VIEW Product");
            System.out.println("3. UPDATE Product");
            System.out.println("4. DELETE Product");
            System.out.println("5. EXIT Product");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine(); // Clear the buffer

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
                    continue;
            }

            System.out.print("Continue? Y/N: ");
            resp = sc.nextLine();

        } while (resp.equalsIgnoreCase("Y"));
        System.out.println("Thank You!");
    }

    public void addProduct() {
        System.out.print("Product Name: ");
        String name = sc.nextLine();
        System.out.print("Product Type (solid/liquid): ");
        String type = sc.nextLine();
        System.out.print("Product Stock: ");
        int stock = sc.nextInt();
        System.out.print("Product Price: ");
        int price = sc.nextInt();

        String sql = "INSERT INTO Product (p_Name, Type, Stock, Price) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, type, stock, price);
    }

    private void viewProduct() {
        String qry = "SELECT * FROM Product";
        String[] hdrs = {"ID", "Name", "Type", "Stock", "Price"};
        String[] clms = {"p_id", "p_Name", "Type", "Stock", "Price"};

        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateProduct() {
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Clear the buffer
        System.out.print("Enter new Name: ");
        String newName = sc.nextLine();
        System.out.print("Enter new Type: ");
        String newType = sc.nextLine();
        System.out.print("Enter new Stock: ");
        int newStock = sc.nextInt();
        System.out.print("Enter new Price: ");
        int newPrice = sc.nextInt();

        String qry = "UPDATE Product SET Name = ?, Type = ?, Stock = ?, Price = ? WHERE p_id = ?";
        conf.updateRecord(qry, newName, newType, newStock, newPrice, id);
    }

    private void deleteProduct() {
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM Product WHERE p_id = ?";
        conf.deleteRecord(qry, id);
    }

    // Method to update stock after an order is placed
    public void updateStock(int productId, int quantity) {
        String qry = "UPDATE Product SET Stock = Stock - ? WHERE p_id = ?";
        conf.updateRecord(qry, quantity, productId);
    }
}