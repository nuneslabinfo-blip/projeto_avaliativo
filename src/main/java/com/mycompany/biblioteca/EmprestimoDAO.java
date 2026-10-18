package com.mycompany.biblioteca;

import java.sql.SQLException;
import java.util.List;

/** Operações de acesso ao banco para a entidade {@link Emprestimo}. */
public interface EmprestimoDAO {
    /** Registra o empréstimo com data de hoje e status EM ANDAMENTO. */
    void inserir(Emprestimo emprestimo) throws SQLException;
    Emprestimo buscarPorId(int id) throws SQLException;
    /** Lista com nome do usuário e título do livro (JOIN). */
    List<Emprestimo> listarTodos() throws SQLException;
    /** Atualiza data de devolução e status. */
    void atualizar(Emprestimo emprestimo) throws SQLException;
    void excluir(int id) throws SQLException;
}