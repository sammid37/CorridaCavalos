import java.util.Random;

public class Horse implements Runnable {
  private int id;
  private String name;
  private int distanceTravelled = 0;
  private static int place = 1; // contador para identificar a colocação do cavalo
  private int bets_num;
  private float total_bet_value;
  private Server server;
  private Boolean winner; // inicializado com false


  // Valor total de apostas em um cavalo
  public float getTotal_bet_value() { return total_bet_value; }
  public void setTotal_bet_value(float total_bet) { this.total_bet_value = total_bet; }

  // Total de apostas em um cavalo
  public int getBets_num() { return bets_num; }
  public void setBets_num(int bets_num) { this.bets_num = bets_num; }

  // Id do cavalo
  public int getHorseId() { return id; }
  private void setHorseId(int id) { this.id = id; }

  // Nome do cavalo
  public String getHorseName() { return name; }
  public void setHorseName(String name) { this.name = name; }

  // Se o cavalo ganhou a corrida ou não
  public Boolean isWinner() { return winner; }
  protected void setWinner(Boolean winner) { this.winner = winner; }

  // Construtor
  Horse(int idHorse, String nameHorse, Server server) {
    setHorseId(idHorse);
    setHorseName(nameHorse);
    setWinner(false); // true quando ganha a corrida em 1º lugar 
    this.server = server;
  }

  // Método que retorna a mensagem da thread (Thread-0: <nome> <metros percorridos>)
  static void threadMessage(String message, Server server) {
    String threadName = Thread.currentThread().getName();
    String format = "%s: %s%n";

    System.out.format("%s: %s%n",
      threadName,
      message
    );

    server.writer.format(format, threadName, message);
  }

  // Método run, acionado quando a thread é inicializada
  public void run() {
    try {
      // Enquanto não percorrer a distância da posta, ele continua a correr
      while (distanceTravelled < 100) {
        Thread.sleep(4000); // a thread dorme por 4s
        distanceTravelled += this.gallop(); // atualiza a distancia percorrida

        // Verifica se cruzou a linha de chegada
        if (distanceTravelled < 100) {
          threadMessage(name + " percorreu " + distanceTravelled + " m.", server);
        } else {
          // Se o cavalo ficar em 1º lugar, o cavalo é declarado como vencedor
          if (place == 1) {
            setWinner(true); 
          }

          // Quando chega na linha de chegada, informa a colocação do cavalo na corrida
          threadMessage(name + " cruzou a linha de chegada em " + place + "º lugar", server);
          place++;
        }
      }
    } catch (InterruptedException e) {
      threadMessage("Interrompido", server);
    } 
  }

  // Método para simular a alteração de velocidade dos cavalos
  private int gallop() {
    Random rand = new Random();
    int maxSpeed = 20;
    int minSpeed = 5;

    return rand.nextInt(maxSpeed - minSpeed) + minSpeed;
  }
}
