package org.usfirst.frc.team1086.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class Logger {
	BufferedWriter out;
	DecimalFormat formatter = new DecimalFormat("00.00");
	boolean error = false;
	public Logger(File file){
		try {
			if(!file.exists())
				file.createNewFile();
			out = new BufferedWriter(new FileWriter(file));
		} catch (Exception e) {
			e.printStackTrace();
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
	    if(!error){
            try {
                out.write(name + "\t|\t" + value);
                out.newLine();
            } catch(Exception e){
            	e.printStackTrace();
                error = true;
            }
	    }
	}
	
	public void finish() {
		try {
			out.flush();
		} catch(Exception e) {}
	}
	
	public String format(double d) {
		return formatter.format(d);
	}
}
