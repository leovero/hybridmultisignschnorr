package br.cin.ufpe.util;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Log {


	private String extension = ".log";

	private String type;

	private String source;

	private BufferedWriter output;
	
	private FileWriter file;

	public Log(String source, String type) {
		this.source = source;
		this.type = type;		
	}

	public void initializeLogFile()  throws IOException  {

		file = new FileWriter(this.source + "/" + this.type + this.extension);
		output = new BufferedWriter(file);

		output.write("ALGORITHM: " + this.type);
		output.newLine();
		output.newLine();

	}

	public void printLine(String data)  throws IOException  {

		output.write(data);
		output.newLine();

	}


	public void printNewLine()  throws IOException  {

		output.newLine();

	}

	public void printLinePerformance(long timeExecution, String unity) throws IOException {
                    
		this.output.write(timeExecution + ";" + unity);
		this.output.newLine(); 
		this.output.flush();
	}

	public void printLineSize(String type, long size, String unity) throws IOException {
                    
		this.output.write(type + ";" + size + " " + unity);
		this.output.newLine();
		this.output.flush(); 
	}

	public void closeLog() throws IOException {
		this.output.close();
	}
	
}
