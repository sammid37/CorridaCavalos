// Corrida de Cavalos - LP2 (dezembro de 2022)
// Christopher Alec, Mayra Daher, Victoria Grisi, Yan Feitosa e Samantha Medeiros

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class Main {
  /* 
  static void cadastrarAposta() {
    Scanner inputHandle = new Scanner(System.in);

    System.out.println("---- Cadastrar aposta ----");
  
    // Cria uma lista com cada jogador adicionado
    ArrayList<Gambler> GamblersList = new ArrayList<Gambler>();
    GamblersList.add(new Gambler(nomeApostador, valorAposta, carteiraApostador, idCavalo));
    inputHandle.close(); // fecha o auxiliar de input
  }*/
  // public static Horse[] main(String args[], Object Horse) throws InterruptedException, BrokenBarrierException {
  
  public static void main(String args[]) throws InterruptedException, BrokenBarrierException {

    // Recupera os cavalos criados na classe Cavalo dentro do método getHorseList()
    Horse h = new Horse();
    List<Horse> horses = h.getHorseList();

    // MENU
    int contagem_jogadores = 0;
    while (true) {
      System.out.println("---------------------------------");
      System.out.println("Corrida de cavalos!");
      System.out.println("1) Iniciar corrida."); // inicia e exibe resultado ao final
      System.out.println("2) Realizar aposta."); // cadastra novo jogador
      System.out.println("3) Visualizar apostas."); // cadastra novo jogador
      System.out.println("4) Sair do programa!");
      System.out.println("---------------------------------");

      System.out.print("Digite a opção: ");

      int opcao;

      try {
        Scanner input_handle = new Scanner(System.in);
        opcao = input_handle.nextInt();
        // input_handle.close();
      } catch (InputMismatchException exception) {
        System.out.println("O carácter informado é incompatível.");
        continue;
      }

      // Validando opção e chamando métodos
      if (opcao == 4) {
        System.out.println("Corrida encerrada.");
        break;
      } else {
        switch (opcao) {
          case 1:
            // Criar nova pista, 
            // informar a dimensão
            // informar número máximo de cavalos (qtd_raias)
            RaceTrack pista = new RaceTrack(100, 7);

            System.out.println("A corrida vai iniciar!");
            // inicia a corrida
            // Delay, in milliseconds before we interrupt MessageLoop thread (default one
            // hour).
            long patience = 1000 * 60 * 60;

            // If command line argument present, gives patience in seconds.
            if (args.length > 0) {
              try {
                patience = Long.parseLong(args[0]) * 1000;
              } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
              }
            }

            Horse.threadMessage("Starting MessageLoop thread");
            long startTime = System.currentTimeMillis();

            /*Horse[] horses = new Horse[] {
              new Horse(1, "Pé de Pano"),
              new Horse(2, "Bala no Alvo"),
              new Horse(3, "Ponyta"),
              new Horse(4, "Ventania"),
              new Horse(5, "Spirit"),
              new Horse(6, "Epona"),
              new Horse(7, "Sortudo"),
            };*/

            Thread[] threads = new Thread[horses.size()];

            Horse.gate = new CyclicBarrier(horses.size() + 1);

            for (int i = 0; i < horses.size(); i++) {
              threads[i] = new Thread(horses.get(i));
              threads[i].start();
            }

            Horse.gate.await(); // The count on the gate is now met, open the gate and start the race!
            Horse.threadMessage("Waiting for Horse threads to finish");

            for (int i = 0; i < horses.size(); i++) {
              while (threads[i].isAlive()) {
                threads[i].join(1000); // Wait a maximum of 1 second for the thread to finish.
                if (((System.currentTimeMillis() - startTime) > patience) && threads[i].isAlive()) {
                  Horse.threadMessage("Tired of waiting!");
                  threads[i].interrupt();
                  threads[i].join(); // The join method allows one thread to wait for the completion of another.
                }
              }
            }
            Horse.threadMessage("Fim da corrida!");

          break;
          case 2:
            System.out.println("Cadastrar aposta.");
            //cadastrarAposta();
            contagem_jogadores += 1; // incremento
            break;
          case 3:
            System.out.println("Visualizar apostas");

            // valida se há apostas, se não houver, exibe apenas o valor do prêmio
            // Exibe: Id, nome, cavalo apostado e valor apostado
            // Exibe a soma das apostas
            // Exibe o prêmio
            break;
          default:
            System.out.println("Opção inválida! Tente novamente.");
        }
      }
    }
  }
}
