package frikfrak;

// controla o fluxo de uma partida
public class Jogo {

    private Tabuleiro tab;
    private Jogador j1, j2;
    private Jogador atual;

    public Jogo(Jogador j1, Jogador j2) {
        this.tab = new Tabuleiro();
        this.j1 = j1;
        this.j2 = j2;
        this.atual = j1;
    }

    public Jogador jogar() {
        System.out.println("\n=== FrikFrak ===");
        System.out.println("cada jogador tem 3 pecas - quem alinhar 3 ganha!");
        System.out.println("================\n");

        tab.desenhar();

        while (!tab.jogoTerminou()) {
            System.out.println("vez de " + atual.getNome() + " (" + (atual.getNumero() == 1 ? "X" : "O") + ")");

            int pos = atual.fazerJogada(tab);
            tab.colocarPeca(pos, atual.getNumero());
            atual.incrementarPecas();

            tab.desenhar();

            int v = tab.verificarVencedor();
            if (v != 0) {
                Jogador vencedor = (v == 1) ? j1 : j2;
                System.out.println("*** " + vencedor.getNome() + " ganhou! ***\n");
                return vencedor;
            }

            // troca de jogador
            atual = (atual == j1) ? j2 : j1;
        }

        System.out.println("empate!\n");
        return null;
    }
}
