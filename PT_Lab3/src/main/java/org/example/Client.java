package org.example;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 2221)) {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Scanner sc = new Scanner(System.in);
			String line = null;
			int n, seed;

			/* implement the protocol */

			try { // Receive "Ready", send an int from stdin
				Message received = (Message)in.readObject();
				logReceived(received);
				seed = received.getNumber();

				n = Integer.parseInt(sc.nextLine());
				out.writeObject(new Message(n, ""));
				out.flush();
			}
			catch (Exception ex) {
				System.out.println("Receive failed!");
				sc.close();
				return;
			}

			try { // Receive "Ready for messages"
				Message received = (Message)in.readObject();
				logReceived(received);
			}
			catch (Exception ex) {
				System.out.println("Receive failed!");
				sc.close();
				return;
			}

			for (int i = 1; i <= n; ++i) { // Send n messages
				line = sc.nextLine();
				Message sent = new Message(i, line);
				out.writeObject(sent);
				out.flush();
				logSent(sent);
			}

			try { // Receive "Ready for messages"
				Message received = (Message)in.readObject();
				logReceived(received);
			}
			catch (Exception ex) {
				System.out.println("Receive failed!");
				sc.close();
				return;
			}

			sc.close();
		}
		catch (IOException ex) {
			System.out.println("Failed to start the server! Details:\n" + ex.toString());
		}
	}

	private static void logReceived(Message message) {
		System.out.println("Server: " + message.getNumber() + " - " + message.getContent());
	}
	private static void logSent(Message message) {
		System.out.println("Out: " + message.getNumber() + " - " + message.getContent());
	}
}
