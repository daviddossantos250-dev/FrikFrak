package frikfrak;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

// janela grafica do jogo feita com Swing
public class JanelaJogo extends JFrame {

    // cores do jogo - da para mudar aqui
    private static final Color FUNDO       = new Color(245, 230, 200);
    private static final Color COR_TAB     = new Color(200, 170, 120);
    private static final Color CASA_VAZIA  = new Color(255, 248, 235);
    private static final Color CASA_HOVER  = new Color(255, 235, 180);
    private static final Color COR_X       = new Color(180, 40, 40);
    private static final Color COR_O       = new Color(40, 80, 160);
    private static final Color COR_WIN     = new Color(80, 180, 80);

    private JButton[] casas;
    private JLabel lblStatus;
    private JLabel lblPlacar;

    private Tabuleiro tab;
    private int jogAtual;
    private boolean ativo;
    private int pecas1, pecas2;

    private String nomeJ1, nomeJ2;
    private boolean temPC;
    private JogadorPC.Dificuldade dif;
    private int pontos1, pontos2;

    public JanelaJogo() {
        super("FrikFrak");
        pontos1 = 0;
        pontos2 = 0;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(FUNDO);
        setLayout(new BorderLayout(10, 10));
        configurar();
    }

    private void configurar() {
        // dialogo de configuracao antes de abrir o jogo
        JDialog d = new JDialog(this, "FrikFrak - Configurar", true);
        d.setLayout(new BorderLayout(10, 10));
        d.getContentPane().setBackground(FUNDO);

        JLabel titulo = new JLabel("FRIK FRAK", SwingConstants.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD, 26));
        titulo.setForeground(new Color(100, 50, 10));
        titulo.setBorder(new EmptyBorder(15, 0, 5, 0));
        d.add(titulo, BorderLayout.NORTH);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(FUNDO);
        p.setBorder(new EmptyBorder(10, 30, 10, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 5, 6, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0;
        p.add(label("Nome Jogador 1 (X):"), g);
        JTextField f1 = new JTextField("Jogador 1", 15);
        g.gridx = 1; p.add(f1, g);

        g.gridx = 0; g.gridy = 1;
        p.add(label("Modo:"), g);
        JComboBox<String> modo = new JComboBox<>(new String[]{"Humano vs Humano", "Humano vs Computador"});
        modo.setBackground(Color.WHITE);
        g.gridx = 1; p.add(modo, g);

        g.gridx = 0; g.gridy = 2;
        JLabel lbN2 = label("Nome Jogador 2 (O):");
        p.add(lbN2, g);
        JTextField f2 = new JTextField("Jogador 2", 15);
        g.gridx = 1; p.add(f2, g);

        g.gridx = 0; g.gridy = 3;
        p.add(label("Dificuldade:"), g);
        JComboBox<String> dificuldade = new JComboBox<>(new String[]{"Facil", "Dificil (Minimax)"});
        dificuldade.setBackground(Color.WHITE);
        dificuldade.setEnabled(false);
        g.gridx = 1; p.add(dificuldade, g);

        modo.addActionListener(e -> {
            boolean pc = modo.getSelectedIndex() == 1;
            f2.setEnabled(!pc);
            dificuldade.setEnabled(pc);
            if (pc) { f2.setText("Computador"); lbN2.setText("Adversario:"); }
            else    { f2.setText("Jogador 2");  lbN2.setText("Nome Jogador 2 (O):"); }
        });

        d.add(p, BorderLayout.CENTER);

        JButton btn = new JButton("Jogar!");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(100, 160, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            nomeJ1 = f1.getText().trim().isEmpty() ? "Jogador 1" : f1.getText().trim();
            temPC  = modo.getSelectedIndex() == 1;
            nomeJ2 = temPC ? "Computador" : (f2.getText().trim().isEmpty() ? "Jogador 2" : f2.getText().trim());
            dif    = dificuldade.getSelectedIndex() == 1 ? JogadorPC.Dificuldade.DIFICIL : JogadorPC.Dificuldade.FACIL;
            d.dispose();
        });

        JPanel pb = new JPanel(); pb.setBackground(FUNDO);
        pb.setBorder(new EmptyBorder(0, 0, 15, 0));
        pb.add(btn);
        d.add(pb, BorderLayout.SOUTH);

        d.pack();
        d.setLocationRelativeTo(null);
        d.setVisible(true);

        construirJanela();
        novaPartida();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void construirJanela() {
        getContentPane().removeAll();

        // topo com titulo e placar
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(FUNDO);
        topo.setBorder(new EmptyBorder(12, 15, 5, 15));

        JLabel t = new JLabel("FRIK FRAK", SwingConstants.CENTER);
        t.setFont(new Font("Georgia", Font.BOLD, 20));
        t.setForeground(new Color(100, 50, 10));
        topo.add(t, BorderLayout.NORTH);

        lblPlacar = new JLabel("", SwingConstants.CENTER);
        lblPlacar.setFont(new Font("Arial", Font.PLAIN, 13));
        lblPlacar.setForeground(new Color(80, 60, 30));
        topo.add(lblPlacar, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

        // tabuleiro 3x3
        JPanel tabPanel = new JPanel(new GridLayout(3, 3, 6, 6));
        tabPanel.setBackground(COR_TAB);
        tabPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 100, 50), 3),
            new EmptyBorder(8, 8, 8, 8)
        ));

        casas = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int idx = i;
            JButton b = new JButton();
            b.setFont(new Font("Arial", Font.BOLD, 48));
            b.setBackground(CASA_VAZIA);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(new Color(180, 140, 100), 1));
            b.setPreferredSize(new Dimension(110, 110));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (ativo && tab.posicaoLivre(idx)) b.setBackground(CASA_HOVER);
                }
                public void mouseExited(MouseEvent e) {
                    if (b.getBackground() == CASA_HOVER) b.setBackground(CASA_VAZIA);
                }
            });
            b.addActionListener(e -> clicar(idx));
            casas[i] = b;
            tabPanel.add(b);
        }

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        centro.setBackground(FUNDO);
        centro.add(tabPanel);
        add(centro, BorderLayout.CENTER);

        // status e botao novo jogo
        JPanel inf = new JPanel(new BorderLayout(5, 5));
        inf.setBackground(FUNDO);
        inf.setBorder(new EmptyBorder(5, 15, 15, 15));

        lblStatus = new JLabel("", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        lblStatus.setOpaque(true);
        lblStatus.setBackground(new Color(240, 220, 180));
        lblStatus.setBorder(new EmptyBorder(8, 10, 8, 10));
        inf.add(lblStatus, BorderLayout.CENTER);

        JButton novo = new JButton("Nova Partida");
        novo.setFont(new Font("Arial", Font.BOLD, 12));
        novo.setBackground(new Color(80, 130, 200));
        novo.setForeground(Color.WHITE);
        novo.setFocusPainted(false);
        novo.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        novo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        novo.addActionListener(e -> novaPartida());
        inf.add(novo, BorderLayout.SOUTH);

        add(inf, BorderLayout.SOUTH);
        pack();
    }

    private void novaPartida() {
        tab     = new Tabuleiro();
        jogAtual = 1;
        ativo   = true;
        pecas1  = 0;
        pecas2  = 0;

        for (int i = 0; i < 9; i++) {
            casas[i].setText("");
            casas[i].setBackground(CASA_VAZIA);
            casas[i].setForeground(Color.BLACK);
            casas[i].setEnabled(true);
        }

        atualizarPlacar();
        lblStatus.setText("vez de " + nome(1) + " (X)");
        lblStatus.setForeground(new Color(50, 100, 50));

        // se o pc for jogador 1, joga logo
        if (ehPC(1)) {
            Timer t = new Timer(500, e -> jogadaPC());
            t.setRepeats(false);
            t.start();
        }
    }

    private void clicar(int pos) {
        if (!ativo || ehPC(jogAtual)) return;

        if (!tab.posicaoLivre(pos)) {
            lblStatus.setText("casa ocupada, escolhe outra");
            lblStatus.setForeground(new Color(160, 40, 40));
            return;
        }

        jogar(pos);
    }

    private void jogar(int pos) {
        tab.colocarPeca(pos, jogAtual);

        JButton b = casas[pos];
        if (jogAtual == 1) { b.setText("X"); b.setForeground(COR_X); }
        else               { b.setText("O"); b.setForeground(COR_O); }
        b.setBackground(new Color(235, 245, 235));
        b.setEnabled(false);

        if (jogAtual == 1) pecas1++; else pecas2++;

        int v = tab.verificarVencedor();
        if (v != 0) { ganhou(v); return; }
        if (tab.tabuleiroCheio()) { empate(); return; }

        // troca jogador
        jogAtual = (jogAtual == 1) ? 2 : 1;
        lblStatus.setText("vez de " + nome(jogAtual) + " (" + (jogAtual == 1 ? "X" : "O") + ")");
        lblStatus.setForeground(new Color(50, 100, 50));

        if (ehPC(jogAtual)) {
            bloquearCasas();
            Timer t = new Timer(600, e -> jogadaPC());
            t.setRepeats(false);
            t.start();
        }
    }

    private void jogadaPC() {
        JogadorPC pc = new JogadorPC(nomeJ2, jogAtual, dif);
        // passa o numero de pecas ja colocadas
        int ja = (jogAtual == 1) ? pecas1 : pecas2;
        for (int i = 0; i < ja; i++) pc.incrementarPecas();

        lblStatus.setText(nomeJ2 + " esta a pensar...");
        int pos = pc.fazerJogada(tab);
        libertarCasas();
        jogar(pos);
    }

    private void ganhou(int v) {
        ativo = false;
        if (v == 1) pontos1++; else pontos2++;
        atualizarPlacar();
        marcarVencedor(v);

        String msg = nome(v) + " ganhou!";
        lblStatus.setText(msg);
        lblStatus.setForeground(new Color(20, 100, 20));

        Timer t = new Timer(400, e ->
            JOptionPane.showMessageDialog(this, msg, "Fim do jogo", JOptionPane.INFORMATION_MESSAGE)
        );
        t.setRepeats(false);
        t.start();
    }

    private void empate() {
        ativo = false;
        lblStatus.setText("empate!");
        lblStatus.setForeground(new Color(100, 80, 20));
        Timer t = new Timer(400, e ->
            JOptionPane.showMessageDialog(this, "Empate!", "Fim do jogo", JOptionPane.INFORMATION_MESSAGE)
        );
        t.setRepeats(false);
        t.start();
    }

    private void marcarVencedor(int v) {
        for (int[] l : Tabuleiro.getLinhas()) {
            if (tab.getCasa(l[0]) == v && tab.getCasa(l[1]) == v && tab.getCasa(l[2]) == v) {
                for (int p : l) {
                    casas[p].setBackground(COR_WIN);
                    casas[p].setForeground(Color.WHITE);
                }
                return;
            }
        }
    }

    private void bloquearCasas() {
        for (JButton b : casas) b.setEnabled(false);
    }

    private void libertarCasas() {
        for (int i = 0; i < 9; i++)
            if (tab.posicaoLivre(i)) casas[i].setEnabled(true);
    }

    private void atualizarPlacar() {
        lblPlacar.setText(nomeJ1 + ": " + pontos1 + "  |  " + nomeJ2 + ": " + pontos2);
    }

    private String nome(int n) { return n == 1 ? nomeJ1 : nomeJ2; }
    private boolean ehPC(int n) { return temPC && n == 2; }

    private JLabel label(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        l.setForeground(new Color(60, 40, 10));
        return l;
    }
}
