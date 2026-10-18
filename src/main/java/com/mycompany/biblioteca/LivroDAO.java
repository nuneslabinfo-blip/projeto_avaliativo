package com.mycompany.biblioteca;

import java.sql.SQLException;
import java.util.List;

/** Operações de acesso ao banco para a entidade {@link Livro}. */
public interface LivroDAO {
    void inserir(Livro livro) throws SQLException;
    Livro buscarPorId(int id) throws SQLException;
    List<Livro> listarTodos() throws SQLException;
    void atualizar(Livro livro) throws SQLException;
    void excluir(int id) throws SQLException;

    /** Busca por trecho do título ou do autor. */
    List<Livro> buscar(String termo) throws SQLException;

    /** Retorna o primeiro livro com esse título, ou {@code null}. */
    Livro buscarPorTitulo(String titulo) throws SQLException;

    /** Títulos que contêm o termo (máx. 8), usado no autocompletar. */
    List<String> sugerirTitulos(String termo) throws SQLException;
}