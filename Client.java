import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  private static String ip = "localhost";
  private static int port = 9090;
  private Socket socket;

  public BufferedReader reader;
  public PrintWriter writer;

  public Scanner scan;
  public static void main(String[] args) { // Main - executado o tempo inteiro recebendo mensagens do servidor
    Client client = new Client();

    while(true){
      try {
        String msg = client.reader.readLine();  // lê mensagem do servidor
        if(msg == null){
          break;
        } else if ("WAITFORANSWER".equals(msg)) { // se a mensagem for "WAITFORANSWER", é liberado o recurso do teclado para o cliente
          String scanned = client.scan.next();
          client.writer.println(scanned);  // envia mensagem para o servidor
        } else {
          System.out.println(msg); // se for uma mensagem normal, a imprime no console do cliente
        }
      } catch (Exception e) {
        e.printStackTrace();
        
      }
    }
    try {
      client.socket.close();  // fecha o socket do cliente ao terminar o programa
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  public Client() { // Método construtor para conectar o cliente ao servidor
    System.out.println("---------------------------------");
    System.out.println("<< CONECTANDO AO SERVIDOR >>");
    try {
      socket = new Socket(ip, port);
      scan = new Scanner(System.in); //Scanner para pegar o input do teclado

      this.writer = new PrintWriter(socket.getOutputStream(), true); //criando stream de input para comunicar-se com o servidor
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //criando stream de output para comunicar-se com o servidor

      System.out.println("<< CONECTADO! >>");
      System.out.println("---------------------------------\n\n");

    } catch (IOException e) {
      e.printStackTrace();  
    }
  }
}
