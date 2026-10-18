package com.mycompany.biblioteca;

import java.time.LocalDate;

/** Entidade que representa um empréstimo de livro a um usuário. */
public class Emprestimo {
    private int id;
    private int usuarioId;
    private int livroId;
    private String usuarioNome;   // preenchidos pelo JOIN na listagem
    private String livroTitulo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private String status;

    public Emprestimo(int usuarioId, int livroId) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getLivroId() { return livroId; }
    public String getUsuarioNome() { return usuarioNome; }
    public String getLivroTitulo() { return livroTitulo; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
    public void setLivroTitulo(String livroTitulo) { this.livroTitulo = livroTitulo; }
    public void setDataEmprestimo(LocalDate d) { this.dataEmprestimo = d; }
    public void setDataDevolucao(LocalDate d) { this.dataDevolucao = d; }
    public void setStatus(String status) { this.status = status; }
}