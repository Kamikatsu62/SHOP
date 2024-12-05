package delsocorroshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reciept {
    Scanner sc = new Scanner(System.in);
    private String customerName;
    private String productName;
    private int quantity;
    private int price;
    private int total;

    public void getReceipt(int orderId) {
        
        fetchOrderDetails(orderId);
        viewReceipt();
    }

    private void fetchOrderDetails(int orderId) {
        String query = "SELECT c.c_fname, c.c_lname, p.p_Name, o.quantity, p.price " +
                       "FROM Orders o " +
                       "JOIN Customer c ON o.customer_id = c.c_id " +
                       "JOIN Product p ON o.product_id = p.p_id " +
                       "WHERE o.order_id = ?";
        
        try (Connection conn = config.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            this.customerName = rs.getString("c_fname") + " " + rs.getString("c_lname");
            this.productName = rs.getString("p_Name");
            this.quantity = rs.getInt("quantity");
            this.price = rs.getInt("price");
            this.total = this.quantity * this.price;
            } else {
                System.out.println("Order ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    public void viewReceipt() {
        System.out.println("-----------Receipt------------");
        System.out.println("|    Customer Name: " + customerName); 
        System.out.println("|    Product Name: " + productName);
        System.out.println("------------------------------");;
        System.out.println("|    Quantity: " + quantity);
        System.out.println("|    Price: " + price);
        System.out.println("------------------------------");
        System.out.println("|    Total: " + total);
        System.out.println("------------------------------");
    }
}