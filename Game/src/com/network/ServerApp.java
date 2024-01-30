package com.network;

import java.net.*;
import java.io.*;



public class ServerApp {
	// initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;

	/** flux d'entrée (pour la reception) assoicé à la socket associé au client */
	private ObjectInputStream in;

	/** flux de sortie (pour l'envoi) assoicé à la socket associé au client */
	private ObjectOutputStream out;

	// sign to start
	private boolean start1;

	public boolean isNoProblem() {
		return noProblem;
	}
	boolean noProblem;

	public ObjectInputStream getIn() {
		return in;
	}
	public void setNoProblem(boolean noProblem) {
		this.noProblem = noProblem;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public Socket getSocket() {
		return socket;
	}
	// store the message in an attribute
	private String meessage;

	public String getMeessage() {
		return meessage;
	}
	public void setMeessage(String meessage) {
		this.meessage = meessage;
	}
	// constructor with port
	public ServerApp(int port) throws Exception {

		noProblem = true;


		server = new ServerSocket(port);
		System.out.println("Server started");


			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted !");

			start1 = true;

			// takes input from the client socket
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			noProblem = true;








	}
	public void sendMessageToClient(String message) throws IOException {

		out.writeObject(message);

	}



	public boolean isStart1() {
		return start1;
	}
}