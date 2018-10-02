import java.net.*;
import java.util.*;
import java.io.*;

class ServerTCP{

  public static void main(String args[]){

    ServerSocket serverSocket = null;

    Socket clientSocket = null;

    BufferedReader reader = null;
    PrintWriter writer = null;

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    int porta;

    try{

      System.out.println("Inserisci numero di porta su cui attendere: ");
      String numeroPorta = input.readLine();
      porta = Integer.parseInt(numeroPorta);

      System.out.println("Creazione socket...");
      serverSocket = new ServerSocket(porta);

      System.out.println("Attesa conneziossione client...");
      clientSocket = serverSocket.accept();

      System.out.println("Connesso: " + clientSocket);

      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      writer = new PrintWriter(clientSocket.getOutputStream());

      int valore;
      int somma = 0;

      do{

        String numero = new String(reader.readLine());

        valore = Integer.parseInt(numero.trim());

        somma = somma + valore;

      }while(valore != 0);

      writer.println(somma);
      writer.flush();

      writer.close();
      reader.close();
      serverSocket.close();
      clientSocket.close();

    }
    catch(Exception e){
      System.out.println("Server error: " + e);
    }

  }

}
