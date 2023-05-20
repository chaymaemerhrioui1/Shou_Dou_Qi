package com.network;

import com.game.classes.Echiquier;
import com.game.classes.Piece;
import com.game.tools.MoveException;

import java.net.*;
import java.util.Scanner;


import java.io.*;

public class ClientProg {


	// initialize socket and input output streams
	private Socket socket = null;

	private ObjectOutputStream out;

	private ObjectInputStream in;
	private static int direction;



	private static ClientProg instance;

	// constructor to put ip address and port
	private ClientProg(String address, int port) {

		try {

			socket = new Socket(address, port);

			// sends output to the socket
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			System.out.println("Connected to server");

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
			obj = in.readObject();

		} catch (Exception ex) {
			throw new NetworkException(ex);
		}
		return obj;
	}


	public static void main(String args[]) {

		Echiquier echiquier = new Echiquier();

		Scanner sc = new Scanner(System.in);
		System.out.println("please entrer the network IP Adresse of the server number (for localhost the ip adresse is: 127.0.0.1)");
		String ipAdresse = sc.next().trim();
		System.out.println("please entrer the network port number user by the server (Example: 5000)");
		int port = sc.nextInt();

		//connect to server
		ClientProg client = ClientProg.getConnnection(ipAdresse, port);
		Scanner s = new Scanner(System.in);
		while (true) {
			//System.out.println("Pleaser write your message:");
			//read message
			//String line = s.nextLine();
			//send message

			//Object rep = client.send(line);
			//print server response
			//System.out.println(rep);

			System.out.println(echiquier.showEchiquier());
			if (echiquier.getTurn() == -1){
				System.out.println("saisir votre Commande player"+echiquier.getTurn());
				Scanner scanner = new Scanner(System.in);
				String command = scanner.nextLine();
				Object rep1 = client.send(command);

				String[] trait = command.split(":");

				// echiquier.switchPlayer();
				Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
				if (choosenPiece != null && trait.length == 2) {
					System.out.println("you choose : " + choosenPiece);
					System.out.println("to make this move " + trait[1]);

					if ("h".equals(trait[1])) {
						direction = 1;
					} else if ("b".equals(trait[1])) {
						direction = 2;
					} else if ("d".equals(trait[1])) {
						direction = 3;
					} else if ("g".equals(trait[1])) {
						direction = 4;
					}



					try {

						choosenPiece.movePiece(direction, 0);
					} catch (MoveException | NullPointerException e) {
						System.out.println("Cant Move");
					}

				} else {
					System.out.println("Cant Find Piece Or command not correct");
				}

			}

			Object rep3=client.send(echiquier.showEchiquier());
			System.out.println(rep3);


		}
	}


}