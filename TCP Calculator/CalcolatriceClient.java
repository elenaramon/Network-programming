import java.util.*;
import java.net.*;
import java.io.*;

class TCPClient{

  public static void main(String args[]){

    Socket clientSocket = null;

    BufferedReader reader = null;
    PrintWriter writer = null;

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    try{

      System.out.println("Inserisci indirizzo IP server: ");
      String indirizzoIP = input.readLine();

      System.out.println("Inserisci numero di porta: ");
      String numeroPorta = input.readLine();
      int porta = Integer.parseInt(numeroPorta.trim());

      clientSocket = new Socket(indirizzoIP, porta);

      System.out.println("Socket creata: " + clientSocket);

      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      writer = new PrintWriter(clientSocket.getOutputStream());

      int valore;

      do {

        String numero = input.readLine();
        valore = Integer.parseInt(numero.trim());
        writer.println(valore);
        writer.flush();

      } while (valore != 0);

      String somma = reader.readLine();
      System.out.println("Somma: " + somma);

      reader.close();
      writer.close();
      clientSocket.close();

    }
    catch(Exception e){
      System.out.println("Errore Client: " + e);
    }

  }

}
