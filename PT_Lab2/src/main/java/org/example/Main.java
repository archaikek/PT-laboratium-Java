package org.example;

import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		int threadCount = Integer.parseInt(args[0]);
		Thread threadTab[] = new Thread[threadCount];

		Resource input = new Resource();
		BufferedReader reader;
		FileWriter writer;
		try {
			reader = new BufferedReader(new FileReader("input.txt"));
			writer = new FileWriter("output.txt");
		} catch (Exception ex) {
			System.out.println("Files not present!");
			return;
		}

		for (int i = 0; i < threadCount; ++i) {
			threadTab[i] = new Thread(new FactorWorker(input, writer));
			threadTab[i].start();
		}
		while (true) {
			String line;
			try {
				line = reader.readLine();
				if (line == null) break;
			}
			catch (IOException ex) {
				break;
			}
			int number = Integer.parseInt(line);
			input.push(number);
		}

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.next();
			if(line == "Exit") break;
			int number = Integer.parseInt(line);
			input.push(number);
		}

		try {
			reader.close();
			writer.close();
		}
		catch (IOException ex) {}
		input.clear();

		for (int i = 0; i < threadCount; ++i) {
			threadTab[i].interrupt();
		}
		for (int i = 0; i < threadCount; ++i) {
			try {
				threadTab[i].join();
			}
			catch (InterruptedException ex) {
				System.out.println("This is epic.");
			}
		}
	}
}