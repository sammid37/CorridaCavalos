import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
  private final int id; // Identificador do cavalo para o apostador
  private final String name;
  private int distanceTravelled = 0; 
  private static int place = 1; // contador para identificar a colocação do cavalo

  static CyclicBarrier gate = null; // Permite que as threads iniciem "juntas" com o mesmo tempo possível
  
  Horse(int idHorse, String nameHorse) {
    id = idHorse; 
    name = nameHorse;
  }
  
static void threadMessage(String message) {
    String threadName = Thread.currentThread().getName();
    System.out.format("%s: %s%n",
      threadName,
      message);
  }

  public void run() {
    try {
      gate.await();
      
      while (distanceTravelled < 100) {
        Thread.sleep(4000);
        distanceTravelled += this.gallop();

        if (distanceTravelled < 100) {
          threadMessage(name + " has galloped " + distanceTravelled + " m.");
        } else {
          // String placeSuffix;
          // if (place == 1) {placeSuffix = "st";} 
          // else if (place == 2) {placeSuffix = "nd";} 
          // else if (place == 3) {placeSuffix = "rd";} 
          // else {placeSuffix = "th";}
          threadMessage(name + " cruzou a linha de chegada em " + place + "º lugar");
          place++;
        }
      }
    } catch (InterruptedException e) {
      threadMessage("I wasn't done!");
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