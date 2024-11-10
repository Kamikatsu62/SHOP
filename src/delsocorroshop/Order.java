package delsocorroshop;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Order {
    private final Scanner sc = new Scanner(System.in);
    private final config conf = new config();

    public void getOrderD() {
        String resp = null;
        do {
            System.out.println("1. ADD Order");
            System.out.println("2. VIEW Orders");
            System.out.println("3. UPDATE Order");
            System.out.println("4. DELETE Order");
            System.out.println("5. EXIT Order");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine(); // Clear the buffer

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

    public void addOrder() {
        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();
        String[] customerData = fetchCustomerName(customerId);
        if (customerData == null) {
            System.out.println("Customer not found!");
            return;
        }
        String e_fname = customerData[0];
        String e_lname = customerData[1];
        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();
        sc.nextLine();
        String[] productName = fetchProductname(productId);
        if (productName == null) {
            System.out.println("Product not found!");
            return;
        }
        String p_Name = productName[0];

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        int availableStock = fetchProductStock(productId);
        if (availableStock < quantity) {
            System.out.println("Insufficient stock! Available stock: " + availableStock);
            return; 
        }
        Date orderDate = new Date(System.currentTimeMillis());

        String sql = "INSERT INTO Orders (customer_id, e_fname, e_lname, product_id, p_Name, quantity, order_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        conf.addRecord(sql, customerId, e_fname, e_lname, productId, p_Name, quantity, orderDate);
    }
    private String[] fetchCustomerName(int customerId) {
        String query = "SELECT e_fname, e_lname FROM Customer WHERE e_id = ?";
        String[] customerData = null;

        try (Connection conn = conf.connectDB(); // Assuming you have a method to get the connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customerData = new String[]{rs.getString("e_fname"), rs.getString("e_lname")};
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer name: " + e.getMessage());
        }
        return customerData;
    }
    private String[] fetchProductname(int productId) {
        String query = "SELECT p_Name FROM Product WHERE p_id = ?"; // Corrected SQL query
        String[] productData = null;
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                productData = new String[]{rs.getString("p_Name")};
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product name: " + e.getMessage());
        }
        return productData;
    }
    private int fetchProductStock(int productId) {
        String query = "SELECT Stock FROM Product WHERE p_id = ?";
        int stock = 0;
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("Stock");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching product stock: " + e.getMessage());
        }
        return stock;
    }
    private void viewOrders() {
        String qry = "SELECT o.order_id, o.customer_id, o.product_id, o.quantity, o.order_date, c.e_fname, c.e_lname, p.p_Name " +
                     "FROM Orders o " +
                     "JOIN Customer c ON o.customer_id = c.e_id " +
                     "JOIN Product p ON o.product_id = p.p_id";
        String[] hdrs = {"Order ID", "Customer ID", "Product ID", "Quantity", "Order Date", "Customer First Name", "Customer Last Name", "Product Name"};
        String[] clms = {"order_id", "customer_id", "product_id", "quantity", "order_date", "e_fname", "e_lname", "p_Name"};
        
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
}