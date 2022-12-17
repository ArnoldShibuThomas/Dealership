

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {
    //instance variables
    private String tableName;
    private ArrayList<String> rows;
    private ResultSet rs;
    private Statement stmt;
    private Connection conn;
    private String[] multipleWhere;

    //the classes only constructor that takes in the ResultSet, Statement, and connection
    public Search(ResultSet rs, Statement stmt, Connection conn){
        //variables are initiated
        String tableName = "";
        ArrayList<String> rows = new ArrayList<String>();
        this.rs = rs;
        this.stmt = stmt;
        this.conn = conn;
    }

    //the only method that is accessible to the objects of type Search
    public void performSearch() throws SQLException {
        //instantiate scanner
        Scanner input = new Scanner(System.in);
        //Tell the user to select a table they wish to search from
        System.out.println("Which table do you want to Search");
        System.out.println("1- car\n2- car_purchased\n3-contract_with\n4- customer\n5- dealership\n6- employee\n7- has_ownership of\n8- mechanic\n9- can_request_for\n10- sales_rep\n11- services\n12- sold_or_rented");
        System.out.println("Please select a table by number e.g 1");
        String option = input.nextLine();
        //get option and parse the integer selection to a number
        int selection = Integer.parseInt(option);
        //pass this number that the user provided and pass to helper method to make sure the search is performed on the right table
        getOption(selection);

        //if the person chooses anything besides what is provided, return to the main method
        if(tableName.equals(null)){
            return;
        }

        //Then ask the user what they are looking for in the table
        System.out.println("Are you looking for everything/everyone or just one column");
        System.out.print("Columns found in this table:");
        printHeader();
        System.out.println("Please type '*' for everyone/everything or type in column you wish to find e.g. 'name', if you need to find more than 1 column do e.g. 'name,year'");
        //parse to make sure multiple tables aren't chosen
        option = input.nextLine();

        //store their selection in this variable
        String columnSelected = option;

        //then we want to parse the selection that they made just in case they requested multiple values included in the search
        parseColumnSelected(columnSelected);

        //ask what if the user has anything they want to add to the WHERE clause constraints if not they type in no
        System.out.println("Any additional constraints type 'no' if not else type in additional constraint (e.g Color = 'Blue')");
        option = input.nextLine();

        //store this response in this variable
        String whereConstraint = option;

        //if they wanted to select everything and with no Where then execute this
        if(columnSelected.equals("*") && whereConstraint.equals("no")){
            String searchQuery = "SELECT * " +" FROM " + tableName;
            rs = stmt.executeQuery(searchQuery);
            starNo();
        }
        //if the user wanted to select all the tuples in that table and have a where clause execute this
        else if(columnSelected.equals("*") && !whereConstraint.equals("no")){
            String searchQuery = "SELECT * " +" FROM " + tableName +" WHERE " + whereConstraint;
            rs = stmt.executeQuery(searchQuery);
            System.out.println("Searching for all fields where " + whereConstraint + ":");
            starOnly();
        }
        //if the user stated which columns they wanted to select and the opted out of a where clause execute this
        else if(!columnSelected.equals("*") && whereConstraint.equals("no")){
            String searchQuery = "SELECT " + columnSelected + " FROM " + tableName;
            rs = stmt.executeQuery(searchQuery);
            System.out.println("Searching for all " + columnSelected + " in DB!" );
            oneColumnSelected(columnSelected);
        }
        //if the user has selected particular columns they want to search and they included a where constraint execute this
        else if(!columnSelected.equals("*") && !whereConstraint.equals("no")){
            String searchQuery = "SELECT " + columnSelected + " FROM " + tableName +" WHERE " + whereConstraint;
            rs = stmt.executeQuery(searchQuery);
            System.out.println("Searching for all " + columnSelected + "s in DB where " + whereConstraint +"!" );
            oneColumnSelected(columnSelected);
        }
        // all other inquiries execute this
        else{
            System.out.println("Please follow the prompt and provided response accordingly try again!");
        }
    }

    //this method translates the user's selection when they are prompted to choose a table!
    private void getOption(int selection){
        if(selection == 1){
            tableName = "car";
            rows = new ArrayList<String>();
            rows.add("Vin");
            rows.add("Inventory");
            rows.add("Year");
            rows.add("Model");
            rows.add("Color");
            rows.add("Manufacturer");
        }
        else if(selection == 2){
            tableName = "car_purchased";
            rows = new ArrayList<String>();
            rows.add("DriverLicenseNumber");
            rows.add("Vin");
        }
        else if(selection == 3){
            tableName = "contract_with";
            rows = new ArrayList<String>();
            rows.add("DriverLicenseNumber");
            rows.add("SalesRepEid");

        }
        else if(selection == 4){
            tableName = "customer";
            rows = new ArrayList<String>();
            rows.add("DriversLicenseNo");
            rows.add("PhoneNumber");
            rows.add("CreditScore");
            rows.add("Name");
        }
        else if(selection == 5){
            tableName = "dealership";
            rows = new ArrayList<String>();
            rows.add("DealershipID");
            rows.add("Address");
            rows.add("PhoneNumber");
        }
        else if(selection == 6){
            tableName = "employee";
            rows = new ArrayList<String>();
            rows.add("EmployeeId");
            rows.add("DealershipId");
            rows.add("PhoneNumber");
            rows.add("Salary");
            rows.add("DOB");
            rows.add("Availability");
            rows.add("MgrFlag");
            rows.add("MgrEid");
            rows.add("Name");
        }
        else if(selection == 7){
            tableName = "has_ownership of";
            rows = new ArrayList<String>();
            rows.add("DealershipId");
            rows.add("Vin");
        }
        else if(selection == 8){
            tableName = "mechanic";
            rows = new ArrayList<String>();
            rows.add("EmployeeId");
            rows.add("Specialty");
        }
        else if(selection == 9){
            tableName = "can_request_for";
            rows = new ArrayList<String>();
            rows.add("SalesRepEid");
            rows.add("Vin");
        }
        else if(selection == 10){
            tableName = "sales_rep";
            rows = new ArrayList<String>();
            rows.add("EmployeeId");
            rows.add("LimitOfNegotiation");
        }
        else if(selection == 11){
            tableName = "services";
            rows = new ArrayList<String>();
            rows.add("MechanicEid");
            rows.add("Vin");
        }
        else if(selection == 12){
            tableName = "sold_or_rented";
            rows = new ArrayList<String>();
            rows.add("DriversLicenseNo");
            rows.add("Vin");

        }
        else{
            System.out.println("Make a valid selection!");
            tableName = null;
            return;
        }
    }

    //this method is called when the User has stated they want to search for everything in a table
    private void starNo() throws SQLException {
        int length = rows.size() + 1;
        int results = 0;
        printHeader();
        while(rs.next()){
            for(int counter = 1; counter <length; counter++) {
                try {
                    System.out.print(rs.getByte(counter) + "\t");
                } catch (SQLException e) {
                    try {
                        System.out.print(rs.getString(counter) + "\t");
                    } catch (SQLException throwables) {
                        System.out.print(rs.getInt(counter) + "\t");
                    }
                }
                if(counter == length -1){
                    System.out.print("\n");
                    results++;
                }
            }
        }
        System.out.println("End Of the Search, there were (" + results + ") found in the DB!" + "\n");
    }

    //this method is a method that is called to call the starNo() method if this is called
    private void starOnly() throws SQLException{
        starNo();
    }

    //if the user has a  from clause that isn't a star then we will execute this method
    private void oneColumnSelected(String columnSelected) throws SQLException {
        int length = multipleWhere.length + 1;
        int results = 0;
        System.out.println("Here is the results for the " + columnSelected + ":");
        while(rs.next()){
            for(int counter = 1; counter <length; counter++) {
                try {
                    System.out.print(rs.getByte(counter) + "\t");
                } catch (SQLException e) {
                    try {
                        System.out.print(rs.getString(counter) + "\t");
                    } catch (SQLException throwables) {
                        System.out.print(rs.getInt(counter) + "\t");
                    }
                }
            }
            results++;
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.println("End Of the Search, there were (" + results + ") found in the DB!"+ "\n");
    }

    //this method prints  out the table header on the off chance the user selects *
    private void printHeader(){
        for(int i = 0; i < rows.size(); i++){
            System.out.print(rows.get(i) + "\t");
        }
        System.out.print("\n");
    }

    //this parses a from clause that might be lengthy and is comma separated
    private void parseColumnSelected(String option){
        multipleWhere = option.split(",");

    }
}
