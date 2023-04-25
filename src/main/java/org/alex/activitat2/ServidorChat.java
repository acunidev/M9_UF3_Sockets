package org.alex.activitat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

  protected static final int PORT_DEFAULT = 5601;
  private int puerto;

  public ServidorChat(int puerto) {
    this.puerto = puerto;
  }

  public static void main(String[] args) {
    ServidorChat servidorChat = new ServidorChat(ServidorChat.PORT_DEFAULT);
    servidorChat.llegirChat();
  }

  public void llegirChat() {
    int port = 5601; // port d'escolta del servidor
    String exitMsg = "Sortir"; // missatge de sortida del bucle

    try {
      // creem el ServerSocket per escoltar pel port especificat
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Servidor escoltant pel port " + port);

      while (true) {
        // esperem a que un client es connecti
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connectat des de l'adreça " + clientSocket.getInetAddress());

        // creem un BufferedReader per llegir les dades enviades pel client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // llegim linies fins que rebem el missatge de sortida
        String line;
        while ((line = in.readLine()) != null) {
          System.out.println("Client diu: " + line);
          if (line.equals(exitMsg)) {
            break;
          }
        }

        // tanquem la connexió amb el client
        clientSocket.close();
        System.out.println("Connexió amb el client tancada");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

