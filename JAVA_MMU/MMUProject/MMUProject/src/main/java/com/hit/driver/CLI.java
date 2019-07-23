package com.hit.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

import com.hit.util.MMULogger;
import com.hit.view.View;

public class CLI extends Observable implements Runnable, View {

	public static final String START = "START";
	public static final String STOP = "STOP";
	public static final String LRU = "LRU";
	public static final String NFU = "NFU";
	public static final String RANDOM = "RANDOM";

	private Scanner inputScanner;
	private PrintWriter outputWriter;

	public CLI(InputStream in, OutputStream out) {
		inputScanner = new Scanner(new BufferedReader(new InputStreamReader(in)));
		outputWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
	}

	public void write(String string) {
		outputWriter.write(string);
		outputWriter.flush();
	}

	public void run() {
		String command[];
		String nextLine;
		Boolean didStart = false;

		write("Hi, please type 'start' to begin:\n");
		while (inputScanner.hasNextLine()) {
			nextLine = inputScanner.nextLine().toUpperCase();
			command = nextLine.split("\\s+");

			if (command[0].equals(START)) {
				write("Enter algorithm and capacity with ALGO CAPACITY format\n");
				didStart = true;
			} else if (command[0].equals(STOP)) {
				write("Thank you\n");
				break;
			} else if (command[0].equals(LRU) || command[0].equals(NFU) || command[0].equals(RANDOM)) {
				if (didStart == true) {
					if (command.length == 2) {
						try {
							Integer capacity = Integer.parseInt(command[1]);
							if (capacity < 0) {
								throw new NumberFormatException();
							}

							// For each run of the application create a new log file
							MMULogger.getInstance().startNewLogFile();
							setChanged();
							notifyObservers(command);
						} catch (NumberFormatException ex) {
							write("Please enter a valid and positive capacity\n");
						}
					} else {
						write("Please write a valid command of format ALGO CAPACITY\n");
					}
				} else {
					write("Please write START before choosing algo\n");
				}
			} else {
				write("Not a valid command\n");
			}
		}
	}

	@Override
	public void start() {
		run();

	}

}
