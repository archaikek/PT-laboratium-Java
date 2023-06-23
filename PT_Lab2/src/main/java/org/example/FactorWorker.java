package org.example;

import static java.lang.Thread.sleep;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class FactorWorker implements Runnable {
	private Resource input;
	private FileWriter writer;
	public FactorWorker(Resource input, FileWriter writer) {
		this.input = input;
		this.writer = writer;
	}
	@Override
	public void run() {
		while(true) {
			try {
				int number = input.pop();
				if (number < 0) break;
				String result = "Liczba " + number + ": 1";

				for (int i = 2; i <= number; ++i) {
					if (i < 0) break;
					if (number % i == 0) {
						result += ", " + i;
						sleep(1256);
					}
				}
				result += "\n";

				synchronized (writer) {
					System.out.println(result);
					try {
						writer.write(result);
						result = "";
					}
					catch (IOException ex) {
						System.out.println("IOException! That won't be good for the economy");
					}
				}
			} catch (InterruptedException ex) {
				System.out.println("Wow that's crazy bro you've been interrupted.");
				break;
			}
		}
	}
}
