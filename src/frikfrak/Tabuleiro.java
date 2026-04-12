package frikfrak;

// classe que representa o estado do tabuleiro
// usei um array simples de 9 posicoes, cada uma pode ser 0, 1 ou 2
public class Tabuleiro {

    // 0 = vazio, 1 = jogador 1, 2 = jogador 2
    private int[] casas;

    // todas as formas de ganhar - linhas, colunas e diagonais
    private static final int[][] LINHAS = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    public Tabuleiro() {
        casas = new int[9];
    }

    public boolean colocarPeca(int pos, int jog) {
        if (pos < 0 || pos > 8) return false;
        if (casas[pos] != 0) return false;
        casas[pos] = jog;
        return true;
    }

    public boolean posicaoLivre(int pos) {
        if (pos < 0 || pos > 8) return false;
        return casas[pos] == 0;
    }

    // retorna 1 ou 2 se alguem ganhou, 0 se nao
    public int verificarVencedor() {
        for (int[] l : LINHAS) {
            int a = casas[l[0]], b = casas[l[1]], c = casas[l[2]];
            if (a != 0 && a == b && b == c) return a;
        }
        return 0;
    }

    public boolean tabuleiroCheio() {
        for (int c : casas) {
            if (c == 0) return false;
        }
        return true;
    }

    public boolean jogoTerminou() {
        return verificarVencedor() != 0 || tabuleiroCheio();
    }

    public int getCasa(int pos) {
        return casas[pos];
    }

    public int[] getCasas() {
        return casas.clone();
    }

    public void limparCasa(int pos) {
        if (pos >= 0 && pos < 9) casas[pos] = 0;
    }

    public static int[][] getLinhas() {
        return LINHAS;
    }

    // mostra o tabuleiro no terminal
    public void desenhar() {
        System.out.println();
        System.out.println("  Posicoes:        Tabuleiro:");
        System.out.println();
        System.out.println("  0 | 1 | 2        " + s(0) + " | " + s(1) + " | " + s(2));
        System.out.println(" ---+---+---      ---+---+---");
        System.out.println("  3 | 4 | 5        " + s(3) + " | " + s(4) + " | " + s(5));
        System.out.println(" ---+---+---      ---+---+---");
        System.out.println("  6 | 7 | 8        " + s(6) + " | " + s(7) + " | " + s(8));
        System.out.println();
    }

    private String s(int pos) {
        switch (casas[pos]) {
            case 1: return "X";
            case 2: return "O";
            default: return ".";
        }
    }
}
