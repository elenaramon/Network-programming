import java.io.*;
import java.util.*;
import java.net.*;

class ClientChat {

	public static void main(String[] args) throws Exception
	{

		try {

      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Inserisci indirizzo IP del server: ");
      String indirizzoIP = input.readLine();

      System.out.println("Inserisci numero di porta: ");
      String numeroPorta = input.readLine();
      int porta = Integer.parseInt(numeroPorta);

			Socket clientSocket = new Socket(indirizzoIP, porta);
      System.out.println("Connsesso: " + clientSocket);

			ClientSend sendChat = new ClientSend(clientSocket);
			Thread threadSend =new Thread(sendChat);
			threadSend.start();

			ClientReceive receiveChat = new ClientReceive(clientSocket);
			Thread threadReceive = new Thread(receiveChat);
			threadReceive.start();			

		}
    catch (Exception e) {
      System.out.println(e);
    }

	}

}

class ClientReceive implements Runnable{

  Socket clientSocket;

  public ClientReceive(Socket clientSocket){

    this.clientSocket = clientSocket;

  }

  public void run(){

    BufferedReader reader = null;
    String message;

    try {

      reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

      while(true){

        message = reader.readLine();

        if(message == null || message.equals("")){
          break;
        }

        System.out.println(message);

      }

       this.clientSocket.close();

    }
    catch(Exception e) {
        System.out.println(e);
    }

  }

}


class ClientSend implements Runnable{

  Socket clientSocket;

  public ClientSend(Socket clientSocket){
    this.clientSocket = clientSocket;
  }

  public void run(){

    PrintWriter writer = null;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String message;

    try {

      writer = new PrintWriter(this.clientSocket.getOutputStream());

      while(true){

        message = input.readLine();

        writer.println(message);
        writer.flush();

        if(message == null || message.equals("")){
          break;
        }

      }

       this.clientSocket.close();

    }
    catch(Exception e) {
        System.out.println(e);
    }

  }

}
