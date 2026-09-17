/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Usuario
 */
public class TelaBiblioteca extends JFrame {
    
    private static final Color AZUL_ESCURO = new Color(25, 118, 210);
    private static final Color AZUL_FUNDO  = new Color(240, 244, 248);
    private static final Color VERDE       = new Color(46, 125, 50);
    private static final Color LARANJA     = new Color(230, 81, 0);
    private static final Color VERMELHO    = new Color(198, 40, 40);

    public TelaBiblioteca() {
        configurarJanela();
        add(construirPainel());
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Sistema de Biblioteca - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(AZUL_FUNDO);
    }

    private JPanel construirPainel() {
        JPanel painel = new JPanel(new BorderLayout(0, 20));
        painel.setBackground(AZUL_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        painel.add(construirTitulo(), BorderLayout.NORTH);
        painel.add(construirBotoes(), BorderLayout.CENTER);
        painel.add(construirRodape(), BorderLayout.SOUTH);

        return painel;
    }

    private JPanel construirTitulo() {
        JPanel topo = new JPanel(new GridLayout(2, 1, 0, 5));
        topo.setOpaque(false);

        JLabel lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 33, 33));

        JLabel lblSub = new JLabel("Selecione um módulo para gerenciar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(117, 117, 117));

        topo.add(lblTitulo);
        topo.add(lblSub);
        return topo;
    }

    private JPanel construirBotoes() {
        JPanel centro = new JPanel(new GridLayout(4, 1, 0, 12));
        centro.setOpaque(false);

        JButton btnLivros = criarBotao("Gerenciar Livros", AZUL_ESCURO);
        JButton btnUsuarios = criarBotao("Gerenciar Usuários", VERDE);
        JButton btnEmprestimos = criarBotao("Gerenciar Empréstimos", LARANJA);
        JButton btnSair = criarBotao("Sair do Sistema", VERMELHO);

        btnLivros.addActionListener(e -> new TelaLivros().setVisible(true));
        btnUsuarios.addActionListener(e -> new TelaUsuarios().setVisible(true));
        btnEmprestimos.addActionListener(e -> new TelaEmprestimos().setVisible(true));
        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair do sistema?", "Sair",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        centro.add(btnLivros);
        centro.add(btnUsuarios);
        centro.add(btnEmprestimos);
        centro.add(btnSair);

        return centro;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return btn;
    }

    private JLabel construirRodape() {
        JLabel rodape = new JLabel("Desenvolvido em Java Swing + PostgreSQL", SwingConstants.CENTER);
        rodape.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        rodape.setForeground(new Color(158, 158, 158));
        return rodape;
    }    
}
