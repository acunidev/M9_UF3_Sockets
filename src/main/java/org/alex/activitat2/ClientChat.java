package org.alex.activitat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChat {

  final String HOST = "127.0.0.1";
  private int puerto;

  public ClientChat(int puerto) {
    this.puerto = puerto;
  }

  public static void main(String[] args) {
    ClientChat clientChat = new ClientChat(ServidorChat.PORT_DEFAULT);
    clientChat.escriureChat();
  }

  public void escriureChat() {
    String serverIP = "localhost"; // adreça IP del servidor
    int serverPort = 5601; // port del servidor
    String exitMsg = "Sortir"; // missatge de sortida del bucle

    try {
      // creem el socket del client i ens connectem al servidor
      Socket socket = new Socket(serverIP, serverPort);
      System.out.println("Connectat al servidor " + socket.getInetAddress());

      // creem un BufferedReader per llegir les dades de la consola
      BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

      // creem un PrintWriter per enviar dades al servidor
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      // llegim linies de la consola fins que introduïm el missatge de sortida
      String line;
      while ((line = consoleIn.readLine()) != null) {
        out.println(line);
        if (line.equals(exitMsg)) {
          break;
        }
      }

      // tanquem la connexió amb el servidor
      socket.close();
      System.out.println("Connexió amb el servidor tancada");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
