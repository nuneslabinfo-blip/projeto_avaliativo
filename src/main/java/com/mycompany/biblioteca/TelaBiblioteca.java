/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca;

import java.awt.BorderLayout;
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
 * Tela de menu principal do sistema.
 * Exibe os botões de acesso aos módulos (Livros, Usuários, Empréstimos)
 * e a opção de sair da aplicação.
 * 
 * @author Usuario
 */
public class TelaBiblioteca extends JFrame {

    public TelaBiblioteca() {
        configurarJanela();
        add(construirPainel());
        setVisible(true);
    }
    
    /**
     * Define as configurações básicas da janela principal
     * (título, tamanho, posição e comportamento ao fechar).
     */
    private void configurarJanela() {
        setTitle("Sistema de Biblioteca - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 480);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Monta o painel principal da tela, organizando o título no topo
     * e os botões de navegação no centro.
     */
    private JPanel construirPainel() {
        JPanel painel = new JPanel(new BorderLayout(0, 20));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        painel.add(construirTitulo(), BorderLayout.NORTH);
        painel.add(construirBotoes(), BorderLayout.CENTER);

        return painel;
    }
    
    /**
     * Cria o painel de título, com o nome do sistema e um subtítulo
     * explicando a ação esperada do usuário.
     */
    private JPanel construirTitulo() {
        JPanel topo = new JPanel(new GridLayout(2, 1, 0, 5));

        JLabel lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel lblSub = new JLabel("Selecione um módulo para gerenciar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        topo.add(lblTitulo);
        topo.add(lblSub);
        return topo;
    }
    
    /**
     * Cria o painel com os botões de navegação para cada módulo do
     * sistema (Livros, Usuários, Empréstimos) e o botão de saída,
     * já associando as respectivas ações de clique.
     */
    private JPanel construirBotoes() {
        JPanel centro = new JPanel(new GridLayout(4, 1, 0, 12));

        JButton btnLivros = criarBotao("Gerenciar Livros");
        JButton btnUsuarios = criarBotao("Gerenciar Usuários");
        JButton btnEmprestimos = criarBotao("Gerenciar Empréstimos");
        JButton btnSair = criarBotao("Sair do Sistema");

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
    
    /**
     * Cria e estiliza um botão padrão do menu (fonte, cursor de mão
     * ao passar por cima, e espaçamento interno), evitando repetir
     * essa configuração em cada botão individualmente.
     */
    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return btn;
    }
}
