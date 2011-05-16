package com.wutianyi.threadpattern.threadSpecificStorage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TSLog {
	
	private PrintWriter writer = null;
	
	public TSLog(String fileName) {
		try {
			writer = new PrintWriter(new FileWriter(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void println(String s) {
		writer.println(s);
	}
	
	public void close() {
		writer.println("==== End of log ====");
		writer.close();
	}
}
