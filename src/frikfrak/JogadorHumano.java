package frikfrak;

import java.util.Scanner;

// jogador humano, le o input do teclado
public class JogadorHumano extends Jogador {

    private Scanner sc;

    public JogadorHumano(String nome, int numero, Scanner sc) {
        super(nome, numero);
        this.sc = sc;
    }

    @Override
    public int fazerJogada(Tabuleiro tab) {
        while (true) {
            System.out.print(nome + " [" + (numero == 1 ? "X" : "O") + "] - escolhe uma posicao (0-8): ");

            try {
                int pos = Integer.parseInt(sc.nextLine().trim());

                if (pos < 0 || pos > 8) {
                    System.out.println("tem de ser entre 0 e 8, tenta outra vez");
                    continue;
                }

                if (!tab.posicaoLivre(pos)) {
                    System.out.println("essa posicao ja esta ocupada");
                    continue;
                }

                return pos;

            } catch (NumberFormatException e) {
                System.out.println("escreve so um numero");
            }
        }
    }

    @Override
    public boolean isHumano() { return true; }
}
