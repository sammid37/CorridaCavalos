import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Gambler {
  private String name;
  private float wallet; // saldo
  private float bet;
  private float reward;
  private Boolean winner;
  private Horse horse; // cavalo da aposta

  private Client client;

  // Get e set do nome do apostador
  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  // Get e set da carteira(saldo) do apostador
  public float getWallet() {
    return wallet;
  }

  protected void setWallet(float wallet) {
    this.wallet = wallet;
  }

  // Get e set do valor da aposta do apostador
  public float getBet() {
    return bet;
  }

  protected void setBet(float bet) {
    this.bet = bet;
  }

  // Get e set do recompensa do apostador
  public float getReward() {
    return reward;
  }

  protected void setReward(float reward) {
    this.reward = reward;
  }

  // Get e set estado do jogador
  public Boolean isWinner() {
    return winner;
  }

  protected void setWinner(Boolean winner) {
    this.winner = winner;
  }

  // Get e set do cavalo do apostador
  public Horse getHorse() {
    return horse;
  }

  private void setHorse(Horse horse) {
    this.horse = horse;
  }

  // Construtor
  public Gambler(Horse horse) {
    Scanner sc = new Scanner(System.in);
    // Informando atributos do apostador
    System.out.print("Nome do jogador: ");
    setName(sc.nextLine());

    System.out.print("Saldo: ");
    setWallet(sc.nextFloat());

    System.out.print("Valor da aposta: ");
    setBet(sc.nextFloat());

    setHorse(horse);
    System.out.println("\nOlá, " + getName() + "! Você apostou R$" + getBet() + " no cavalo " + horse.getHorseName() + ".");
  }

  // Conexão Client-Server
  
  //Método para conectar-se ao servidor
  public void connectToServer(){
    client = new Client();
  }

  //Classe Client
  private class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client() {
      System.out.println("----- Conexão de Cliente -----");
      try {
        socket = new Socket("localhost", 9090);

        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}