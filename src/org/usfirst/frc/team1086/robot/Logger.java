package org.usfirst.frc.team1086.robot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger implements Tickable{
	private PrintStream out;
	boolean working = true;
	static final DateFormat format = new SimpleDateFormat("MM-dd HH;mm;ss");
	private ArrayList<Printable> printList = new ArrayList<Printable>();

	public Logger() {
		File directory = new File("logs");
		if (!directory.exists() || !directory.isDirectory()) {
			if (!directory.mkdir()) {
				System.out.println("Problem Creating Directory");
				working = false;
			}
		}
		File newFile = new File("logs/" + format.format(new Date()) + ".log");
		if (directory.listFiles().length > 9) {
			File leastFile = directory.listFiles()[0];
			for (File f : directory.listFiles()) {
				leastFile = f.lastModified() < leastFile.lastModified() ? f : leastFile;
			}
			leastFile.delete();
		}

		try {
			out = new PrintStream(newFile);
		} catch (FileNotFoundException e) {
			System.out.println("Problem setting up logger");
			working = false;
		}
	}

	/**
	 * Allows you to directly print a valid value to the file <br/>
	 * <br/>
	 * <hr/>
	 * 
	 * @param name
	 *            the value's identifier
	 * @param value
	 *            the variable you want to print
	 */
	public void print(String name, String value) {
		if (working) {
			out.println(name + "  |  " + value);
		}
	}

	/**
	 * <b>Adds a new print statement to the list of periodically printed statements.
	 * </b> <br/>
	 * <br>
	 * <hr/>
	 * Example:<br/>
	 * logger.add(()->{return this.exampleValue;});<br/>
	 * <br/>
	 * <hr/>
	 * 
	 * @param p
	 *            the lambda expression to return your printed value
	 */
	public void add(Printable p) {
		if (working)
			printList.add(p);
	}
@Override
	public void tick() {
		for (Printable p : printList) {
			out.println(p.getKey()+ " | " +p.getValue());
		}
	}

}
