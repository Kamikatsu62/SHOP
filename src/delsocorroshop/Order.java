
package delsocorroshop;

import java.util.Scanner;


public class Order {
    public void getOrderD(){
        
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
            Order test = new Order();
            switch (action) {
                case 1:
                    test.addOrder();
                    break;
                case 2:
                    test.viewOrder();
                    break;
                case 3:
                    test.updateOrder();
                    break;
                case 4:
                    test.deleteOrder();
                    break;
                case 5:System.out.println("Returning to main selection.../n");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                continue;
                    
            }

            System.out.println("Continue? Y/N:");
            resp = sc.next();

        } while (resp.equalsIgnoreCase("Y"));
        System.out.println("Thank You!");
        
    }

    public void addOrder() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Order address: ");
        String Name = sc.nextLine();
        System.out.print("Order Type(cod/Pickup): ");
        String Type = sc.nextLine();
        System.out.print("Order date: ");
        String date = sc.nextLine();
        System.out.print("Order Status: ");
        String Status = sc.nextLine();

        String sql = "INSERT INTO Orders (oName, oType, oDate, Status) VALUES (?, ?, ?, ?)";

        conf.addRecord(sql, Name, Type, date, Status);
    }

    private void viewOrder() {
        String qry = "SELECT * FROM Orders";
        String[] hdrs = {"ID", "Name", "Type", "Stock", "Price"};
        String[] clms = {"o_id", "oName", "oType", "oDate", "Status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter new address: ");
        String nname = sc.next();
        System.out.print("Enter new order type(cod/pickup): ");
        String ntype = sc.next();
        System.out.print("Enter new date: ");
        String ndate = sc.nextLine();
        System.out.print("Enter new status: ");
        String nstatus = sc.nextLine();

        String qry = "UPDATE Orders SET oName = ?, oType = ?, oDate = ?, Status = ? WHERE p_id = ?";

        config conf = new config();
        conf.updateRecord(qry, nname, ntype, ndate, nstatus, id);
    }

    private void deleteOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM Orders WHERE o_id = ?";

        config conf = new config();
        conf.deleteRecord(qry, id);
    
    }

    
}
    
