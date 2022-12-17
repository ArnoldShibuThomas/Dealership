import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Insert {
	
	//private class variables for connecting to the DBS
	private String dbUsername;
	private String dbPassword;
	private String dbDriver;
	private String dbConnectionString;
	Scanner input = new Scanner(System.in);

	/**
	 * Insert
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbDriver
	 * @param dbConnectionString
	 * Constructor to assign the DBS connection data the appropriate values
	 */
	public Insert(String dbUsername, String dbPassword, String dbDriver, String dbConnectionString) {
		this.dbUsername=dbUsername;
		this.dbPassword=dbPassword;
		this.dbDriver=dbDriver;
		this.dbConnectionString=dbConnectionString;
	}
	
	public void startInsert() {
    	
    	/**
    	 * what does the user want to insert into?
    	 * helper function that displays menu and returns a value
    	 */
    	int tableChoice = tableSelect();
    	
    	/**
    	 * switch statement that will navigate to helper function to further prompt
    	 * user for more info to insert into desired table
    	 */
    	switch(tableChoice) {
    		case 1: 
    			getCarInsert();
    			break;
    		case 2: 
    			getCarPurchasedInsert();
    			break;
    		case 3: 
    			getContractWithInsert();
    			break;
    		case 4: 
    			getCustomerInsert();
    			break;
    		case 5: 
    			getDealershipInsert();
    			break;
    		case 6:
    			getEmployeeInsert();
    			break;
    		case 7: 
    			getMechanicInsert();
    			break;
    		case 8: 
    			getSalesRepInsert();
    			break;
    		case 9: 
    			getServicesInsert();
    			break;
    		case 10: 
    			getSoldRentedInsert();
    			break;
    		case 11: 
    			getHasInsert();
    			break;
    		case 12:
    			getRequestInsert();
    			break;
    	}
    	
    }
	
    /**
     * tableSelect
     * @return int
     * Helper function to ouput a menu of tables for the user to choose from to input data to
     */
    private int tableSelect() {
    	
    	int choice;
    	
    	//prints out menu for user
    	System.out.println("Please select a NUMBER option from below that corresponds to the table you would like to insert data into.");
    	System.out.println("1. Car");
    	System.out.println("2. Car_Purchased");
    	System.out.println("3. Contract_With");
    	System.out.println("4. Customer");
    	System.out.println("5. Dealership");
    	System.out.println("6. Employee");
    	System.out.println("7. Mechanic");
    	System.out.println("8. Sales Rep");
    	System.out.println("9. Services");
    	System.out.println("10. Sold_or_Rented");
    	System.out.println("11. Has Ownership Of");
    	System.out.println("12. Can Request For\n");
    	
    	//takes users input and returns value
    	choice = Integer.parseInt(input.nextLine());
    	return choice;
    	
    }
    	
    /**
     * getCarInsert
     * Asks for user input on the car values needed, sends values to helper
     * function to be inserted into DBS
     */
    private void getCarInsert() {
    	
    	//get vin
    	System.out.println("Please enter the car's VIN number:");
    	String vin = input.nextLine();
    	
    	//get inventory
    	System.out.println("Please enter the how many of this car is in the inventory:");
    	int inventory = Integer.parseInt(input.nextLine());
    	
    	//get year
    	System.out.println("Please enter the year of this car:");
    	String year = input.nextLine();
    	
    	//get model
    	System.out.println("Please enter the model of this car:");
    	String model = input.nextLine();
    	
    	//get color
    	System.out.println("Please enter the color of this car:");
    	String color = input.nextLine();
    	
    	//get manuf
    	System.out.println("Please enter the manufacturer of this car:");
    	String manuf = input.nextLine();
    	
    	//add to table then return
    	insertCar(vin, inventory, year, model, color, manuf);
    	
    }
    
    /**
     * insertCar
     * @param vin
     * @param inventory
     * @param year
     * @param model
     * @param color
     * @param manuf
     * 
     * Passes parameters into prepared statement to be added to DBS
     */
    private void insertCar(String vin, int inventory, String year, String model, String color, String manuf) {
    	
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO CAR (VIN, INVENTORY, YEAR, MODEL, COLOR, MANUFACTURER)"
					+ " VALUES (?, ?, ?, ?, ?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, vin);
			stmt.setInt(2,  inventory);
			stmt.setString(3,  year);
			stmt.setString(4,  model);
			stmt.setString(5, color);
			stmt.setString(6, manuf);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Car successfully added to the database!");
		}
		else {
			System.out.println("Failed to add Car...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }
    
    /**
     * getCarPurchasedInsert
     * asks the user for inputs to add to the Car Purchased table, sends data to helper
     * function to be added
     */
    private void getCarPurchasedInsert() {
    	
    	System.out.println("Please enter the Drivers License Number of the Customer:");
    	String dln = input.nextLine();
    	
    	System.out.println("Please enter the car VIN number:");
    	String vin = input.nextLine();
    	
    	insertCarPurchased(dln, vin);
    	}
    
    /**
     * insertCarPurchased
     * @param dln
     * @param vin
     * adds parameters to the CarPurchased table
     */
    private void insertCarPurchased(String dln, String vin) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO CAR_PURCHASED (DRIVERSLICENSENO, VIN)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, dln);
			stmt.setString(2,  vin);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("CarPurchased successfully added to the database!");
		}
		else {
			System.out.println("Failed to add CarPurchased...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getContractWithInsert
     * gets data to add to ContractWith table, sends it to function to be added
     */
    private void getContractWithInsert() {
    	
    	System.out.println("Please enter the Customer's Drivers License Number:");
    	String dln = input.nextLine();
    	
    	System.out.println("Please enter the Sales Reps Employee ID:");
    	String id = input.nextLine();
    	
    	insertContractWith(dln, id);
    }
    
    /**
     * insertContractWith
     * @param dln
     * @param id
     * takes data and inserts in the contractwith table
     */
    private void insertContractWith(String dln, String id) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO CONTRACT_WITH (DRIVERSLICENSENO, SALESREPEID)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, dln);
			stmt.setString(2,  id);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("ContractWith successfully added to the database!");
		}
		else {
			System.out.println("Failed to add contractWith...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getCustomerInsert
     * gets the values for a new customer, sends to function to be added to table
     */
    private void getCustomerInsert() {
    	System.out.println("Enter Customer Driver License Number:");
    	String dln = input.nextLine();
    	
    	System.out.println("Enter Customer Phone Number:");
    	String phoneNum = input.nextLine();
    	
    	System.out.println("Enter Customer Credit Score");
    	String credit = input.nextLine();
    	
    	System.out.println("Enter Customer Name:");
    	String name = input.nextLine();
    	
    	insertCustomer(dln, phoneNum, credit, name);
    }

    /**
     * insertCustomer
     * @param dln
     * @param phoneNum
     * @param credit
     * @param name
     * inserts new customer into customer table using the parameters given
     */
    private void insertCustomer(String dln, String phoneNum, String credit, String name) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO CUSTOMER (DRIVERSLICENSENO, PHONENUMBER, CREDITSCORE, NAME)"
					+ " VALUES (?, ?, ?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, dln);
			stmt.setString(2,  phoneNum);
			stmt.setString(3, credit);
			stmt.setString(4, name);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Customer successfully added to the database!");
		}
		else {
			System.out.println("Failed to add customer...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getDealershipInsert
     * gets user data for a new dealership, sends data to function to be added to table
     */
    private void getDealershipInsert() {
    	
    	System.out.println("Please Enter the Delaership ID:");
    	String id = input.nextLine();
    	
    	System.out.println("Please enter the Dealership Address:");
    	String address = input.nextLine();
    	
    	System.out.println("Please enter the Dealership Phone Number:");
    	String phoneNum = input.nextLine();
    	
    	insertDealership(id, address, phoneNum);
    }

    /**
     * insertDealership
     * @param id
     * @param address
     * @param phoneNum
     * uses parameters to insert a new dealership into the table
     */
    private void insertDealership(String id, String address, String phoneNum) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO DEALERSHIP (DEALERSHIPID, ADDRESS, PHONENUMBER)"
					+ " VALUES (?, ?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  address);
			stmt.setString(3, phoneNum);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Dealership successfully added to the database!");
		}
		else {
			System.out.println("Failed to add dealership...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getEmployeeInsert
     * gets user input for new employee data, sends to function to be added to table
     */
    private void getEmployeeInsert(){
    	System.out.println("Enter the Employee ID");
    	String eID = input.nextLine();
    	
    	System.out.println("Enter the DealershipID for the Employee");
    	String dID = input.nextLine();
    	
    	System.out.println("Enter the Employee Phone Number");
    	String phoneNum = input.nextLine();
    	
    	System.out.println("Enter the employee's salary");
    	String salary = input.nextLine();
    	
    	System.out.println("Enter the employee DOB (YYYY-MM-DD");
    	String DOB = input.nextLine();
    	
    	System.out.println("Enter the employee availability");
    	String avail = input.nextLine();
    	
    	System.out.println("Enter the employee MgrFlag (0 = not manager, 1 = manager)");
    	Byte flag = Byte.parseByte(input.nextLine());
    	
    	System.out.println("Enter the MgrEid");
    	String mgrID = input.nextLine();
    	
    	System.out.println("Enter the employee Name");
    	String name = input.nextLine();
    	
    	insertEmployee(eID,dID,phoneNum,salary,DOB,avail,flag,mgrID,name);
    }
    
    /**
     * insertEmployee
     * @param eID
     * @param dID
     * @param phoneNum
     * @param salary
     * @param DOB
     * @param avail
     * @param flag
     * @param mgrID
     * @param name
     * uses parameters to add a new employee to the table
     */
    private void insertEmployee(String eID, String dID, String phoneNum, String salary, String DOB, String avail, Byte flag, String mgrID, String name) {
    	
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO EMPLOYEE (EMPLOYEEID, DEALERSHIPID, PHONENUMBER, SALARY, DOB, AVAILABILITY, MGRFLAG, MGREID, NAME)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, eID);
			stmt.setString(2,  dID);
			stmt.setString(3, phoneNum);
			stmt.setString(4,  salary);
			stmt.setString(5, DOB);
			stmt.setString(6, avail);
			stmt.setByte(7, flag);
			stmt.setString(8, mgrID);
			stmt.setString(9, name);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Employee successfully added to the database!");
		}
		else {
			System.out.println("Failed to add Employee...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getHasInsert
     * collects data from user, sends data to helper function to be added to table
     */
    private void getHasInsert() {
    	System.out.println("Enter the DealershipID");
    	String id = input.nextLine();
    	
    	System.out.println("Enter the VIN number");
    	String vin = input.nextLine();
    	
    	insertHas(id, vin);
    }
    
    /**
     * insertHas
     * @param id
     * @param vin
     * takes parameters and adds a new Has entry in the table
     */
    private void insertHas(String id, String vin) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO HAS_OWNERSHIP_OF (DEALERSHIPID, VIN)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  vin);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Has_Ownership_Of successfully added to the database!");
		}
		else {
			System.out.println("Failed to add entry to Has Ownership Of...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getMechanicInsert
     * takes user data about mechanic and sends to function to be added to the database
     */
    private void getMechanicInsert() {
    	System.out.println("Enter Mechanic EmployeeID");
    	String id = input.nextLine();
    	
    	System.out.println("Enter Mechanic specialty");
    	String specialty = input.nextLine();
    	
    	insertMechanic(id, specialty);
    }

    /**
     * insertMechanic
     * @param id
     * @param specialty
     * uses parameters to create a new entry in the mechanic table
     */
    private void insertMechanic(String id, String specialty) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO MECHANIC (EMPLOYEEID, SPECIALTY)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  specialty);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Mechanic successfully added to the database!");
		}
		else {
			System.out.println("Failed to add mechanic...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getRequestInsert
     * takes user input about a request, sends data to function to be added
     */
    private void getRequestInsert() {
    	System.out.println("Enter SalesRepId");
    	String id = input.nextLine();
    	
    	System.out.println("Enter VIN number");
    	String vin = input.nextLine();
    	
    	insertRequest(id, vin);
    }

    /**
     * insertRequest
     * @param id
     * @param vin
     * uses parameters to add a new request entry in the table
     */
    private void insertRequest(String id, String vin) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO CAN_REQUEST_FOR (SALESREPEID, VIN)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  vin);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Can Request For entry successfully added to the database!");
		}
		else {
			System.out.println("Failed to add entry to Can Request For...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getSalesRepInsert
     * takes user input for a new sales rep entry, sends to function to be added to DBS
     */
    private void getSalesRepInsert() {
    	System.out.println("Enter Employee ID");
    	String id = input.nextLine();
    	
    	System.out.println("Enter Limit of Negotiation");
    	String limit = input.nextLine();
    	
    	insertSalesRep(id, limit);
    }

    /**
     * insertSalesRep
     * @param id
     * @param limit
     * uses parameters to add a new SalesRep to the table
     */
    private void insertSalesRep(String id, String limit) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO SALES_REP (EMPLOYEEID, LIMITOFNEGOTATION)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  limit);
			
			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("SalesRep successfully added to the database!");
		}
		else {
			System.out.println("Failed to add SalesRep...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getServicesInsert
     * takes input from user to be added to the services table
     */
    private void getServicesInsert() {
    	System.out.println("Enter Mechanic Employee ID");
    	String id = input.nextLine();
    	
    	System.out.println("Enter the VIN number");
    	String vin = input.nextLine();
    	
    	insertServices(id, vin);
    }

    /**
     * insertServices
     * @param id
     * @param vin
     * uses parameters to add a new services entry in the DBS
     */
    private void insertServices(String id, String vin) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO SERVICES (MECHANICEID, VIN)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, id);
			stmt.setString(2,  vin);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("Services successfully added to the database!");
		}
		else {
			System.out.println("Failed to add Services...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }

    /**
     * getSoldRentedInsert
     * takes user input to be added to the Sold or Rented table in the DBS
     */
    private void getSoldRentedInsert() {
    	System.out.println("Enter Drivers License Number");
    	String dln = input.nextLine();
    	
    	System.out.println("Enter VIN number");
    	String vin = input.nextLine();
    	
    	insertSoldRented(dln, vin);
    }

    
    /**
     * insertSoldRented
     * @param dln
     * @param vin
     * used parameters to create new soldRented entry in the DBS
     */
    private void insertSoldRented(String dln, String vin) {
    	int status = 0;
		PreparedStatement stmt = null;
		Connection conn = null;

		try {
			// Step 1: Load the JDBC driver
			Class.forName(dbDriver);

			// Step 2: make a connection
			conn = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);

			// Step 3: Create a statement
			stmt = conn.prepareStatement("INSERT INTO SOLD_OR_RENTED (DRIVERSLICENSENO, VIN)"
					+ " VALUES (?, ?)");
			
			//adds arguments to the statement value
			stmt.setString(1, dln);
			stmt.setString(2,  vin);

			// Step 4: Make a query
		status = stmt.executeUpdate();
		
		//checks if the values were added successfully and prints appropriate message
		if (status>0) {
			System.out.println("SoldRented successfully added to the database!");
		}
		else {
			System.out.println("Failed to add SoldRented...");
		}
			
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
    }
}
