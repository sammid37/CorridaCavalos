import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
  private int id; // Identificador do cavalo para o apostador
  private String name;
  private int distanceTravelled = 0; 
  private int place = 1; // contador para identificar a colocação do cavalo
  private int bets_num;
  private float total_bet_value;
  private List<Horse> horses;

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

  public String getHorseName() { return name; }
  public void setHorseName(String name) { this.name = name;}

  public int getHorseId() { return id;}
  private void setHorseId(int id) { this.id = id;}

  // Construtores
  Horse () {}
  Horse(int idHorse, String nameHorse) {
    setHorseId(idHorse); 
    setHorseName(nameHorse);
  }

  // Método que retorna a lista de cavalos que estão na corrida
  // p/ torná-la acessível às outras classes
  public List<Horse> getHorseList() {
    horses = new ArrayList<Horse>();
    horses.add(new Horse(1, "Pé de Pano"));
    horses.add(new Horse(2, "Bala no Alvo"));
    horses.add(new Horse(3, "Ponyta"));
    horses.add(new Horse(4, "Ventania"));
    horses.add(new Horse(5, "Spirit"));
    horses.add(new Horse(6, "Epona"));
    horses.add(new Horse(7, "Sortudo"));  
    return horses;
  }

  // Método que retorna a mensagem da thread em execução 
  static void threadMessage(String message) {
    String threadName = Thread.currentThread().getName();
    System.out.format("%s: %s%n",
      threadName, // nome da thread (ex: cavalo1, main, etc)
      message); // mensagem
  }

  public void run() {
    try {
      gate.await();
      
      while (distanceTravelled < 100) {
        Thread.sleep(4000);
        distanceTravelled += this.gallop();

        // Passa a dimensão da pista
        if (distanceTravelled < 100) {
          threadMessage(name + " percorreu " + distanceTravelled + " m.");
        } else {
          threadMessage(name + " cruzou a linha de chegada em " + place + "º lugar");
          place++;
        }
      }
    } catch (InterruptedException e) {
      threadMessage("Não acabei!");
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
