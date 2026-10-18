package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implementação JDBC (PostgreSQL) de {@link LivroDAO}. */
public class LivroDAOJdbc implements LivroDAO {

    private Livro mapear(ResultSet rs) throws SQLException {
        return new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"));
    }

    @Override
    public void inserir(Livro l) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getAutor());
            stmt.executeUpdate();
        }
    }

    @Override
    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livros WHERE id = ?";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Livro> listarTodos() throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros ORDER BY id";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public void atualizar(Livro l) throws SQLException {
        String sql = "UPDATE livros SET titulo = ?, autor = ? WHERE id = ?";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getTitulo());
            stmt.setString(2, l.getAutor());
            stmt.setInt(3, l.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Livro> buscar(String termo) throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo ILIKE ? OR autor ILIKE ? ORDER BY id";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    @Override
    public Livro buscarPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT * FROM livros WHERE titulo ILIKE ? ORDER BY id LIMIT 1";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<String> sugerirTitulos(String termo) throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT titulo FROM livros WHERE titulo ILIKE ? ORDER BY titulo LIMIT 8";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + termo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(rs.getString("titulo"));
            }
        }
        return lista;
    }
}