package com.mycompany.biblioteca;

import java.sql.SQLException;
import java.util.List;

/** Operações de acesso ao banco para a entidade {@link Usuario}. */
public interface UsuarioDAO {
    void inserir(Usuario usuario) throws SQLException;
    Usuario buscarPorId(int id) throws SQLException;
    List<Usuario> listarTodos() throws SQLException;
    void atualizar(Usuario usuario) throws SQLException;
    void excluir(int id) throws SQLException;

    /** Busca por trecho do nome ou do e-mail. */
    List<Usuario> buscar(String termo) throws SQLException;

    /** Retorna o primeiro usuário com esse nome, ou {@code null}. */
    Usuario buscarPorNome(String nome) throws SQLException;

    /** Nomes que contêm o termo (máx. 8), usado no autocompletar. */
    List<String> sugerirNomes(String termo) throws SQLException;
}