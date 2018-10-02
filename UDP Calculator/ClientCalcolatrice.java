//CLIENT UDP calcolatrice

import java.io.*;
import java.lang.*;
import java.net.*;

class UDPClient {
	public static void main (String args[]) throws Exception{

		try{

			DatagramSocket clientSocket = new DatagramSocket();
			DatagramPacket sendP;
			DatagramPacket receivedP;
			DatagramPacket ackP;

			int numero;
			String sentence = "";

			InetAddress IPAddress = InetAddress.getByName("localhost");

			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			do{

				byte[] sendData = new byte[1024];

				byte[] ackData = new byte[1024];

				String numero1 = input.readLine();

				do{
					sendData = numero1.getBytes();

					sendP = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

					clientSocket.send(sendP);

					numero = Integer.parseInt(numero1.trim());

					try{

						ackP = new DatagramPacket(ackData, ackData.length);

						clientSocket.receive(ackP);

						clientSocket.setSoTimeout(100);

						sentence = new String(ackP.getData());

						System.out.println("RECEIVED: " + sentence);

					}
					catch(SocketTimeoutException e){
						 System.err.println("Client: errore "+e);
					}
				}while(sentence.compareTo("") == 0);

				sentence = "";

			} while(numero != 0);

			byte[] receivedData = new byte[1024];

			receivedP = new DatagramPacket(receivedData, receivedData.length);

			clientSocket.receive(receivedP);

			String somma = new String(receivedP.getData());

			System.out.println("Somma: " + somma);

			clientSocket.close();

		}
		catch(Exception u){
			 System.err.println("Client: errore "+u);
		}

	}
}
