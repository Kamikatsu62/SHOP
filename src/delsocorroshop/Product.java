
package delsocorroshop;

import java.util.Scanner;

public class Product {
    public void getProductI(){
        Scanner sc = new Scanner(System.in);
        String resp = null;
        do {
            System.out.println("1. ADD Product");
            System.out.println("2. VIEW Product");
            System.out.println("3. UPDATE Product");
            System.out.println("4. DELETE Product");
            System.out.println("5. EXIT Product");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            Product test = new Product();
            switch (action) {
                case 1:
                    test.addProduct();
                    break;
                case 2:
                    test.viewProduct();
                    break;
                case 3:
                    test.updateProduct();
                    break;
                case 4:
                    test.deleteProduct();
                    break;
                case 5:System.out.println("Returning to main selection...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                continue;
                    
            }

            System.out.print("Continue? Y/N:");
            resp = sc.next();

        } while (resp.equalsIgnoreCase("Y"));
        System.out.println("Thank You!");
        
    }

    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Product Name: ");
        String Name = sc.nextLine();
        System.out.print("Product Type(solid/liquid): ");
        String Type = sc.nextLine();
        System.out.print("Product Stock: ");
        int stock = sc.nextInt();
        System.out.print("Product price: ");
        int price = sc.nextInt();

        String sql = "INSERT INTO tbl_products (Name, Type, Stock, Price) VALUES (?, ?, ?, ?)";

        conf.addRecord(sql, Name, Type, stock, price);
    }

    private void viewProduct() {
        String qry = "SELECT * FROM tbl_products";
        String[] hdrs = {"ID", "Name", "Type", "Stock", "Price"};
        String[] clms = {"p_id", "Name", "Type", "Stock", "Price"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter new First Name: ");
        String nname = sc.next();
        System.out.print("Enter new Last Name: ");
        String ntype = sc.next();
        System.out.print("Enter new Stock: ");
        int nstock = sc.nextInt();
        System.out.print("Enter new price: ");
        int nprice = sc.nextInt();

        String qry = "UPDATE tbl_products SET Name = ?, Type = ?, Stock = ?, Price = ? WHERE p_id = ?";

        config conf = new config();
        conf.updateRecord(qry, nname, ntype, nstock, nprice, id);
    }

    private void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_products WHERE p_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
    
    }

    
}
