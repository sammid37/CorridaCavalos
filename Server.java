import java.io.*;
import java.net.*;

public class Server {
  private static String ip = "localhost";
  private static int port = 9090;

  public BufferedReader reader;
  public PrintWriter writer;

  public ServerSocket serverSocket;

  public static void main(String[] args) { // Main - Cria o servidor
    try {
      Server server = new Server();
      server.listener(server);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Server() throws IOException{ // Método construtor do servidor
    System.out.println("---------------------------------");
    System.out.println("<< CRIANDO SERVIDOR >>");

    try {
      serverSocket = new ServerSocket(port);
        System.out.println("<< SERVIDOR ABERTO E ACEITANDO CLIENTES NA PORTA " + serverSocket.getLocalPort() + " >>");
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
    
  public void listener(Server server) throws IOException{ // listener() aceita o cliente, abre as streams de read/write e executa o jogo (Race)
    Socket socket = null;

    try {
        socket = serverSocket.accept(); // aceita o cliente
        System.out.println("<< CLIENTE ACEITO NA PORTA " + socket.getLocalPort() + " >>");

        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //criando stream de output para comunicar-se com o cliente
        this.writer = new PrintWriter(socket.getOutputStream(), true); //criando stream de input para comunicar-se com o cliente

        writer.println("\n\n");

        try { 
          Race race = new Race(server); // cria objeto da corrida e inicia o menu
          race.menu();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          server.close(); // fecha o servidor
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void close(){
    try{
      System.out.println("<< FECHANDO SERVIDOR >>");
      System.out.println("---------------------------------");
      serverSocket.close();
      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
          
  }
}