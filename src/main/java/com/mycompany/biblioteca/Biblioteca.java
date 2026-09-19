/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal do sistema.
 * Guarda os dadosve inicia a interface grafica (GUI).
 *
 * @author Usuario
 */

/**
 * Abre uma nova conexão com o banco de dados PostgreSQL da biblioteca.
 * Usado por todas as telas (Livros, Usuários, Empréstimos) antes de
 * qualquer operação SQL.
 *
 * @return a conexão aberta, ou {@code null} se não foi possível conectar
 *         (nesse caso, quem chamou deve verificar o retorno antes de usar)
 */
public class Biblioteca {

    // =============================================
    // DADOS DE CONEXAO COM O BANCO
    // =============================================
    private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    // =============================================
    // CONEXAO COM O BANCO
    // =============================================
    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conectado ao banco de dados com sucesso!");
            return conn;

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco!");
            System.out.println("Mensagem: " + e.getMessage());
            return null;
        }
    }

    // =============================================
    // MAIN - INICIA A INTERFACE GRAFICA
    // =============================================
    public static void main(String[] args) {

        // Inicializa a interface gráfica na Thread do Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaBiblioteca();
            }
        });
    }
}