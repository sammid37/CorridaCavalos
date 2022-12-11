import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
  private int id;
  private String name;
  private int distanceTravelled = 0;
  private static int place = 1; // contador para identificar a colocação do cavalo
  private int bets_num;
  private float total_bet_value;
  private Server server;

  static CyclicBarrier gate = null; // Permite que as threads iniciem "juntas" com o mesmo tempo possível

  public float getTotal_bet_value() {
    return total_bet_value;
  }
  
  public void setTotal_bet_value(float total_bet) {
    this.total_bet_value = total_bet;
  }

  public int getBets_num() {
    return bets_num;
  }
  public void setBets_num(int bets_num) {
    this.bets_num = bets_num;
  }

  
  public String getHorseName() {
    return name;
  }

  public void setHorseName(String name) {
    this.name = name;
  }

  public int getHorseId() {
    return id;
  }

  private void setHorseId(int id) {
    this.id = id;
  }

  Horse(int idHorse, String nameHorse, Server server) {
    setHorseId(idHorse);
    setHorseName(nameHorse);
    this.server = server;
  }

  static void threadMessage(String message, Server server) {
    String threadName = Thread.currentThread().getName();
    String format = "%s: %s%n";
    System.out.format("%s: %s%n",
        threadName,
        message);
    server.writer.format(format, threadName, message);
    
  }

  public void run() {
    try {
      gate.await();

      while (distanceTravelled < 100) {
        Thread.sleep(4000);
        distanceTravelled += this.gallop();

        if (distanceTravelled < 100) {
          threadMessage("🐎" + name + " percorreu " + distanceTravelled + " m.", server);
        } else {
          threadMessage("🏆" + name + " cruzou a linha de chegada em " + place + "º lugar", server);
          place++;
        }
      }
    } catch (InterruptedException e) {
      threadMessage("I wasn't done!", server);
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
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
