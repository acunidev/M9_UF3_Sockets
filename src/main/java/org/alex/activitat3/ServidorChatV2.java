package org.alex.activitat3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServidorChatV2 {

  private static final int PORT_DEFAULT = 5601;
  private static final String EXIT_MSG = "Sortir";
  private int puerto;
  private List<ClientHandler> clients;
  private boolean serverIniciat;

  public ServidorChatV2(int puerto) {
    this.puerto = puerto;
    clients = new ArrayList<>();
  }

  public ServidorChatV2() {
    this(PORT_DEFAULT);
  }

  public static void main(String[] args) {
    ServidorChatV2 servidorChat = new ServidorChatV2();
    servidorChat.iniciar();
  }

  public void iniciar() {
    try (ServerSocket serverSocket = new ServerSocket(puerto)) {
      System.out.println("Servidor escoltant pel port " + puerto);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Client connectat des de l'adreça " + socket.getInetAddress());
        ClientHandler clientHandler = new ClientHandler(socket);
        clients.add(clientHandler);
        clientHandler.start();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private class ClientHandler extends Thread {

    private Socket socket;
    private String nomClient;
    private boolean teNomClient;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ClientHandler(Socket socket) {
      this.socket = socket;
      teNomClient = false;
      try {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      try {
        String missatgeClient;
        while ((missatgeClient = bufferedReader.readLine()) != null) {
          if (!teNomClient) {
            nomClient = missatgeClient;
            teNomClient = true;
            System.out.printf("Benvingut al chat %s%n", nomClient);
            enviarMissatge(nomClient + " ha entrat al chat");
          } else {
            if (missatgeClient.equals(EXIT_MSG)) {
              break;
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.printf("%s: %s%n", nomClient, missatgeClient);
            enviarMissatge(dateTimeFormatter.format(localDateTime) + " " + nomClient + ": " + missatgeClient);
          }
        }
        socket.close();
        clients.remove(this);
        System.out.printf("%s ha sortit del chat%n", nomClient);
        enviarMissatge(nomClient + " ha sortit del chat");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void enviarMissatge(String missatge) {
      for (ClientHandler client : clients) {
        client.printWriter.println(missatge);
      }
    }
  }
}
