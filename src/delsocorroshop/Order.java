package delsocorroshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Order {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void getOrderD() {
        String resp;
        do {
            System.out.println("------ORDERS--------");
            System.out.println("|  1. ADD Order    |");
            System.out.println("|  2. VIEW Orders  |");
            System.out.println("|  3. UPDATE Order |");
            System.out.println("|  4. DELETE Order |");
            System.out.println("|  5. GET Receipt  |");
            System.out.println("|  6. EXIT Order   |");
            System.out.println("--------------------");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    updateOrder();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 5:
                    getReceipt();
                    break;
                case 6:
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

    public void addOrder() {
        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();
        String[] customerData = fetchCustomerName(customerId);
        if (customerData == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();
        sc.nextLine();
        String[] productName = fetchProductname(productId);
        if (productName == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        int availableStock = fetchProductStock(productId);
        if (availableStock < quantity) {
            System.out.println("Insufficient stock! Available stock: " + availableStock);
            return; 
        }


        String sql = "INSERT INTO Orders (customer_id, c_fname, c_lname, product_id, p_name ,quantity) VALUES (?, ?, ?, ?, ?, ?)";
        

        conf.addRecord(sql, customerId, customerData[0], customerData[1], productId, productName[0], quantity);
        
        Product product = new Product();
        product.updateStock(productId, quantity); 
    }

    private String[] fetchCustomerName(int customerId) {
        String query = "SELECT c_fname, c_lname FROM Customer WHERE c_id = ?";
        String[] customerData = null;

        try (Connection conn = config.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerData = new String[]{rs.getString("c_fname"), rs.getString("c_lname")};
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer name: " + e.getMessage());
        }
        return customerData;
    }

    private String[] fetchProductname(int productId) {
        String query = "SELECT p_Name FROM Product WHERE p_id = ?"; 
        String[] productData = null;
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if ( rs.next()) {
                productData = new String[]{rs.getString("p_Name")};
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product name: " + e.getMessage());
        }
        return productData;
    }

    private int fetchProductStock(int productId) {
        String query = "SELECT Astock FROM Product WHERE p_id = ?";
        int stock = 0;
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("Astock");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product stock: " + e.getMessage());
        }
        return stock;
    }

    private void viewOrders() {
        String qry = "SELECT o.order_id, o.customer_id, o.product_id, o.quantity, o.order_date, c.c_fname, c.c_lname, p.p_Name " +
                     "FROM Orders o " +
                     "JOIN Customer c ON o.customer_id = c.c_id " +
                     "JOIN Product p ON o.product_id = p.p_id";
        String[] hdrs = {"Order ID", "Customer ID", "Product ID", "Quantity", "Order Date", "Customer First Name", "Customer Last Name", "Product Name"};
        String[] clms = {"order_id", "customer_id", "product_id", "quantity", "order_date", "c_fname", "c_lname", "p_Name"};
        
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateOrder() {
        System.out.print("Enter the Order ID to Update: ");
        int orderId = sc.nextInt();
        System.out.print("Enter new Customer ID: ");
        int customerId = sc.nextInt();
        System.out.print("Enter new Product ID: ");
        int productId = sc.nextInt();
        System.out.print("Enter new Quantity: ");
        int quantity = sc.nextInt();

        String qry = "UPDATE Orders SET customer_id = ?, product_id = ?, quantity = ? WHERE order_id = ?";

        conf.updateRecord(qry, customerId, productId, quantity, orderId);
    }

    private void deleteOrder() {
        System.out.print("Enter the Order ID to Delete: ");
        int orderId = sc.nextInt();

        String qry = "DELETE FROM Orders WHERE order_id = ?";

        conf.deleteRecord(qry, orderId);
    }

    private void getReceipt() {
        System.out.print("Enter Order ID to get receipt: ");
        int orderId = sc.nextInt();
        Reciept receipt = new Reciept();
        receipt.getReceipt(orderId);
    }
}