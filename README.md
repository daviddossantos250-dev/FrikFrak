# FrikFrak

Jogo tradicional de Cabo Verde implementado em Java.

---

## Como funciona o jogo

O tabuleiro tem 9 posições dispostas numa grelha 3x3:

```
0 | 1 | 2
--+---+--
3 | 4 | 5
--+---+--
6 | 7 | 8
```

Cada jogador tem 3 peças. Os jogadores vão alternando a colocar uma peça por turno em qualquer posição livre. Ganha quem conseguir alinhar as 3 peças em linha, coluna ou diagonal. Se o tabuleiro encher sem ninguém ganhar é empate.

---

## Modos de jogo

- **Humano vs Humano** - dois jogadores na mesma máquina
- **Humano vs PC fácil** - o computador joga aleatoriamente
- **Humano vs PC difícil** - o computador usa o algoritmo minimax

Tem também modo gráfico com janela Swing como extra.

---

## Estrutura do projeto

```
src/frikfrak/
    Main.java          - menu e arranque do programa
    Jogo.java          - controla o fluxo de cada partida
    Tabuleiro.java     - guarda o estado do tabuleiro
    Jogador.java       - classe base para os jogadores
    JogadorHumano.java - lê o input do teclado
    JogadorPC.java     - inteligência artificial
    JanelaJogo.java    - interface gráfica (extra)
```

---

## Explicação das classes

### Tabuleiro

Usei um array de 9 inteiros onde 0 é vazio, 1 é o jogador 1 e 2 é o jogador 2. Para verificar se alguém ganhou percorro as 8 combinações possíveis (3 linhas, 3 colunas e 2 diagonais) e vejo se alguma tem os 3 valores iguais.

### Jogador

Classe abstrata com o método `fazerJogada` que cada subclasse implementa à sua maneira. O JogadorHumano lê do teclado e o JogadorPC calcula.

### JogadorPC e o Minimax

O minimax é um algoritmo para jogos de dois jogadores. A ideia é simular todas as jogadas possíveis até ao fim do jogo e escolher a que tem melhor resultado.

Funciona assim:
- quando é a vez do PC tenta maximizar a pontuação
- quando é a vez do adversário assume que ele vai minimizar
- se o PC ganhar retorna pontuação positiva, se perder negativa, empate é 0
- a profundidade entra no cálculo para preferir ganhar mais rápido

Como o tabuleiro só tem 9 casas e cada jogador só usa 3 peças, a árvore é pequena e consegue explorar tudo sem problemas.

```
minimax(tabuleiro, maximizar, profundidade):
    se alguem ganhou ou tabuleiro cheio:
        retorna avaliacao do estado
    
    se maximizar:
        experimenta cada posicao livre como jogada do PC
        retorna o maximo dos resultados
    senao:
        experimenta cada posicao livre como jogada do adversario
        retorna o minimo dos resultados
```

### JanelaJogo

A interface gráfica usa Java Swing. Tem um diálogo de configuração no inicio para escolher os nomes e o modo de jogo. O tabuleiro é uma grelha de 9 botões. Quando é a vez do PC a interface bloqueia os botões e usa um Timer para dar uma pausa antes de jogar, para parecer mais natural. No fim marca a linha vencedora a verde.

---

## Como correr

No IntelliJ basta correr a classe `Main`. Vai perguntar se queres modo consola ou gráfico.

Na linha de comandos:
```
javac -d out src/frikfrak/*.java
java -cp out frikfrak.Main
```

Ou com o jar:
```
java -jar FrikFrak.jar
java -jar FrikFrak.jar gui
```
