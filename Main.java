import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Sean Gerhardt on 5/26/2015.
 */
public class Main {

  static void cadastrarAposta() {
    // Jogador j1 = new Jogador(1, "João", 25.00f, 7, false);
    System.out.println("---- Cadastrar aposta ----");
    System.out.println("Nome do jogador: ");
    System.out.println("Valor da aposta: ");
    System.out.println("Número do cavalo apostado: ");

    // cria objeto do tipo jogador e faz a conexão?
  }

  public static void main(String args[]) throws InterruptedException, BrokenBarrierException {
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
            System.out.println("Iniciar corrida");

            // válida se há alguma aposta
            if (contagem_jogadores > 2) {
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

              Horse[] horses = new Horse[] {
                  new Horse(1, "Pé de Pano"),
                  new Horse(2, "Bala no Alvo"),
                  new Horse(3, "Ponyta"),
                  new Horse(4, "Ventania"),
                  new Horse(5, "Spirit"),
                  new Horse(6, "Epona"),
                  new Horse(7, "Sortudo"),
              };

              Thread[] threads = new Thread[horses.length];

              Horse.gate = new CyclicBarrier(horses.length + 1);

              for (int i = 0; i < horses.length; i++) {
                threads[i] = new Thread(horses[i]);
                threads[i].start();
              }

              Horse.gate.await(); // The count on the gate is now met, open the gate and start the race!
              Horse.threadMessage("Waiting for Horse threads to finish");

              for (int i = 0; i < horses.length; i++) {
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
            } else {
              System.out.println("A corrida iniciará quando houver mais de 2 apostas.");
              System.out.println("O número atual de apostas é: " + contagem_jogadores + ".");
            }

            break;
          case 2:
            System.out.println("Cadastrar aposta.");
            cadastrarAposta();
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