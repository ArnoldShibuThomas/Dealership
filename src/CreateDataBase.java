

import java.sql.*;

public class CreateDataBase {

	private static String driver;
	private static String connection;
	private static String userName;
	private static String password;

	/**
     * This method is the Constructor for the CreateDataBase class. It will delete the database car if it exists
     * and rebuild it.
     *
     * @param sDriver The driver information
     * @param sConnection The Connection String for the Database
     * @param sUserName The user name for the database
     * @param sPassword The Password for the database
     */
	
	public CreateDataBase(String sDriver, String sConnection, String sUserName, String sPassword) {
		driver = sDriver;
		connection = sConnection;
		userName = sUserName;
		password = sPassword;
		try {
			String sSQL = "DROP DATABASE IF EXISTS dealershipdb;";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connection, userName, password);
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE DATABASE IF NOT EXISTS dealershipdb;";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			ps.close();
			con.close();
			con = DriverManager.getConnection(connection+"dealershipdb", userName, password);
			sSQL = "CREATE TABLE DEALERSHIP (DealershipID VARCHAR(12) NOT NULL,  Address VARCHAR(40) NOT NULL,  PhoneNumber VARCHAR(12) NOT NULL,  PRIMARY KEY(DealershipID),  UNIQUE(Address,PhoneNumber));";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE CUSTOMER (DriversLicenseNo VARCHAR(40) NOT NULL,  PhoneNumber VARCHAR(12) NOT NULL,  CreditScore VARCHAR(5) NOT NULL,  Name VARCHAR(50) NOT NULL,  Primary KEY(DriversLicenseNo), UNIQUE(NAME,CreditScore,PhoneNumber));";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE EMPLOYEE (EmployeeId VARCHAR(12) NOT NULL, DealershipID VARCHAR(12) NOT NULL, PhoneNumber VARCHAR(12) NOT NULL, Salary VARCHAR(12) NOT NULL, DOB VARCHAR(12) NOT NULL, Availability VARCHAR(50) NOT NULL, MgrFlag bit NOT NULL, MgrEid VARCHAR(12) NOT NULL, Name VARCHAR(50) NOT NULL, Primary KEY(EmployeeId), UNIQUE(PhoneNumber,DOB,NAME), FOREIGN KEY(DealershipID) REFERENCES DEALERSHIP(DealershipID) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE CAR ( Vin VARCHAR(30) NOT NULL, Inventory INT NOT NULL, Year VARCHAR(5) NOT NULL, Model VARCHAR(30) NOT NULL, Color VARCHAR(30) NOT NULL, Manufacturer VARCHAR(30) NOT NULL, PRIMARY KEY(Vin) );";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE CAR_PURCHASED (DriversLicenseNo VARCHAR(40) NOT NULL,  Vin VARCHAR(30) NOT NULL,  PRIMARY KEY(DriversLicenseNo,VIN),  FOREIGN KEY(DriversLicenseNo) REFERENCES CUSTOMER(DriversLicenseNo) ON DELETE CASCADE, FOREIGN KEY(Vin) REFERENCES CAR(Vin));";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE MECHANIC( EmployeeId VARCHAR(12) NOT NULL, Specialty VARCHAR(30) NOT NULL, PRIMARY KEY(EmployeeId), FOREIGN KEY(EmployeeId) REFERENCES EMPLOYEE(EmployeeId) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE SALES_REP( EmployeeId VARCHAR(12) NOT NULL, LimitOfNegotation VARCHAR(30) NOT NULL, PRIMARY KEY(EmployeeId), FOREIGN KEY(EmployeeId) REFERENCES EMPLOYEE(EmployeeId) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE CONTRACT_WITH(DriversLicenseNo VARCHAR(40) NOT NULL, SalesRepEid VARCHAR(12) NOT NULL, PRIMARY KEY(DriversLicenseNo,SalesRepEid), FOREIGN KEY(DriversLicenseNo) REFERENCES CUSTOMER(DriversLicenseNo) ON DELETE CASCADE, FOREIGN KEY(SalesRepEid) REFERENCES EMPLOYEE(EmployeeId) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE SERVICES(MechanicEid VARCHAR(12) NOT NULL, Vin VARCHAR(30) NOT NULL, PRIMARY KEY(MechanicEid,Vin), FOREIGN KEY(MechanicEid) REFERENCES EMPLOYEE(EmployeeId) ON DELETE CASCADE, FOREIGN KEY(Vin) REFERENCES CAR(Vin) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE HAS_OWNERSHIP( DealershipID VARCHAR(12) NOT NULL, Vin VARCHAR(30) NOT NULL,  PRIMARY KEY(DealershipID,Vin), FOREIGN KEY(DealershipID) REFERENCES DEALERSHIP(DealershipID) ON DELETE CASCADE, FOREIGN KEY(Vin) REFERENCES CAR(Vin) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "CREATE TABLE SOLD_OR_RENTED(DriversLicenseNo VARCHAR(40) NOT NULL,  Vin VARCHAR(30) NOT NULL, PRIMARY KEY(DriversLicenseNo,Vin), FOREIGN KEY(DriversLicenseNo) REFERENCES CUSTOMER(DriversLicenseNo) ON DELETE CASCADE, FOREIGN KEY(Vin) REFERENCES CAR(Vin) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "Create TABLE CAN_REQUEST_FOR(  SalesRepEid VARCHAR(12) NOT NULL,  Vin VARCHAR(30) NOT NULL,  PRIMARY KEY(SalesRepEid,Vin),  FOREIGN KEY(SalesRepEid) REFERENCES SALES_REP(EmployeeId) ON DELETE CASCADE,  FOREIGN KEY(Vin) REFERENCES CAR(Vin) ON DELETE CASCADE);";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO DEALERSHIP (DealershipID, Address, PhoneNumber) VALUES ('001', '123 N Fun Street', '5550001234'), ('002', '456 E Sql Ave', '5551115678'),('003', '789 S Devils Lane', '555234');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO EMPLOYEE (EmployeeID, DealershipId, PhoneNumber, Salary, DOB, Availability, MgrFlag, MgrEid, Name) VALUES('5555', '001', '5554441233', '50000', '1990-08-10', 'Any', 1, '5555', 'Paul'),('3366', '002', '5553336555', '50000', '1991-09-10', 'Any', 1, '5555', 'James'),('2233', '003', '5557754033', '50000', '1994-08-17', 'Any', 1, '5555', 'Diana'),('5533', '001', '5558783330', '43450', '1982-06-22', 'Mon-Wed-Fri',0, '5555', 'Tina'),('3383', '002', '5559022342', '43450', '1980-05-02', 'Mon-Wed-Fri',0, '3366', 'Arthur'),('2273', '003', '5558792344', '43450', '1986-05-22', 'Mon-Wed-Fri', 0, '2233', 'Brooke'),('5593', '001', '5557473234', '55000', '1998-02-20', 'Weekdays', 0, '5555', 'Bill'),('3387', '002', '5559044772', '55000', '1976-06-25', 'Weekdays', 0, '3366', 'Audrey'),('2222', '003', '5551277348', '55000', '1992-04-21', 'Weekdays', 0, '2233',  'Esther');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO MECHANIC (EmployeeId, Specialty) VALUES('5533', 'Brakes'),('3383', 'Oil'),('2273', 'Tires');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO SALES_REP (EmployeeId, LimitOfNegotation) VALUES('5593', '15000'),('3387', '18000'),('2222', '10000');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO CUSTOMER (DriversLicenseNo, PhoneNumber, CreditScore, Name) VALUES('D0988', '5552113444', '710', 'Joel'),('D0899', '5553225434', '645', 'Betty'),('D0966', '5556778343', '698', 'Dimitri');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO CAR (Vin, Inventory, Year, Model, Color, Manufacturer) VALUES('V985543', '4', '2021', 'Elantra', 'Black', 'Hyundai'),('V877338', '2', '2012', 'Fusion', 'Blue', 'Ford'),('V992328', '5', '2018', 'CRV', 'Green', 'Honda');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO CAR_PURCHASED (DriversLicenseNo, Vin) VALUES('D0988', 'V985543'),('D0966', 'V877338'),('D0899', 'V992328');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO CONTRACT_WITH (DriversLicenseNo, SalesRepEid) VALUES('D0988', '5593'),('D0966', '3387'),('D0899', '2222');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO SERVICES (MechanicEid, Vin) VALUES('5533', 'V985543'),('3383', 'V877338'),('2273', 'V992328');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO HAS_OWNERSHIP (DealershipId, Vin) VALUES('001', 'V985543'),('002', 'V877338'),('003', 'V992328');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO SOLD_OR_RENTED (DriversLicenseNo, Vin) VALUES('D0988', 'V985543'),('D0966', 'V877338'),('D0899', 'V992328');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			sSQL = "INSERT INTO CAN_REQUEST_FOR (SalesRepEid, Vin) values('5593', 'V985543'),('3387', 'V877338'),('2222', 'V992328');";
			ps = con.prepareStatement(sSQL);
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
