
package delsocorroshop;

import java.util.Scanner;

public class Customer {
    public void getCustomerI(){
        Scanner sc = new Scanner(System.in);
        String resp = null;
        do {
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. EXIT");

            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            Customer test = new Customer();
            switch (action) {
                case 1:
                    test.addCustomer();
                    break;
                case 2:
                    test.viewCustomer();
                    break;
                case 3:
                    test.updateCustomer();
                    break;
                case 4:
                    test.deleteCustomer();
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

    public void addCustomer() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Customer First Name: ");
        String fname = sc.nextLine();
        System.out.print("Customer Last Name: ");
        String lname = sc.nextLine();
        System.out.print("Customer Email: ");
        String email = sc.nextLine();
        System.out.print("Customer Status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO Customer (e_fname, e_lname, e_email, e_status) VALUES (?, ?, ?, ?)";

        conf.addRecord(sql, fname, lname, email, status);
    }

    private void viewCustomer() {
        String qry = "SELECT * FROM Customer";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Status"};
        String[] clms = {"e_id", "e_fname", "e_lname", "e_email", "e_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter new First Name: ");
        String nfname = sc.next();
        System.out.print("Enter new Last Name: ");
        String nlname = sc.next();
        System.out.print("Enter new Email: ");
        String nemail = sc.next();
        System.out.print("Enter new Status: ");
        String nstatus = sc.next();

        String qry = "UPDATE Customer SET e_fname = ?, e_lname = ?, e_email = ?, e_status = ? WHERE e_id = ?";

        config conf = new config();
        conf.updateRecord(qry, nfname, nlname, nemail, nstatus, id);
    }

    private void deleteCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM Customer WHERE e_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
    }
        
    }
    
