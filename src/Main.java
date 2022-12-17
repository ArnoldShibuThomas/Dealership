
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws SQLException {
        String dbUsername;
        String dbPassword;
        String dbDriver;
        String url;

        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        Scanner  input = null;

        try {
            System.out.println("Enter DataBase UserName:");
            Scanner username = new Scanner(System.in);
            dbUsername = username.nextLine();


            System.out.println("Enter DataBase Password:");
            Scanner pass = new Scanner(System.in);
            dbPassword = pass.nextLine();

            System.out.println("Enter Driver:");
            Scanner driver = new Scanner(System.in);
            dbDriver = driver.nextLine();
            //1. Load the JDBC Driver
            System.out.println("Enter Connection URL(In this format jdbc:mysql://localhost:3306/\nyou don't have to specify which database the program will create it):");
            Scanner cs = new Scanner(System.in);
            url = cs.nextLine();
            
            System.out.println("Please wait. Building Database......");
            CreateDataBase cdb = new CreateDataBase(dbDriver, url,dbUsername,dbPassword);
            url += "dealershipdb";
            System.out.println("Done");
            Class.forName(dbDriver); //com.mysql.cj.jdbc.Driver*/
            /*Class.forName("com.mysql.cj.jdbc.Driver"); //Testing Purposes only*/

            //2. make a connection
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
/*
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dealershipdb", "root", "Mysqlpass@123"); //testing purposes only
*/

            //3. Create a Statement
            stmt = conn.createStatement();

            Search s =new Search(rs,stmt,conn);
            //create a update object
            Update u = new Update(dbUsername,dbPassword,dbDriver,url);
            //create a new insert object
            Insert i = new Insert(dbUsername, dbPassword, dbDriver, url);
            //create a new delete object
            Delete d = new Delete(dbDriver, url,dbUsername,dbPassword,"dealershipdb");
            String keyPressed = "n";
            while (!keyPressed.equals("q")) {
                input = new Scanner(System.in);
                System.out.println("*------------------------------------------------*");
                System.out.println("* Select a option to interact with the DataBase: *");
                System.out.println("* s -search                                      *");
                System.out.println("* d -delete                                      *");
                System.out.println("* i -insert                                      *");
                System.out.println("* u -update                                      *");
                System.out.println("* q -quit                                        *");
                System.out.println("*------------------------------------------------*");
                System.out.println("\n");
                keyPressed = input.nextLine();

                //switch statements
                if (keyPressed.equals("s")) {
                    //call search method
                    s.performSearch();
                } else if (keyPressed.equals("d")) {
                    //call delete method
                	d.UI();
                } else if (keyPressed.equals("i")) {
                    //call insert method
                    i.startInsert();
                } else if (keyPressed.equals("u")) {
                    //call update method
                   u.startUpdate();
                } else if (keyPressed.equals("q")) {
                    //quit the program
                    keyPressed = "q";
                    input.close();
                    stmt.close();
                    conn.close();
                    if(rs != null) {
                        rs.close();
                    }
                } else {
                    System.out.println("** Please select a valid option from below **");
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The class you are trying to use is not found!");
        }
        catch (SQLException throwables){
            System.out.println("look like the query you are trying to perform is not in the proper format mentioned by the prompt try again!");
        }
        finally {
            if(input != null) {
                input.close();
            }
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) {
                conn.close();
            }
            if(rs != null) {
                rs.close();
            }
            System.out.println("Program closing!");
        }
    }
}
