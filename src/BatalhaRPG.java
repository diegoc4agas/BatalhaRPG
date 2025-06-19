import java.util.Random;
import java.util.Scanner;

public class BatalhaRPG {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        boolean jogarNovamente = true;

        while (jogarNovamente) {
            System.out.println("\n===== JOGO DE RPG: 4 HERÓIS VS 1 BOSS =====");

            // Criação dos jogadores
            Personagem[] jogadores = new Personagem[4];
            int guerreiroCount, magoCount, arqueiroCount;

            for (int i = 0; i < jogadores.length; i++) {
                System.out.println("\nJogador " + (i + 1) + ":");

                int escolha;
                do {
                    System.out.println("Escolha o seu herói:");
                    System.out.println("1 - Guerreiro 🛡️");
                    System.out.println("2 - Mago 🔥");
                    System.out.println("3 - Arqueiro 🏹");
                    System.out.print("Digite o número da sua escolha: ");
                    escolha = validarEntrada(1, 3);
                } while (escolha == -1);

                String nome;
                if (i == 3) { // O quarto jogador escolhe o nome
                    System.out.print("Digite o nome do seu herói: ");
                    nome = scanner.nextLine();
                } else {
                    // Nome automático baseado na classe
                    nome = switch (escolha) {
                        case 1 -> "Guerreiro ";
                        case 2 -> "Mago ";
                        case 3 -> "Arqueiro ";
                        default -> "Herói";
                    };
                }

                jogadores[i] = switch (escolha) {
                    case 1 -> new Guerreiro(nome);
                    case 2 -> new Mago(nome);
                    case 3 -> new Arqueiro(nome);
                    default -> throw new IllegalStateException("Escolha inválida.");
                };

                System.out.println("🧝 Herói escolhido: " + jogadores[i].getClass().getSimpleName());
            }

            // Escolha do inimigo
            int escolhaInimigo;
            do {
                System.out.println("\nEscolha o Boss:");
                System.out.println("1 - Hydra");
                System.out.println("2 - Medusa");
                System.out.println("3 - Orc Selvagem");
                System.out.print("Digite o número da sua escolha: ");
                escolhaInimigo = validarEntrada(1, 3);
            } while (escolhaInimigo == -1);

            Inimigo inimigo = switch (escolhaInimigo) {
                case 1 -> new Inimigo("Hydra", 250, 32);
                case 2 -> new Inimigo("Medusa", 600, 13);
                case 3 -> new Inimigo("Orc Selvagem", 1200, 17);
                default -> throw new IllegalStateException("Escolha inválida.");
            };

            System.out.println("\n⚔️ Os Heróis vão enfrentar o Boss " + inimigo.nome + "! ⚔️");

            // Loop da batalha
            while (inimigo.estaVivo() && peloMenosUmVivo(jogadores)) {
                for (Personagem jogador : jogadores) {
                    if (!jogador.estaVivo()) continue;

                    System.out.println("\n🎯 Turno de " + jogador.nome);
                    int escolhaDado;
                    do {
                        System.out.println("Escolha um dado para atacar:");
                        System.out.println("1 - D6 🎲 (Menor risco, dano menor)");
                        System.out.println("2 - D10 🎲 (Intermediário, pode errar 5%)");
                        System.out.println("3 - D20 🎲 (Maior risco, pode errar 20%)");
                        System.out.print("Digite o número da sua escolha: ");
                        escolhaDado = validarEntrada(1, 3);
                    } while (escolhaDado == -1);

                    int tipoDado = switch (escolhaDado) {
                        case 1 -> 6;
                        case 2 -> 10;
                        case 3 -> 20;
                        default -> 6;
                    };

                    jogador.atacar(inimigo, tipoDado);
                    if (!inimigo.estaVivo()) {
                        System.out.println("\n🏆 " + inimigo.nome + " foi derrotado! VITÓRIA DOS HERÓIS! 🏆");
                        break;
                    }
                }

                if (!inimigo.estaVivo()) break;

                // Inimigo ataca jogador aleatório vivo
                Personagem alvo;
                do {
                    alvo = jogadores[random.nextInt(jogadores.length)];
                } while (!alvo.estaVivo());

                //System.out.println("\n💥" + inimigo.nome + " ataca " + alvo.nome+ "!");
                inimigo.atacar(alvo, 6);
            }

            // Resultado final
            if (!peloMenosUmVivo(jogadores)) {
                System.out.println("\n💀 Todos os jogadores foram derrotados. " + inimigo.nome + " venceu! 💀");
            }

            System.out.print("\nJogar novamente? (s/n): ");
            String resposta = scanner.next().toLowerCase();
            scanner.nextLine(); // Limpa buffer
            jogarNovamente = resposta.equals("s");
        }

        scanner.close();
        System.out.println("\nAté a próxima, heróis! 🎲🛡️🔥");
    }

    public static int rolarDado(int faces) {
        return random.nextInt(faces) + 1;
    }

    public static boolean chanceDeErro(int tipoDado) {
        if (tipoDado == 10) return random.nextInt(100) < 5;
        if (tipoDado == 20) return random.nextInt(100) < 20;
        return false;
    }

    public static int validarEntrada(int min, int max) {
        try {
            int entrada = Integer.parseInt(scanner.nextLine());
            if (entrada >= min && entrada <= max) return entrada;
            System.out.println("Opção inválida, tente novamente.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida, digite um número.");
        }
        return -1;
    }

    public static boolean peloMenosUmVivo(Personagem[] jogadores) {
        for (Personagem jogador : jogadores) {
            if (jogador.estaVivo()) return true;
        }
        return false;
    }
}