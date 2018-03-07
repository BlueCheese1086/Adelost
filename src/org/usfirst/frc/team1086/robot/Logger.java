package org.usfirst.frc.team1086.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Logger {
	PrintStream out;
	DecimalFormat formatter = new DecimalFormat("00.00");
	public Logger(File file){
		try {
			out = new PrintStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Problem setting up logger -- File not found");
		}
	}
	
	/**
	 * Allows you to directly print a valid value to the file
	 * 
	 * @param name
	 *            the value's identifier
	 * @param value
	 *            the variable you want to print
	 */
	public void print(String name, String value) {
		out.println(name + "\t|\t" + value);
	}
	
	public String format(double d) {
		return formatter.format(d);
	}
}
