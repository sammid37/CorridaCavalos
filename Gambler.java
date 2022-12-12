import java.util.Scanner;

public class Gambler {
  private String name;
  private float wallet; // saldo
  private float bet;
  private float reward;
  private Boolean winner;
  private Horse horse; // cavalo da aposta

  // Get e set do nome do apostador
  public String getName() { return name; }
  private void setName(String name) { this.name = name; }

  // Get e set da carteira(saldo) do apostador
  public float getWallet() { return wallet; }
  protected void setWallet(float wallet) { this.wallet = wallet; }

  // Get e set do valor da aposta do apostador
  public float getBet() { return bet; }
  protected void setBet(float bet) { this.bet = bet; }

  // Get e set do recompensa do apostador
  public float getReward() { return reward; }
  protected void setReward(float reward) { this.reward = reward; }

  // Get e set estado do jogador
  public Boolean isWinner() { return winner; }
  protected void setWinner(Boolean winner) { this.winner = winner; }

  // Get e set do cavalo do apostador
  public Horse getHorse() { return horse; }
  private void setHorse(Horse horse) { this.horse = horse; }

  // Construtor
  public Gambler(Horse horse, Server server) {
    Scanner sc = new Scanner(System.in);

    setWinner(false); // true quando o cavalo apostado cruza a linha de chegada em 1º lugar 
    server.writer.println("\n>> Nome do jogador: ");
    server.writer.println("WAITFORANSWER");
    try {
      setName(server.reader.readLine());
    } catch (Exception e) {
      e.printStackTrace();
    }

    server.writer.println("\n>> Saldo: ");
    server.writer.println("WAITFORANSWER");
    try {
      setWallet(Float.parseFloat(server.reader.readLine()));
    } catch (Exception e) {
      e.printStackTrace();
    }
   
    // Verifica se o valor da aposta excede o valor da carteira
    Boolean valid_value = false;
    while (valid_value == false) {
      server.writer.println("\n>> Valor da aposta: ");
      server.writer.println("WAITFORANSWER");
      
      try {
        float bet_value = Float.parseFloat(server.reader.readLine());
        // Precisa ser um número positivo e <= ao saldo
        if (bet_value <= this.getWallet() && bet_value != 0) {
          setBet(bet_value);
          valid_value = true;
          break;
        } else {
          server.writer.println("O valor informado excede o valor da carteira. Tente novamente.");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    setHorse(horse);  // Seta o cavalo quando o valor da aposta é aceito.

    // Mensagem do processamento
    server.writer.println("\nOlá, " 
    + getName() + "! Você apostou R$" 
    + getBet() + " no cavalo " 
    + horse.getHorseName() + ".");
  }
}
