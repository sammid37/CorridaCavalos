
// Corrida de Cavalos - LP2 (dezembro de 2022)
// Christopher Alec, Mayra Daher, Victoria Grisi, Yan Feitosa e Samantha Medeiros
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class Main {
  public static void main(String args[]) throws InterruptedException, BrokenBarrierException {
    Scanner sc = new Scanner(System.in);

    List<Horse> horses = new ArrayList<Horse>(); // Lista de cavalos da corrida
    horses.add(new Horse(1, "Pé de Pano"));
    horses.add(new Horse(2, "Bala no Alvo"));
    horses.add(new Horse(3, "Ponyta"));
    horses.add(new Horse(4, "Ventania"));
    horses.add(new Horse(5, "Spirit"));
    horses.add(new Horse(6, "Epona"));
    horses.add(new Horse(7, "Sortudo"));

    List<Gambler> gamblers = new ArrayList<Gambler>(); // Lista de apostadores

    // MENU
    int contagem_jogadores = 0;
    while (true) {
      System.out.println("---------------------------------");
      System.out.println("<< 🐎 CORRIDA DE CAVALOS >>");
      System.out.println("1) Iniciar corrida."); // inicia e exibe resultado ao final
      System.out.println("2) Realizar aposta."); // cadastra novo jogador
      System.out.println("3) Visualizar apostas."); // cadastra novo jogador
      System.out.println("4) Sair do programa!");
      System.out.println("---------------------------------");

      System.out.print("🔘 Digite a opção: ");

      int opcao;

      try {
        opcao = sc.nextInt();
      } catch (InputMismatchException exception) {
        System.out.println("Esperava um caracter inteiro.");
        break;
      }

      // Validando opção e chamando métodos
      if (opcao == 4) {
        System.out.println("\n\n< 💖 OBRIGADO POR JOGAR >");
        System.out.println("🧑‍💻 Desenvolvedores: \n\tChristopher Tavares, \n\tMayra Daher, \n\tVictoria Grisi, \n\tSamantha Medeiros \n\te Yan Feitosa.");
        sc.close();
        break;
      } else {
        switch (opcao) {
          case 1:
            // O jogo só iniciará se houver ao menos 2 apostadores
            if (contagem_jogadores >= 2) {
              System.out.println("\n\n🏇 < A CORRIDA IRÁ INICIAR >");
              // Delay antes de interromper a Thread MessageLoop (por padrao é 1h)
              long patience = 1000 * 60 * 60; 

              // Se existir argumentos na linha de comando, a paciência é em segundos
              if (args.length > 0) {
                try {
                  patience = Long.parseLong(args[0]) * 1000;
                } catch (NumberFormatException e) {
                  System.err.println("Argumento deve ser inteiro.");
                  System.exit(1);
                }
              }

              Horse.threadMessage("Iniciando MessageLoop thread");
              long startTime = System.currentTimeMillis();

              Thread[] threads = new Thread[horses.size()]; // Cria as threads de acordo com a qtd de cavalos 

              Horse.gate = new CyclicBarrier(horses.size() + 1);

              for (int i = 0; i < horses.size(); i++) {
                threads[i] = new Thread(horses.get(i));
                threads[i].start();
              }

              Horse.gate.await(); // A contagem no portão foi atingida, abra o portão e comece a corrida!
              Horse.threadMessage("Cavalos em posições...");

              for (int i = 0; i < horses.size(); i++) {
                while (threads[i].isAlive()) {
                  threads[i].join(1000); // Aguarde no máximo 1 segundo para a thread terminar.
                  if (((System.currentTimeMillis() - startTime) > patience) && threads[i].isAlive()) {
                    Horse.threadMessage("Em suas marcas... *Pow* ... Já!");
                    threads[i].interrupt();
                    threads[i].join(); // O método join permite que uma thread aguarde a conclusão de outra.
                  }
                }
              }
              Horse.threadMessage("\n\nFim da corrida!");
            } else {
              System.out.println("\n\n< ⛔ A CORRIDA NÃO PODE SER INICIADA >\n");
              System.out.println("É necessário ter ao menos 2 apostadores.");
              System.out.println("O número atual de apostas é: " + contagem_jogadores + ".\n\n"); // ou gamblers.size()
            }

            break;
          case 2:
            System.out.println("\n\n< 💰 CADASTRAR APOSTAS >");
            boolean validchoice = false;

            // Verifica se o número informado é inteiro positivo e <= qtd de cavlos
            // Enquanto não digitar um valor válido, continua a informar um número
            while (!validchoice) {
              System.out.println("< 🐎 LISTA DE CAVALOS >");
              for (int i = 0; i < horses.size(); i++) {
                System.out.println(horses.get(i).getHorseId() + " - " + horses.get(i).getHorseName());
              }
              System.out.print("\nInforme o número do cavalo que deseja apostar: ");
              int horse_id = sc.nextInt();
              int indice = horse_id - 1;
              if (horse_id <= horses.size() && horse_id != 0) {
                System.out.println("Você selecionou o cavalo " + horses.get(indice).getHorseName());
                validchoice = true;

                // Cria um novo apostador e atribui um cavalo a ele
                gamblers.add(new Gambler(horses.get(indice)));
                contagem_jogadores += 1;
                break;
              } else {
                System.out.println("Este cavalo não existe. Tente novamente.");
              }
            }
            break;
          case 3:
            float total_bets_value = 0.00f;
            System.out.println("\n\n< 💰 VISUALIZAR APOSTAS >");

            for (int i = 0; i < gamblers.size(); i++) {
              System.out.println(i + " "
                + gamblers.get(i).getName() + " apostou R$" 
                + gamblers.get(i).getBet() + " no cavalo "
                + gamblers.get(i).getHorse().getHorseName() + "."
              );
              total_bets_value += gamblers.get(i).getBet();
            }
            
            System.out.println("\nNúmero total de apostas: " + gamblers.size() + ".");
            System.out.println("Valor total das apostas: " + total_bets_value + ".\n\n");
            
            break;
          default:
            System.out.println("Opção inválida! Tente novamente.");
        }
      }
    }
  }
}