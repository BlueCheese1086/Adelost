package org.usfirst.frc.team1086.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logger {
	BufferedWriter out;
	DecimalFormat formatter = new DecimalFormat("00.00");
	public static final int MAX_LOGS = 10;
	boolean error = false;
	public Logger(String mode){
		/*
		String time = new Date().toString();
		try {
			File folder = new File("/home/lvuser/logs");
			List<File> files = Stream.of(folder.listFiles()).sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
					.limit(MAX_LOGS - 1).collect(Collectors.toList());
			for(File f : folder.listFiles())
				if(!files.contains(f))
					f.delete();
			File logFile = new File("/home/lvuser/logs/" +time + " - " + mode + ".log");
			out = new BufferedWriter(new PrintWriter(logFile));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem setting up logger -- File not found");
		} */
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
		/*
	    if(!error){
            try {
                out.write(name + "\t|\t" + value);
                out.newLine();
            } catch(Exception e){
            	e.printStackTrace();
                error = true;
            }
	    } */
	}
	
	public void finish() {
		/*
		try {
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} */
	}
	
	public String format(double d) {
		//return formatter.format(d);
		return "";
	}
}
