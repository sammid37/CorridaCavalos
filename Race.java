import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Race {
  Server server;
  PrintWriter writer;
  BufferedReader reader;
  
  Boolean race_finished = false; // verdadeira quando a corrida é finalizada
  
  BookMaker bookMaker = new BookMaker(server); // Casa de apostas
  List<Horse> horses = new ArrayList<Horse>(); // Lista de cavalos da corrida
  List<Gambler> gamblers = new ArrayList<Gambler>(); // Lista de apostadores

  // Construtor da corrida
  public Race(Server server) {
    try {
      this.server = server;
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Criando os cavalos da corrida
    horses.add(new Horse(1, "Pé de Pano", server));
    horses.add(new Horse(2, "Bala no Alvo", server));
    horses.add(new Horse(3, "Ponyta", server));
    horses.add(new Horse(4, "Ventania", server));
    horses.add(new Horse(5, "Spirit", server));
    horses.add(new Horse(6, "Epona", server));
    horses.add(new Horse(7, "Sortudo", server));
  }

  public void menu() throws InterruptedException {
    // MENU
    int contagem_jogadores = 0;

    while (true) {
      server.writer.println("---------------------------------");
      server.writer.println("1) Iniciar corrida."); // inicia e exibe resultado ao final
      server.writer.println("2) Realizar aposta."); // cadastra novo jogador
      server.writer.println("3) Visualizar apostas."); // cadastra novo jogador
      server.writer.println("4) Sair do programa!");
      server.writer.println("---------------------------------");

      server.writer.println("Digite a opção: ");
      server.writer.println("WAITFORANSWER");

      int opcao;

      try {
        opcao = Integer.parseInt(server.reader.readLine());
      } catch (Exception exception) {
        server.writer.println("Esperava um caracter inteiro.");
        break;
      }

      // Validando opção e chamando métodos
      if (opcao == 4) {
        if(!race_finished){
          // Caso a corrida não tenha finalizado, devolve o dinheiro de todos
          for (int i = 0; i < gamblers.size(); i ++) {
            gamblers.get(i).setWallet(gamblers.get(i).getWallet() + gamblers.get(i).getBet());
            server.writer.println("Número de apostadores insuficiente, o valor apostado será estornado!");
          }
        } 
        server.writer.println("\n\n< OBRIGADO POR JOGAR >");
        server.writer.println(
            "Desenvolvedores: \n\tChristopher Tavares, \n\tMayra Daher, \n\tVictoria Grisi, \n\tSamantha Medeiros \n\te Yan Feitosa.");
        server.close();
        break;
      } else {
        switch (opcao) {
          case 1:
            // O jogo só iniciará se houver ao menos 2 apostadores
            if (contagem_jogadores >= 2) {
              server.writer.println("\n\n< A CORRIDA IRÁ INICIAR >");
              // Delay antes de interromper a Thread MessageLoop (por padrao é 1h)
              long patience = 1000 * 60 * 60;

              Horse.threadMessage("Iniciando MessageLoop thread", server);
              long startTime = System.currentTimeMillis();

              Thread[] threads = new Thread[horses.size()]; // Cria as threads de acordo com a qtd de cavalos

              for (int i = 0; i < horses.size(); i++) {
                threads[i] = new Thread(horses.get(i));
                threads[i].start();
              }

              Horse.threadMessage("Cavalos em posições...", server);

              for (int i = 0; i < horses.size(); i++) {
                while (threads[i].isAlive()) {
                  threads[i].join(1000); // Aguarde no máximo 1 segundo para a thread terminar.
                  if (((System.currentTimeMillis() - startTime) > patience) && threads[i].isAlive()) {
                    Horse.threadMessage("Chega de esperar!", server);
                    threads[i].interrupt();
                    threads[i].join(); // O método join permite que uma thread aguarde a conclusão de outra.
                  }
                }
              }
              race_finished = true;
              Horse.threadMessage("\n\nFim da corrida!", server);

              // Verifica quais os jogadores venceram com base no cavalo que ganhou a corrida
              for (int i = 0; i < horses.size(); i ++) {
                if (horses.get(i).isWinner() == true) {
                  for (int f = 0; f < gamblers.size(); f ++) {
                    if (gamblers.get(f).getHorse() == horses.get(i)){
                      gamblers.get(f).setWinner(true);
                      bookMaker.result(gamblers.get(f));
                    }
                  }
                }
              }
            } else {
              server.writer.println("\n\n< A CORRIDA NÃO PODE SER INICIADA >\n");
              server.writer.println("É necessário ter ao menos 2 apostadores.");
              server.writer.println("O número atual de apostas é: " + contagem_jogadores + ".\n\n"); // ou  gamblers.size()
            }
            break;
          case 2:
            server.writer.println("\n\n< CADASTRAR APOSTAS >");
            boolean validchoice = false;

            // Verifica se o número informado é inteiro positivo e <= qtd de cavlos
            // Enquanto não digitar um valor válido, continua a informar um número
            while (!validchoice) {
              server.writer.println("< LISTA DE CAVALOS >");
              for (int i = 0; i < horses.size(); i++) {
                server.writer.println(horses.get(i).getHorseId() + " - " + horses.get(i).getHorseName());
              }
              server.writer.println("---------------------------------");
              server.writer.println("\n>>> Informe o número do cavalo que deseja apostar: ");
              server.writer.println("WAITFORANSWER");

              try {
                int horse_id = Integer.parseInt(server.reader.readLine());
                int indice = horse_id - 1;
                if (horse_id <= horses.size() && horse_id != 0) {
                  server.writer.write("[SELECIONADO] " 
                    + horses.get(indice).getHorseId() + " "
                    + horses.get(indice).getHorseName() + ".\n"
                  );
                  validchoice = true;

                  // Cria um novo apostador e atribui um cavalo a ele
                  gamblers.add(new Gambler(horses.get(indice), server));
                  contagem_jogadores += 1;
                  bookMaker.bet(gamblers.get(contagem_jogadores-1), gamblers.get(contagem_jogadores-1).getHorse());
                  break;
                } else {
                  server.writer.println("Este cavalo não existe. Tente novamente.");
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            break;
          case 3:
            server.writer.println("\n\n< VISUALIZAR APOSTAS >");

            // Exibe o resultado quando a corrida é finalizada!
            if (race_finished) {
              for (int i = 0; i < gamblers.size(); i++) {
                if (gamblers.get(i).isWinner()){
                  server.writer.println("\n[GANHADOR] " 
                  + gamblers.get(i).getName() + " apostou R$"
                  + gamblers.get(i).getBet() + " no cavalo "
                  + gamblers.get(i).getHorse().getHorseName() 
                  + ".\n>> Lucro: " + gamblers.get(i).getReward()
                  + "\n>> Saldo atual: " + gamblers.get(i).getWallet() + ".");
                }
                else {
                  server.writer.println("\n[PERDEU] " 
                  + gamblers.get(i).getName() + " apostou R$"
                  + gamblers.get(i).getBet() + " no cavalo "
                  + gamblers.get(i).getHorse().getHorseName()
                  + ".\n>> Saldo atual: " + gamblers.get(i).getWallet()+ ".");
                }
              }
              server.writer.println("---------------------------------");
              server.writer.println("\n\n~~~Lucro da casa de apostas: R$" + bookMaker.getBookmaker_profit() + ".~~~\n");
            } 
            else {
              for (int i = 0; i < gamblers.size(); i++) {
                server.writer.println(i + " "
                  + gamblers.get(i).getName() + " apostou R$"
                  + gamblers.get(i).getBet() + " no cavalo "
                  + gamblers.get(i).getHorse().getHorseName() + ".");
              }
            }
            server.writer.println("Número total de apostas: " + bookMaker.getBets_num() + ".");
            server.writer.println("Valor total das apostas: R$" + bookMaker.getTotal_value() + ".\n");
            server.writer.println("Valor do prêmio: R$" + bookMaker.getPrize() + ".");

            break;
          default:
            server.writer.println("Opção inválida! Tente novamente.");
        }
      }
    }
  }
}
