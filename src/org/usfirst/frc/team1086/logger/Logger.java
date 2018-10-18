package org.usfirst.frc.team1086.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author Grant Matteo A multi-functional logger designed to output to the
 *         Console, a File, a SQL database, or all 3.
 */
public class Logger {

	private HashMap<String, Loggable> functions; // Used to hold a set of lambda functions that return Strings to be
														// logged

	private BufferedWriter fileWriter;
	private String delimeter; // Used as a delimeter in the file output. (ex: comma in .csv files)

	private boolean sqlInitialized = false, fileIntialized = false;
	private long sqlCounter = 0, consoleCounter = 0, fileCounter = 0;
	String sqlAddCommand = "INSERT INTO log VALUES (?);";
	private Connection connection; // holds the connection to the SQL server
	private String username, password, url; // holds SQL server data

	/**
	 * Initializes a Logger object.
	 */
	public Logger() {
		functions = new HashMap<String, Loggable>();
	}

	/**
	 * Adds an anonymous function to a list of functions that will be called when
	 * logging. <i>Example: addStatement("Time", ()->{return
	 * ""+System.nanoTime();}); </i>
	 * 
	 * @param name
	 *            The label that will be associated with this function's return
	 *            value
	 * @param function
	 *            A function with <i>NO PARAMETERS</i> that <i>RETURNS A STRING</i>
	 *            to be logged by logTo*() methods.
	 */
	public void addStatement(String name, Loggable function) throws Exception{
		for (char c : name.toCharArray()) {
			if (c<48 || c>122 || (c>90 && c<97) ||(c>58 && c<65)) { //if it isn't a letter or number, it's wrong
				System.err.println("ERROR IN addStatement(); function name is not valid. Please use only ASCII characters an numbers");
				throw(new Exception());
			}
		}
		functions.put(name, function);
		
		
		sqlAddCommand = sqlAddCommand.substring(0, sqlAddCommand.length() - 2);
		sqlAddCommand += ", ?);";
		if (sqlInitialized) {
			String command = "ALTER TABLE log ADD "+name+" varchar(80);";
			try {
				Statement add = connection.createStatement();
				add.executeUpdate(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Error in addStatement(); initSQL() method either wasn't called or failed");
		}

	}

	/**
	 * Prepares the logger for file output. <b>THIS FUNCTION MUST BE CALLED BEFORE
	 * CALLING logToFile()!!!!!</b>
	 * 
	 * @param filepath
	 *            The path to the file to which you would like to output.
	 * @param delimeter
	 *            The String that will be used to separate values (EX: a comma in a
	 *            CSV)
	 */
	public void initFile(String filepath, String delimeter) {
		this.delimeter = delimeter;
		try {
			fileWriter = new BufferedWriter(new FileWriter(new File(filepath)));
			fileIntialized = true;
		} catch (IOException e) {
			System.err.println("INVALID FILEPATH IN LOGGER initFile(): STACKTRACE:\r\n\r\n");
			e.printStackTrace();
		}
	}

	/**
	 * Prepares the logger for SQL output. <b>THIS FUNCTION MUST BE CALLED BEFORE
	 * CALLING logToSQL()!!!!!</b>
	 * 
	 * @param url
	 *            the url of your SQL server
	 *            (EX:jdbc:postgresql://localhost/postgres) See
	 *            <i>https://dzone.com/articles/connecting-sql-server-java</i> and
	 *            the associated articles for more information
	 * @param username
	 *            the username you wish to use for your SQL server. This should be
	 *            the same as the one used in postgresql setup
	 * @param password
	 *            the password you wish to use for your SQL server. This should be
	 *            the same as the one used in postgresql setup
	 */
	public void initSQL(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.print("Successfully connected to Server at " + url + "\r\n");
			Statement init = connection.createStatement();
			init.executeUpdate("DROP TABLE IF EXISTS log;");
			init.executeUpdate("CREATE TABLE log (iteration int NOT NULL, PRIMARY KEY (iteration));");
			sqlInitialized = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Prints the function names to the file. <b>DO NOT USE BEFORE
	 * initFile()!!!</b> Recommended to use after adding all functions but before
	 * calling logToFile()
	 */
	public void printFileHeaders() {
		try {
			fileWriter.write("Iteration" + delimeter);
			for (String str : functions.keySet()) {
				fileWriter.write(str + delimeter);
			}
			fileWriter.write("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Puts the return values of functions passed into addStatement() into a SQL
	 * database <b>DO NOT USE UNTIL initSQL() HAS RUN!!!! WARNING: Using
	 * addStatement() after calling this method can cause unintended behavior</b>
	 */
	public void logToSQL() {
		if (sqlInitialized) {
			
			try {
				PreparedStatement add = connection.prepareStatement(sqlAddCommand);
				add.setLong(1, sqlCounter);
				int j=2;
				for (String str : functions.keySet()) {
					add.setString(j, functions.get(str).getLogValue());
					j++;
				}
				sqlCounter++;
				add.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error in logToSQL(); initSQL() method either wasn't called or failed");
				e.printStackTrace();
			}

		} else {
			System.err.println("Error in logToSQL(); initSQL() method either wasn't called or failed");
		}
	}

	/**
	 * Puts the return values of functions passed into addStatement() into StdOut
	 */
	public void logToConsole() {
		System.out.println("---------------------------------------\r\n" + "ITERATION " + consoleCounter + ":");
		for (String str : functions.keySet()) {
			System.out.println(str + "\t\t" + functions.get(str).getLogValue());
		}
		consoleCounter++;
		System.out.println("---------------------------------------\r\n\r\n");
	}

	/**
	 * Puts the return values of functions passed into addStatement() into a File
	 * <b>DO NOT USE UNTIL initFile() HAS RUN!!!! WARNING: Using addStatement()
	 * after calling this method can cause unintended behavior</b>
	 */
	public void logToFile() {
		if (fileIntialized) {
			try {
				fileWriter.write(fileCounter + delimeter);
				for (String str : functions.keySet()) {
					fileWriter.write(functions.get(str).getLogValue() + delimeter);
				}
				fileWriter.write("\r\n");
				fileCounter++;
			} catch (Exception e) {
				System.err
						.println("EXCEPTION THROWN IN logToFile(). CHECK THAT YOUR FILEPATH IS VALID. STACKTRACE:\r\n\r\n");
				e.printStackTrace();
			}
		} else {
			System.err.println("File Uninitialized. Please check that initFile was run correctly\r\n\r\n");
		}
	}

	/**
	 * Closes all associated objects and ends logging.
	 */
	public void close() {
		try {
			if (fileIntialized)
				fileWriter.close();
			if (sqlInitialized)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
