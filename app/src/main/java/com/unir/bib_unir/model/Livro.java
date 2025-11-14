package com.unir.bib_unir.model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int ano;

    public Livro(int id, String titulo, String autor, String editora, int ano) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getEditora() { return editora; }
    public int getAno() { return ano; }
}