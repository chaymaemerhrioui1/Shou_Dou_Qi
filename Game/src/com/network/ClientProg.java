package com.network;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientProg {


	// initialize socket and input output streams
	private Socket socket = null;

	private ObjectOutputStream out;

	private ObjectInputStream in;

	private boolean start2;



	private static ClientProg instance;

	public void setStart2(boolean start2) {
		this.start2 = start2;
	}

	public boolean isStart2() {
		return start2;
	}

	// constructor to put ip address and port
	private ClientProg(String address, int port) {

		try {

			socket = new Socket(address, port);

			// sends output to the socket
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			System.out.println("Connected to server");
			setStart2(true);

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}

	}

	public static ClientProg getConnnection(String pIpAdress, int port) {

		if (instance == null) {
			instance = new ClientProg(pIpAdress, port);

		}

		return instance;

	}

	public void close() {
		try {
			out.writeObject("end_connection");
			out.flush();
			out.close();
			in.close();
			socket.close();
		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
	}

	public Object send(String pMessage) {

		Object obj;
		try {
			out.writeObject(pMessage);
			out.flush();

			while (true) {
				try {
					obj = in.readObject();
					break; // Break the loop if an object is successfully read
				} catch (ClassNotFoundException e) {
					// Handle class not found exception, if necessary
					e.printStackTrace();
				}
			}

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
		return obj;
	}

	public static void main(String args[]) {


		Scanner sc = new Scanner(System.in);
		System.out.println("please entrer the network IP Adresse of the server number (for localhost the ip adresse is: 127.0.0.1)");
		String ipAdresse =  sc.next().trim();
		System.out.println("please entrer the network port number user by the server (Example: 5000)");
		int port  = sc.nextInt();

		//connect to server
		ClientProg client = ClientProg.getConnnection(ipAdresse, port);
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println("Pleaser write your message:");
			//read message
			String line = s.nextLine();
			//send message
			Object rep = client.send(line);
			//print server response
			System.out.println(rep);

		}

	}
}
