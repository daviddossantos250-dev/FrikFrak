package frikfrak;

// classe mae dos jogadores
// tanto humano como pc herdam daqui
public abstract class Jogador {

    protected String nome;
    protected int numero;
    protected int pecasColocadas;

    public Jogador(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
        this.pecasColocadas = 0;
    }

    // cada tipo de jogador decide a jogada a sua maneira
    public abstract int fazerJogada(Tabuleiro tab);

    public String getNome() { return nome; }
    public int getNumero() { return numero; }
    public int getPecasColocadas() { return pecasColocadas; }
    public void incrementarPecas() { pecasColocadas++; }
    public abstract boolean isHumano();
}
