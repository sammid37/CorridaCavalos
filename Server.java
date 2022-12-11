import java.io.*;
import java.net.*;

public class Server {
    public static String ip = "localhost";
    public static int port = 9090;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private ServerSocket server;

    public int numberPlayers;
    public int numberPlayersMax;

    public Server() throws IOException{
        System.out.println("----- Servidor -----");
        numberPlayers = 0;
        numberPlayersMax = 7;
        
        try {
            server = new ServerSocket(port);
            System.out.println("Servidor aberto, aceitando clientes na porta " + server.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("oi");
        try {
            Server server = new Server();
            server.listenAndRegister();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void listenAndRegister() throws IOException{
        Socket socket = null;
        try {
            socket = server.accept();
            System.out.println("Cliente aceito na porta " + socket.getLocalPort());

            numberPlayers++;

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.write("------ Registrando Apostador n° " + numberPlayers + "------");
            //new Gambler();

            while(numberPlayers < numberPlayersMax){
                writer.write("Registrar mais um apostador?");
                writer.write("1 - Sim");
                writer.write("2 - Não");

                String message = reader.readLine();

                if("2".equals(message) && numberPlayers < 2){
                    writer.write("Não atingiu a quantidade mínima de apostadores (2), tente novamente");
                    continue;
                }
                else if("1".equals(message)){
                    writer.write("------ Registrando Apostador n° " + numberPlayers + "------");
                    //new Gambler();
                    numberPlayers++;
                }
                else{
                    writer.write(numberPlayers + " apostadores registrados!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
