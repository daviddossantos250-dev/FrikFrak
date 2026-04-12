package frikfrak;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("gui")) {
            SwingUtilities.invokeLater(() -> new JanelaJogo());
            return;
        }

        System.out.println("==============================");
        System.out.println("  FRIK FRAK - Jogo de Cabo Verde");
        System.out.println("==============================");
        System.out.println("1. modo consola");
        System.out.println("2. modo grafico (GUI)");
        System.out.print("opcao: ");

        try {
            int op = Integer.parseInt(sc.nextLine().trim());
            if (op == 2) {
                SwingUtilities.invokeLater(() -> new JanelaJogo());
                return;
            }
        } catch (NumberFormatException e) {
            // se nao conseguir ler, vai para consola
        }

        menuConsola();
    }

    static void menuConsola() {
        System.out.println("\n  FRIK FRAK");
        System.out.println("  Jogo de Cabo Verde\n");

        while (true) {
            System.out.println("1. humano vs humano");
            System.out.println("2. humano vs computador");
            System.out.println("3. regras");
            System.out.println("0. sair");
            System.out.print("opcao: ");

            try {
                int op = Integer.parseInt(sc.nextLine().trim());
                if (op == 1) modoHvH();
                else if (op == 2) modoHvPC();
                else if (op == 3) verRegras();
                else if (op == 0) { System.out.println("ate logo!"); break; }
                else System.out.println("opcao invalida");
            } catch (NumberFormatException e) {
                System.out.println("escreve um numero");
            }
        }
    }

    static void modoHvH() {
        System.out.print("nome do jogador 1: ");
        String n1 = sc.nextLine().trim();
        if (n1.isEmpty()) n1 = "Jogador 1";

        System.out.print("nome do jogador 2: ");
        String n2 = sc.nextLine().trim();
        if (n2.isEmpty()) n2 = "Jogador 2";

        jogarLoop(
            new JogadorHumano(n1, 1, sc),
            new JogadorHumano(n2, 2, sc)
        );
    }

    static void modoHvPC() {
        System.out.print("o teu nome: ");
        String n = sc.nextLine().trim();
        if (n.isEmpty()) n = "Jogador";

        System.out.println("dificuldade: 1. facil  2. dificil");
        System.out.print("opcao: ");

        JogadorPC.Dificuldade dif = JogadorPC.Dificuldade.FACIL;
        try {
            if (Integer.parseInt(sc.nextLine().trim()) == 2)
                dif = JogadorPC.Dificuldade.DIFICIL;
        } catch (NumberFormatException e) {}

        System.out.println("quem comeca? 1. tu  2. pc");
        System.out.print("opcao: ");

        Jogador ja, jb;
        try {
            if (Integer.parseInt(sc.nextLine().trim()) == 2) {
                ja = new JogadorPC("Computador", 1, dif);
                jb = new JogadorHumano(n, 2, sc);
            } else {
                ja = new JogadorHumano(n, 1, sc);
                jb = new JogadorPC("Computador", 2, dif);
            }
        } catch (NumberFormatException e) {
            ja = new JogadorHumano(n, 1, sc);
            jb = new JogadorPC("Computador", 2, dif);
        }

        jogarLoop(ja, jb);
    }

    static void jogarLoop(Jogador ja, Jogador jb) {
        while (true) {
            // recria os jogadores para resetar pecas colocadas
            Jogador novoA = clonar(ja);
            Jogador novoB = clonar(jb);

            new Jogo(novoA, novoB).jogar();

            System.out.print("jogar de novo? (s/n): ");
            String r = sc.nextLine().trim().toLowerCase();
            if (!r.equals("s")) break;
        }
    }

    static Jogador clonar(Jogador j) {
        if (j instanceof JogadorHumano)
            return new JogadorHumano(j.getNome(), j.getNumero(), sc);
        JogadorPC pc = (JogadorPC) j;
        return new JogadorPC(j.getNome(), j.getNumero(), pc.getDificuldade());
    }

    static void verRegras() {
        System.out.println("\n--- REGRAS DO FRIK FRAK ---");
        System.out.println("origem: jogo tradicional de Cabo Verde");
        System.out.println();
        System.out.println("tabuleiro com 9 posicoes:");
        System.out.println("  0 | 1 | 2");
        System.out.println(" ---+---+---");
        System.out.println("  3 | 4 | 5");
        System.out.println(" ---+---+---");
        System.out.println("  6 | 7 | 8");
        System.out.println();
        System.out.println("cada jogador tem 3 pecas (X e O)");
        System.out.println("os jogadores alternam a colocar 1 peca por turno");
        System.out.println("qualquer posicao livre e valida para comecar");
        System.out.println("ganha quem alinhar as 3 pecas em linha, coluna ou diagonal");
        System.out.println("---------------------------\n");
        System.out.print("enter para continuar...");
        sc.nextLine();
    }
}
