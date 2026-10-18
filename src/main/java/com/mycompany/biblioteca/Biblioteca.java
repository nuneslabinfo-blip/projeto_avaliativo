/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe principal do sistema.
 * Guarda os dados de conexão com o banco e inicia a interface gráfica (GUI).
 *
 * @author Usuario
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

    /**
     * Abre uma nova conexão com o banco de dados PostgreSQL da biblioteca.
     *
     * @return a conexão aberta, ou {@code null} se não foi possível conectar
     */
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

    /**
     * Abre uma conexão ou lança exceção; usado pelos DAOs.
     *
     * @return conexão aberta (nunca {@code null})
     * @throws SQLException se não for possível conectar
     */
    public static Connection abrirConexao() throws SQLException {
        Connection conn = conectar();
        if (conn == null) throw new SQLException("Não foi possível conectar ao banco.");
        return conn;
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