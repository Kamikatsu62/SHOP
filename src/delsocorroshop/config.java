package delsocorroshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class config {

    // Connection Method to SQLITE
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:Shop.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    // Add Record Method
    public void addRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); 
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); 
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); 
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); 
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); 
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); 
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); 
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); 
                } else {
                    pstmt.setString(i + 1, values[i].toString()); 
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    // View Records Method
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
        // Check that columnHeaders and columnNames arrays are the same length
        if (columnHeaders.length != columnNames.length) {
            System.out.println("Error: Mismatch between column headers and column names.");
            return;
        }

        try (Connection conn = this.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("--------------------------------------------------------------------------------------------------------------------\n| ");
            for (String header : columnHeaders) {
                headerLine.append(String.format("%-20s | ", header)); 
            }
            headerLine.append("\n-------------------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : columnNames) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s | ", value != null ? value : "")); 
                }
                System.out.println(row.toString());
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    // Update Record Method
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); 
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); 
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); 
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); 
                } else if (values[i] instanceof Boolean) {
                    pstmt .setBoolean(i + 1, (Boolean) values[i]); 
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); 
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); 
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); 
                } else {
                    pstmt.setString(i + 1, values[i].toString()); 
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    // Delete Record Method
    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); 
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); 
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); 
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); 
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); 
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); 
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); 
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); 
                } else {
                    pstmt.setString(i + 1, values[i].toString()); 
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }
}