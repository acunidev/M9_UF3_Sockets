package org.alex;

import java.net.UnknownHostException;
import org.alex.activitat1.NetUtils;

public class Main {


  public static void main(String[] args) throws UnknownHostException {

    System.out.println("Hello world!");

    String[] ips = {
        "google.com",
        "google.es",
        "www.upv.es",
        "www.wikipedia.es"
    };

    NetUtils.mostrarIpPerNom(ips);
  }
}