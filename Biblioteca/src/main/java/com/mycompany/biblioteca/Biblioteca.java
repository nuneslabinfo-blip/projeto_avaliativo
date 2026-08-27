/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Biblioteca {

    // =============================================
    // DADOS DE CONEXAO COM O BANCO
    // =============================================
    private static final String URL     = "jdbc:postgresql://localhost:5432/biblioteca";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "postgres";

    // =============================================
    // MAIN - MENU PRINCIPAL
    // =============================================
    public static void main(String[] args) {

        int opcao;

        do {
            opcao = Entrada.leiaInt(
                "===================================\n" +
                "       SISTEMA DE BIBLIOTECA       \n" +
                "===================================\n" +
                "1 - Cadastrar livro\n" +
                "2 - Listar todos os livros\n" +
                "3 - Buscar livro por titulo\n" +
                "4 - Atualizar autor do livro\n" +
                "5 - Remover livro\n" +
                "0 - Sair\n" +
                "===================================\n" +
                "Escolha uma opcao: "
            );

            if (opcao == 1) {
                cadastrar();
            } else if (opcao == 2) {
                listar();
            } else if (opcao == 3) {
                buscar();
            } else if (opcao == 4) {
                atualizar();
            } else if (opcao == 5) {
                remover();
            } else if (opcao == 0) {
                System.out.println("Encerrando o sistema...");
            } else {
                System.out.println("Opcao invalida! Tente novamente.");
            }

        } while (opcao != 0);

        System.exit(0);
    }

    // =============================================
    // CONEXAO COM O BANCO
    // =============================================
    private static Connection conectar() {
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
    // VALIDADOR DE TEXTO (reutilizado)
    // =============================================
    private static boolean validarTexto(String texto, String nomeCampo) {
        if (texto == null || texto.isBlank()) {
            System.out.println("O campo '" + nomeCampo + "' nao pode ser vazio!");
            return false;
        }
        return true;
    }

    // =============================================
    // 1 - CADASTRAR
    // =============================================
    private static void cadastrar() {
        String titulo = Entrada.leiaString("Titulo do livro: ");
        if (!validarTexto(titulo, "Titulo")) {
            return;
        }

        String autor = Entrada.leiaString("Autor do livro: ");
        if (!validarTexto(autor, "Autor")) {
            return;
        }

        Connection conn = conectar();
        if (conn == null) {
            return; // Sem conexao, volta ao menu
        }

        String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";

        try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.executeUpdate();
            System.out.println("Livro cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    // =============================================
    // 2 - LISTAR
    // =============================================
    private static boolean listar() {
        Connection conn = conectar();
        if (conn == null) {
            return false; // Sem conexao, volta ao menu
        }

        String sql = "SELECT * FROM livros ORDER BY id";

        try (conn; PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("===================================");
            System.out.println("        LIVROS CADASTRADOS         ");
            System.out.println("===================================");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                System.out.println("ID:     " + rs.getInt("id"));
                System.out.println("Titulo: " + rs.getString("titulo"));
                System.out.println("Autor:  " + rs.getString("autor"));
                System.out.println("-----------------------------------");
            }

            if (!encontrou) {
                System.out.println("Nenhum livro cadastrado ainda.");
            }

            return encontrou;

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return false;
        }
    }

    // =============================================
    // 3 - BUSCAR
    // =============================================
    private static void buscar() {
        String termo = Entrada.leiaString("Digite parte do titulo para buscar: ");
        if (!validarTexto(termo, "Termo de busca")) {
            return;
        }

        Connection conn = conectar();
        if (conn == null) {
            return; // Sem conexao, volta ao menu
        }

        String sql = "SELECT * FROM livros WHERE titulo ILIKE ?";

        try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + termo + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                System.out.println("===================================");
                System.out.println("       RESULTADO DA BUSCA          ");
                System.out.println("===================================");

                boolean encontrou = false;
                while (rs.next()) {
                    encontrou = true;
                    System.out.println("ID:     " + rs.getInt("id"));
                    System.out.println("Titulo: " + rs.getString("titulo"));
                    System.out.println("Autor:  " + rs.getString("autor"));
                    System.out.println("-----------------------------------");
                }

                if (!encontrou) {
                    System.out.println("Nenhum livro encontrado com esse termo.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
    }
    
    // =============================================
    // 4 - ATUALIZAR
    // =============================================
    private static void atualizar() {
        // Se nao houver livros (ou nao conectar), encerra a operacao aqui
        boolean existemLivros = listar();
        if (!existemLivros) {
            return;
        }

        int id = Entrada.leiaInt("Digite o ID do livro que deseja atualizar: ");

        String novoAutor = Entrada.leiaString("Novo autor: ");
        if (!validarTexto(novoAutor, "Autor")) {
            return;
        }

        Connection conn = conectar();
        if (conn == null) {
            return;
        }

        String sql = "UPDATE livros SET autor = ? WHERE id = ?";

        try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoAutor);
            stmt.setInt(2, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro atualizado com sucesso!");
            } else {
                System.out.println("ID nao encontrado no banco.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }
    
    // =============================================
    // 5 - REMOVER
    // =============================================
    private static void remover() {
        // Se nao houver livros (ou nao conectar), encerra a operacao aqui
        boolean existemLivros = listar();
        if (!existemLivros) {
            return;
        }

        int id = Entrada.leiaInt("Digite o ID do livro que deseja remover: ");
        boolean confirmar = Entrada.leiaBoolean("Confirma a remocao? (true/false): ");

        if (!confirmar) {
            System.out.println("Remocao cancelada.");
            return;
        }

        Connection conn = conectar();
        if (conn == null) {
            return;
        }

        String sql = "DELETE FROM livros WHERE id = ?";

        try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro removido com sucesso!");
            } else {
                System.out.println("ID nao encontrado no banco.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }
}