package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(2221);
			server.setReuseAddress(true);

			while(true) {
				Socket client = server.accept();
				System.out.println("New client connected: " + client.getInetAddress().getHostName());
				ClientHandler clientSocket = new ClientHandler(client);
				new Thread(clientSocket).start();
			}
		}
		catch (IOException ex) {
			System.out.println("Server failed! Details:\n" + ex.toString());
		}
		finally {
			if(server != null) {
				try {
					server.close();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	private static void logReceived(Message message) {
		System.out.println("Client: " + message.getNumber() + " - " + message.getContent());
	}
	private static void logSent(Message message) {
		System.out.println("Out: " + message.getNumber() + " - " + message.getContent());
	}

	private static class ClientHandler implements Runnable {
		private final Socket client;
		public ClientHandler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			try {
				out = new ObjectOutputStream(client.getOutputStream());
//				out = new PrintWriter(client.getOutputStream(), true);
				in = new ObjectInputStream(client.getInputStream());
//				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String line;
				int n;

				/* implement the protocol */
				Message sent = new Message(client.getPort(), "Ready");
				out.writeObject(sent);
				out.flush();
				logSent(sent);

				try { // Receive an int - number of incoming messages
					Message received = (Message)in.readObject();
					logReceived(received);
					n = received.getNumber();
				}
				catch (Exception ex) {
					System.out.println("Receive failed!");
					throw (new IOException());
				}

				sent = new Message(0, "Ready for messages.");
				out.writeObject(sent);
				out.flush();
				logSent(sent);

				try { // Receive n messages
					for(int i = 1; i <= n; ++i) {
						Message received = (Message)in.readObject();
						logReceived(received);
					}
				}
				catch (Exception ex) {
					System.out.println("Receive failed!");
					throw (new IOException());
				}

				sent = new Message(0, "Finished.");
				out.writeObject(sent);
				out.flush();
				logSent(sent);
			}
			catch (IOException ex) {
				System.out.println("A client handler failed! Details:\n" + ex.toString());
			}
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						client.close();
					}
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
