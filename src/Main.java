import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    static void cadastrarAposta() {
        // Jogador j1 = new Jogador(1, "João", 25.00f, 7, false);
        System.out.println("---- Cadastrar aposta ----");
        System.out.println("Nome do jogador: ");
        System.out.println("Valor da aposta: ");
        System.out.println("Número do cavalo apostado: ");

        // cria objeto do tipo jogador e faz a conexão?

    }

    public static void main(String[] args) {

        int contagem_jogadores = 0;

        // Menu do jogo
        while(true) {
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
                        } else {
                            System.out.println("A corrida iniciará quando houver mais de 2 apostas.");
                            System.out.println("O número atual de apostas é: " + contagem_jogadores + ".");
                        }

                        // inicia a corrida
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
                    default: System.out.println("Opção inválida! Tente novamente.");
                }
            }
        }
    }
}