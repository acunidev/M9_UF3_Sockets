package org.alex.activitat3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientChatV2 {

  // adreça IP del servidor
  private static final String SERVER_IP = "localhost";
  private static final int SERVER_PORT = 5601;
  private static final String EXIT_MSG = "Sortir";
  private String nomClient;

  public static void main(String[] args) {
    ClientChatV2 clientChat = new ClientChatV2();
    clientChat.escriureChat();
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
      BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
      PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

      nomClient = consoleIn.readLine();
      printWriter.println(nomClient);

      System.out.printf("Benvingut al chat, %s!%n", nomClient);
      System.out.println("Introdueix les teves paraules:");

      BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // manté la connexió oberta i llegeix/escriu dades contínuament
      while (true) {
        String missatge = consoleIn.readLine();
        printWriter.println(missatge);
        if (missatge.equals(EXIT_MSG)) {
          break;
        }
        String missatgeServidor = socketIn.readLine();
        System.out.printf("%s%n", missatgeServidor);
      }

      System.out.println("Connexió amb el chat tancada");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
