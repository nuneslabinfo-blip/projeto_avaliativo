package com.mycompany.biblioteca;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implementação JDBC (PostgreSQL) de {@link EmprestimoDAO}. */
public class EmprestimoDAOJdbc implements EmprestimoDAO {

    private static final String SELECT_BASE =
            "SELECT e.id, e.usuario_id, e.livro_id, u.nome AS usuario, l.titulo AS livro, "
            + "e.data_emprestimo, e.data_devolucao, e.status "
            + "FROM emprestimos e "
            + "JOIN usuarios u ON u.id = e.usuario_id "
            + "JOIN livros l ON l.id = e.livro_id ";

    private Emprestimo mapear(ResultSet rs) throws SQLException {
        Emprestimo e = new Emprestimo(rs.getInt("usuario_id"), rs.getInt("livro_id"));
        e.setId(rs.getInt("id"));
        e.setUsuarioNome(rs.getString("usuario"));
        e.setLivroTitulo(rs.getString("livro"));
        Date emp = rs.getDate("data_emprestimo");
        Date dev = rs.getDate("data_devolucao");
        e.setDataEmprestimo(emp == null ? null : emp.toLocalDate());
        e.setDataDevolucao(dev == null ? null : dev.toLocalDate());
        e.setStatus(rs.getString("status"));
        return e;
    }

    @Override
    public void inserir(Emprestimo e) throws SQLException {
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, status) "
                + "VALUES (?, ?, CURRENT_DATE, 'EM ANDAMENTO')";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getUsuarioId());
            stmt.setInt(2, e.getLivroId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Emprestimo buscarPorId(int id) throws SQLException {
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BASE + "WHERE e.id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Emprestimo> listarTodos() throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BASE + "ORDER BY e.id");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    @Override
    public void atualizar(Emprestimo e) throws SQLException {
        String sql = "UPDATE emprestimos SET data_devolucao = ?, status = ? WHERE id = ?";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, e.getDataDevolucao());  // LocalDate (ou null)
            stmt.setString(2, e.getStatus());
            stmt.setInt(3, e.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        try (Connection conn = Biblioteca.abrirConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}