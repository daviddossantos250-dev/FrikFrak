package frikfrak;

// jogador computador
// tem dois modos: facil (aleatorio) e dificil (minimax)
public class JogadorPC extends Jogador {

    public enum Dificuldade { FACIL, DIFICIL }

    private Dificuldade dificuldade;

    public JogadorPC(String nome, int numero, Dificuldade dif) {
        super(nome, numero);
        this.dificuldade = dif;
    }

    @Override
    public int fazerJogada(Tabuleiro tab) {
        System.out.println(nome + " esta a pensar...");

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int pos;
        if (dificuldade == Dificuldade.FACIL) {
            pos = jogadaAleatoria(tab);
        } else {
            pos = melhorJogada(tab);
        }

        System.out.println(nome + " jogou na posicao " + pos);
        return pos;
    }

    // modo facil - escolhe qualquer casa livre ao acaso
    private int jogadaAleatoria(Tabuleiro tab) {
        int pos;
        do {
            pos = (int)(Math.random() * 9);
        } while (!tab.posicaoLivre(pos));
        return pos;
    }

    // modo dificil - usa minimax para escolher a melhor jogada
    private int melhorJogada(Tabuleiro tab) {
        int melhorVal = Integer.MIN_VALUE;
        int melhorPos = -1;

        for (int i = 0; i < 9; i++) {
            if (tab.posicaoLivre(i)) {
                tab.colocarPeca(i, numero);
                int val = minimax(tab, false, 0);
                tab.limparCasa(i);

                if (val > melhorVal) {
                    melhorVal = val;
                    melhorPos = i;
                }
            }
        }
        return melhorPos;
    }

    // minimax recursivo
    // maximizar = true quando e a vez do pc, false quando e o adversario
    private int minimax(Tabuleiro tab, boolean maximizar, int prof) {
        int v = tab.verificarVencedor();

        if (v == numero) return 10 - prof;   // pc ganhou
        if (v != 0) return prof - 10;         // adversario ganhou
        if (tab.tabuleiroCheio()) return 0;   // empate

        int adv = (numero == 1) ? 2 : 1;

        if (maximizar) {
            int melhor = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (tab.posicaoLivre(i)) {
                    tab.colocarPeca(i, numero);
                    melhor = Math.max(melhor, minimax(tab, false, prof + 1));
                    tab.limparCasa(i);
                }
            }
            return melhor;
        } else {
            int melhor = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (tab.posicaoLivre(i)) {
                    tab.colocarPeca(i, adv);
                    melhor = Math.min(melhor, minimax(tab, true, prof + 1));
                    tab.limparCasa(i);
                }
            }
            return melhor;
        }
    }

    @Override
    public boolean isHumano() { return false; }

    public Dificuldade getDificuldade() { return dificuldade; }
}
