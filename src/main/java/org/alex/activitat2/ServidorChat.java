package org.alex.activitat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

  static final int PORT_DEFAULT = 5601;
  private static final String EXIT_MSG = "Sortir";
  private boolean serverIniciat;
  private int puerto;
  private String nomClient;
  private boolean teNomClient;

  public ServidorChat(int puerto, String nomClient) {
    this.puerto = puerto;
    this.nomClient = nomClient;
  }

  public ServidorChat(int puerto) {
    this.puerto = puerto;
  }

  public static void main(String[] args) {
    ServidorChat servidorChat = new ServidorChat(ServidorChat.PORT_DEFAULT);
    servidorChat.llegirChat();
  }

  public void llegirChat() {

    try (ServerSocket serverSocket = new ServerSocket(ServidorChat.PORT_DEFAULT)) {
      System.out.println("Servidor escoltant pel port " + ServidorChat.PORT_DEFAULT);

      while (true) {
        Socket clientChatSocket = serverSocket.accept();
        serverIniciat = true;
        teNomClient = false;
        System.out.println("Client connectat des de l'adreça " + clientChatSocket.getInetAddress());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientChatSocket.getInputStream()));

        String missatgeClient = bufferedReader.readLine();
        while ((missatgeClient = bufferedReader.readLine()) != null) {
          if (!teNomClient) {
            nomClient = missatgeClient;
            teNomClient = true;
            continue;
          }
          System.out.printf("%s: %s%n", nomClient, missatgeClient);
          if (missatgeClient.equals(EXIT_MSG)) {
            break;
          }
        }
        if (!serverIniciat) {
          break;
        }

        System.out.printf("Chat amb %s tancat%n :(", nomClient);
        System.out.println("Connexió amb el client tancada");
        break;

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}

