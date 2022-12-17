import java.sql.*;
import java.util.*;

/**
 * The purpose of this class is to be able to remove entries from a database
 *
 * @author David Hanley
 * @version 1.0
 */

public class Delete {
	private static String driver;
	private static String connection;
	private static String userName;
	private static String password;
	private static String dataBase;
	private static String[][] ColumnInformation;
	private static String[] ColumnNames;
	private static String tableName;
	private static int Count;
	Scanner myScanner = new Scanner(System.in);

	/**
	 * This method is the Constructor for the Delete class.
	 *
	 * @param sDriver     The driver information
	 * @param sConnection The Connection String for the Database
	 * @param sUserName   The user name for the database
	 * @param sPassword   The Password for the database
	 * @param sDataBase   The Database name. Allows the class to be usable
	 *                    regardless of connection.
	 */
	public Delete(String sDriver, String sConnection, String sUserName, String sPassword, String sDataBase) {
		driver = sDriver;
		connection = sConnection;
		userName = sUserName;
		password = sPassword;
		dataBase = sDataBase;
	}

	/**
	 * This Method takes and returns a user input after displaying the list of
	 * available tables.
	 * 
	 * @return String returns the selected Tables name
	 */
	private String DisplayTableOptions() {
		int iReturn = 0;
		String[] tableNames = new String[0];
		System.out.println("Please select a Table to remove an entry");
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connection, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs2 = stmt.executeQuery(
					"SELECT count(table_name) as rowcount FROM information_schema.tables where table_schema = '"
							+ dataBase + "';");
			while (rs2.next()) {
				tableNames = new String[rs2.getInt("rowcount")];
			}
			rs2.close();
			int iCounter = 0;
			ResultSet rs = stmt.executeQuery(
					"SELECT table_name FROM information_schema.tables where table_schema = '" + dataBase + "';");
			while (rs.next()) {
				tableNames[iCounter] = rs.getString(1);
				iCounter++;
				System.out.println(iCounter + ")" + rs.getString(1));
			}
			System.out.println("Please select the table number you wish to alter or type '0' to exit");
			iReturn = myScanner.nextInt();
			iReturn--;
			rs.close();
			stmt.close();
			con.close();
			if(iReturn != -1) {
			return tableNames[iReturn];
			}
			else {
			return "?";	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "Invalid Entry";
	}

	/**
	 * This method takes a table name and displays the table's information
	 *
	 * @param sTableName the name of the table to search on.
	 */
	private void DisplayTable(String sTableName) {
		tableName = sTableName;
		ColumnNames = new String[0];
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(connection, userName, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT count(*) as rowcount FROM information_schema.COLUMNS WHERE TABLE_NAME = '"
							+ tableName + "';");
			while (rs.next()) {
				ColumnNames = new String[rs.getInt("rowcount")];
			}
			rs = stmt.executeQuery("SELECT Column_Name as name FROM information_schema.COLUMNS WHERE TABLE_NAME ='"
					+ tableName + "';");
			int iCounter = 0;
			String header = "";

			while (rs.next()) {
				ColumnNames[iCounter] = rs.getString("name");
				String indent = "                    ";
				header += rs.getString("name") + indent.substring(0, indent.length() - rs.getString("name").length());
				iCounter++;
			}
			System.out.println(header);
			rs = stmt.executeQuery("SELECT count(*) as rowCount FROM " + tableName + ";");
			while (rs.next()) {
				ColumnInformation = new String[rs.getInt("rowCount")][ColumnNames.length];
				Count = rs.getInt("rowCount");
			}
			rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");
			iCounter = 0;
			while (rs.next()) {
				String displayLine = "";
				int innerCounter = 0;
				while (innerCounter < ColumnNames.length) {
					ColumnInformation[iCounter][innerCounter] = rs.getString(ColumnNames[innerCounter]);
					String indent = "                    ";
					displayLine += rs.getString(ColumnNames[innerCounter])
							+ indent.substring(0, indent.length() - rs.getString(ColumnNames[innerCounter]).length());
					innerCounter++;
				}
				iCounter++;
				System.out.println(iCounter + " " + displayLine);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method receives a line number and deletes the corresponding line from
	 * the database
	 *
	 * @param iLineNo the line number the user wishes to delete.
	 */
	private void DeleteByLine(int iLineNo) {
		String sSQL = "Delete From " + tableName + " where ";
		int iCounter = 0;
		while (iCounter < ColumnNames.length) {
			sSQL += ColumnNames[iCounter] + " = '" + ColumnInformation[iLineNo - 1][iCounter] + "'";
			iCounter++;
			if (iCounter < ColumnNames.length) {
				sSQL += " and ";
			} else {
				sSQL += ";";
			}
		}
		try {
			Connection con = DriverManager.getConnection(connection, userName, password);
			Statement stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.execute();
			System.out.println("Entry Removed sucessfully");
			ps.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		DisplayTable(tableName);
	}

	/**
	 * This method allows a user to build a where statement for deleting multiple
	 * entries at once.
	 *
	 * @param ColumnNo is an array of line numbers corresponding to columns.
	 * @param where    is an array of values the user wishes to delete
	 */
	private void DeleteByWhere(int[] columnNo, String[] where) {
		int iCounter = 0;
		String sSQL = "Delete From " + tableName + " where ";
		while (iCounter < columnNo.length) {
			sSQL += " " + ColumnNames[columnNo[iCounter]] + "  = '" + where[iCounter] + "'";
			iCounter++;
		}
		try {
			Connection con = DriverManager.getConnection(connection, userName, password);
			Statement stmt = con.createStatement();
			PreparedStatement ps = con.prepareStatement(sSQL);
			ps.execute();
			System.out.println("Entry Removed sucessfully");
			ps.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This Method displays all the columns in a chosen table
	 */
	private void displayColumn() {
		if (ColumnNames.length > 0) {
			int iCounter = 0;
			while (ColumnNames.length > iCounter) {
				System.out.println(iCounter + ") " + ColumnNames[iCounter]);
				iCounter++;
			}
		}
	}

	/**
	 * This Method expands an existing string Array by 1
	 */
	private String[] increaseArrayString(String[] oldArray) {
		String[] newArray = new String[oldArray.length + 1];
		int iCounter = 0;
		while (iCounter < oldArray.length) {
			newArray[iCounter] = oldArray[iCounter];
			iCounter++;
		}
		return newArray;
	}

	/**
	 * This Method expands an existing int Array by 1
	 */
	private int[] increaseArrayInt(int[] oldArray) {
		int[] newArray = new int[oldArray.length + 1];
		int iCounter = 0;
		while (iCounter < oldArray.length) {
			newArray[iCounter] = oldArray[iCounter];
			iCounter++;
		}
		return newArray;
	}

	/**
     * This Method is the UI for the delete class. All user Input is handled in this class.
     */
	public void UI() {
		Boolean exitDelete = false;
		while (!exitDelete) {
			String table = DisplayTableOptions();
			if (table.contains("Invalid Entry")) {
				System.out.println("Invalid Entry, please select a valid table");
			}
			else if(table.contains("?")) {
				exitDelete= true;
			}
			Boolean escapeDelete = false;
			while(!escapeDelete && !exitDelete && !table.contains("Invalid Entry")){
			if (!table.contains("?")) {
				DisplayTable(table);
				System.out.println(
						"to delete by line number ender 'L', to delete by a specific collum type 'C', or go back '?'");
				try {
					String input = myScanner.next();
					if (input.toUpperCase().contains("L")) {
						try {
							Boolean escapeDeleteLine = false;
							DisplayTable(table);
							System.out.println(
									"Please select the line number you wish to delete, use 0 to return to go back");
							while (!escapeDeleteLine) {
								int iInput = myScanner.nextInt();
								if (iInput != 0) {
									if (iInput <= Count) {
										DeleteByLine(iInput);
									} else {
										System.out.println("Invalid Line Number");
									}
									System.out.println(
											"Select any other lines you wish to delete, otherwise use 0 to go back");
								} else {
									escapeDeleteLine = true;
								}

							}

						} catch (Exception e) {

						}
					} else if (input.toUpperCase().contains("C")) {
						displayColumn();
						int[] Column = new int[0];
						String[] Where = new String[0];
						Boolean noMore = false;
						int counter = 0;
						while (!noMore) {
							Column = increaseArrayInt(Column);
							Where = increaseArrayString(Where);							
							System.out.println("Please select the number of the Column you would like to delete on");
							Column[counter] = myScanner.nextInt();
							System.out.println("Please input the value you want to delete on ");
							Where[counter] = myScanner.next();
							System.out.println("are there any more Columns you would like to delete on? Y/N");
							String userTest = myScanner.next();
							if(userTest.toUpperCase().contains("N")) {
								DeleteByWhere(Column,Where);
								noMore = true;
							}							
						}
					} else if (input.contains("?")) {
						table = "?";						
					}

				} catch (Exception e) {
					System.out.println("Invalid Entry");
				}
			} else {				
				escapeDelete = true;
			}
			}
		}
		//myScanner.close();
	}
}
