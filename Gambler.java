import java.util.List;
import java.util.Scanner;

public class Gambler {
  private String name;
  private float wallet; // saldo
  private float bet; 
  private float reward;
  private Boolean winner;
  private Horse horse; // cavalo da aposta

  // Get e set do nome do apostador
  public String getName() { return name;}
  private void setName(String name) {this.name = name; }

  // Get e set da carteira(saldo) do apostador
  public float getWallet() {return wallet;}
  protected void setWallet(float wallet) {this.wallet = wallet;}

  // Get e set do valor da aposta do apostador
  public float getBet() { return bet;}
  protected void setBet(float bet) { this.bet = bet; }

  // Get e set do recompensa do apostador
  public float getReward() { return reward;}
  protected void setReward(float reward) { this.reward = reward;}

  // Get e set estado do jogador
  public Boolean isWinner() { return winner; }
  protected void setWinner(Boolean winner) { this.winner = winner; }

  // Get e set do cavalo do apostador
  public Horse getHorse() { return horse; }
  private void setHorse(Horse horse) { this.horse = horse; }

  // Construtores
  public Gambler() {
    Scanner inputHandle = new Scanner(System.in);
    // Informando atributos do apostador
    System.out.print("Nome do jogador: ");
    setName(inputHandle.nextLine());

    System.out.print("Saldo: ");
    setWallet(inputHandle.nextFloat());

    System.out.print("Valor da aposta: ");
    setBet(inputHandle.nextFloat());

    // Informa o ID do Cavalo a ser apostado
    System.out.print("Número do cavalo apostado (1 à 7): ");
    int numero = inputHandle.nextInt();
    int indice = numero - 1;

    horse = new Horse();
    List<Horse> horseList = horse.getHorseList(); // Salva a lista original
    // Observação: não é feita a verificação se o número informado é <= horseList.size()
    if (numero == horseList.get(indice).getHorseId()) {
      System.out.println("Você selecionou o cavalo " + horseList.get(indice).getHorseName());
      setHorse(horseList.get(indice));
    }

    inputHandle.close(); // fecha o auxiliar de input
  }

  public Gambler(String nameGambler, float walletGambler, float betValue) {
    setName(nameGambler);
    setWallet(walletGambler);
    setBet(betValue);
  }
}