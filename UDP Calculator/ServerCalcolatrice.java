//CLIENT UDP calcolatrice

import java.io.*;
import java.lang.*;
import java.net.*;

class UDPServer {
	public static void main (String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		DatagramPacket sendP;
		DatagramPacket receivedP;
		DatagramPacket ackP;

		int numero;
		int somma = 0;

		byte[] sendData = new byte[1024];

		System.out.println("Attesa di connessione...");

			do {

				byte[] receivedData = new byte[1024];

				receivedP = new DatagramPacket(receivedData, receivedData.length);

				serverSocket.receive(receivedP);

				String numero1 = new String(receivedP.getData());

				numero = Integer.parseInt(numero1.trim());

				System.out.println("Numero: " + numero);

				InetAddress IPAddress = receivedP.getAddress();

				int port = receivedP.getPort();

				byte[] ackData = new byte[1024];

				String ackMessage = "Numero ricevuto";

				ackData = ackMessage.getBytes();

				ackP = new DatagramPacket(ackData, ackData.length, IPAddress, port);

				serverSocket.send(ackP);

				somma = somma + numero;

			} while (numero != 0);

		InetAddress IPAddress = receivedP.getAddress();

		int port = receivedP.getPort();

		sendData = (Integer.toString(somma)).getBytes();

		sendP = new DatagramPacket(sendData, sendData.length, IPAddress, port);

		serverSocket.send(sendP);

	}
}
