package org.alex.activitat2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientChat {

  // adreça IP del servidor
  private static final String SERVER_IP = "localhost";
  private static final int SERVER_PORT = 5601;
  private static final String EXIT_MSG = "Sortir";
  final String HOST = "127.0.0.1";
  private int puerto;
  private String nomClient;
  private boolean teNomClient;

  public ClientChat(int puerto) {
    this.puerto = puerto;
  }

  public static void main(String[] args) {
    ClientChat clientChat = new ClientChat(ServidorChat.PORT_DEFAULT);
    clientChat.escriureChat();
  }

  public int getPuerto() {
    return puerto;
  }

  public void setPuerto(int puerto) {
    this.puerto = puerto;
  }

  public String getNomUser() {
    return nomClient;
  }

  public void setNomUser(String nomUser) {
    this.nomClient = nomUser;
  }

  public void escriureChat() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.now();

    try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {

      System.out.println("Connectat a ServidorChat: " + dateTimeFormatter.format(localDateTime));

      System.out.println("IP del ServidorChat: " + socket.getInetAddress());
      System.out.println("Introdueix el nom d'usuari");

      teNomClient = false;

      BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

      PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

      String missatgeClient;
      while ((missatgeClient = consoleIn.readLine()) != null) {
        if (!teNomClient) {
          printWriter.println(missatgeClient);
          teNomClient = true;
          System.out.printf("Benvingut al chat %s%n", missatgeClient);
        }
        printWriter.println(missatgeClient);
        if (missatgeClient.equals(EXIT_MSG)) {
          break;
        }
      }

      System.out.println("Connexió amb el chat tancada");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
