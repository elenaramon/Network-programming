import java.util.*;
import java.net.*;
import java.io.*;

class ServerChat {

    public static void main(String args[]) throws Exception {

        ServerSocket serverSocket = new ServerSocket(1025);

        while(true){

          Socket clientSocket = serverSocket.accept();
          Server chat = new Server(clientSocket);

        }

    }

}

class Server implements Runnable{

    Socket clientSocket;
    static Map<String,PrintStream> client = new HashMap<>();

    public Server(Socket clientSocket) {

        this.clientSocket = clientSocket;
        Thread chat = new Thread(this);
        chat.start();

    }

    public void run() {

        String message;
        BufferedReader reader = null;
        PrintStream streamer = null;
        String clientNickname = null;
        Set<String> nickIterator = new HashSet<>();

        try {

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            streamer = new PrintStream(clientSocket.getOutputStream());

            streamer.println("Inserisci nickname: ");

            clientNickname = reader.readLine();

            while((client.putIfAbsent(clientNickname, streamer)) != null){
              streamer.println("Scegliere un altro nickname, questo è già occupato...");
              clientNickname = reader.readLine();
            }

            System.out.println(clientNickname + " connected");
            while ((message = reader.readLine()) != null && !message.equals("")) {

                System.out.println(clientNickname + ": " + message);

                nickIterator = client.keySet();

                for(String nick : nickIterator){

                  PrintStream streamerIterator = client.get(nick);
                  if(nick != clientNickname){
                    streamerIterator.println(clientNickname + ": " + message);
                  }

                }

            }

            clientSocket.close();

        }
        catch (Exception e) {
            System.out.println(e);
        }

        for(String nick : nickIterator){
          PrintStream streamerIterator = client.get(nick);
          if(nick != clientNickname){
            streamerIterator.println(clientNickname + " disconnected");
          }
        }

        System.out.println(clientNickname + " disconnected");

        client.remove(clientNickname);

    }

}
