
import java.sql.*;
import java.util.Scanner;

public class Update {
    private String user;
    private String pwd;
    private String driver;
    private String url;

    public Update(String user, String pwd, String driver, String url) {
        this.user = user;
        this.pwd = pwd;
        this.driver = driver;
        this.url = url;
    }

    public void startUpdate() {
        Scanner scan = new Scanner(System.in);
        PreparedStatement stmt = null;
        Connection connection = null;

        String[] tables = {"car", "car_purchased", "contract_with", "customer", "dealership",
                "employee", "has_ownership_of", "mechanic", "can_request_for", "sales_rep", "services", "sold_or_rented" };

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pwd);

            System.out.println("----- UPDATE -----");
            System.out.println("Please select the integer representing the table you would like to update.");
            for (int i = 0; i < tables.length; i++) {
                System.out.println((i + 1) + ". " + tables[i]);
            }
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println("Displaying " + tables[choice - 1] + ".");
            displayTable(connection, tables[choice - 1]);

            System.out.println("------- SET ------");
            System.out.println("Which attribute would you like to update? Please enter the full name of the column");
            String aName = scan.nextLine();
            System.out.println("What would you like to change the value to?");
            String aVal = scan.nextLine();

            System.out.println("------ WHERE ------");
            System.out.println("What conditions should this happen under?");
            System.out.println("Enter condition, i.e., Name = 'Bob'");
            String where = scan.nextLine();

            int intVal;
            try {
                intVal = Integer.parseInt(aVal);
            } catch (Exception e) {
                intVal = -1;
            }

            String updateStmt = "UPDATE " + tables[choice - 1] +
                    " SET " + aName + " = ";
            if (intVal >= 0) {
                updateStmt += intVal;
            } else {
                updateStmt += "'" + aVal +"'";
            }
            updateStmt += " WHERE " + where;
            stmt = connection.prepareStatement(updateStmt);

            stmt.executeUpdate();

            displayTable(connection, tables[choice - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private void displayTable(Connection connection, String tableName) {
        String strSt = "SELECT * FROM " + tableName;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(strSt);

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            String colName[] = new String[colCount];

            for (int i = 0; i < colCount; i++) {
                colName[i] = meta.getColumnLabel(i + 1);
                System.out.printf("%15s", colName[i]);
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 0; i < colCount; i++) {
                    try {
                        System.out.printf("%15d", rs.getInt(i + 1));
                    } catch (Exception e) {
                        System.out.printf("%15s", rs.getString(i + 1));
                    }
                }
                System.out.println();
            }
            System.out.println();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
