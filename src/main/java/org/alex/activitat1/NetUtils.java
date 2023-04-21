package org.alex.activitat1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Date;

public class NetUtils {

  public NetUtils() {
  }


  public static void main(String[] args) {
    System.out.println("Hello world!");

    String[] ips = {
        "google.com",
        "google.es",
        "www.upv.es",
        "www.wikipedia.es"
    };

    String[] urls = {
        "https://facebook.com",
        "https://drive.google.com/file/d/1Tdffs86myn5KP91rCIHcmdPZ2ElOUwMl/view",
        "https://ezgif.com/images/format-demo/butterfly.gif",
        "https://cdn.icon-icons.com/icons2/265/PNG/512/JPG_29711.png"};

    System.out.println("Exercici 1");
    System.out.println("Mostrar IP per URL");
    mostrarIpPerNom(ips);
    System.out.println("__________________________");
    System.out.println();
    System.out.println("Exercici 2");
    System.out.println(
        "Mostrar tipus de contingut que té la url segons la capçalera contentType o segons el nom del fitxer.");
    mostrarHeaderGesstypePerUrl(urls);
    System.out.println("__________________________");
    System.out.println();
    System.out.println("Mostrar les mateixes url anteriors ha de donar-nos les capçaleres més "
                       + "habituals com són:");
    mostrarHeaderFieldsFromUrl(urls);
  }

  public static void mostrarHeaderFieldsFromUrl(String[] urls) {
    for (String urlStr : urls) {
      System.out.println(urlStr);
      try {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("httpURLConnection.getResponseCode() = " + conn.getResponseCode());
        System.out.println("httpURLConnection.getResponseMessage() = " + conn.getResponseMessage());
        System.out.println("httpURLConnection.getContentType() = " + conn.getContentType());
        System.out.println("httpURLConnection.getContentEncoding() = " + conn.getContentEncoding());
        System.out.println("httpURLConnection.getContentLength() = " + conn.getContentLength());

        long date = conn.getDate();
        if (date != 0) {
          System.out.println("httpURLConnection.getDate() = " + new Date(date));
        }

        long expiration = conn.getExpiration();
        if (expiration != 0) {
          System.out.println("httpURLConnection.getExpiration() = " + new Date(expiration));
        }

        long lastModified = conn.getLastModified();
        if (lastModified != 0) {
          System.out.println("httpURLConnection.getLastModified() = " + new Date(lastModified));
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      System.out.println();
    }
  }

  public static void mostrarHeaderGesstypePerUrl(String[] urls) {
    for (String urlString : urls) {
      System.out.println(urlString);
      try {
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        String headerType = urlConnection.getContentType();
        String fileName = url.getFile();
        String gessType = null;
        if (fileName.contains(".")) {
          gessType = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        System.out.println("headerType = " + headerType);
        System.out.println("gessType = " + gessType);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      System.out.println();
    }
  }

  /*
   Crea una classe NetUtils que de les adreces:
        • google.com
  • google.es
  • www.upv.es
  • www.wikipedia.es
    mostri les diferents IP’s associades a cada nom. La sortida ha de ser del tipus:
    google.com
  142.250.200.110
      2a00:1450:4003:80e:0:0:0:200e
  */
  public static void mostrarIpPerNom(String[] ips) {

    for (String ip : ips) {
      System.out.println(ip);
      InetAddress[] inetAddresses = new InetAddress[0];
      try {
        inetAddresses = InetAddress.getAllByName(ip);
      } catch (UnknownHostException e) {
        throw new RuntimeException(e);
      }
      for (InetAddress address : inetAddresses) {
        System.out.println("=> " + address.getHostAddress());
      }
      System.out.println();

    }

  }
}
