package com.unir.bib_unir.database;

import android.content.ContentValues;
import java.util.List;

public interface ILivroDAO {
    public ContentValues pesquisarPorId(int id);
    public List<ContentValues> pesquisarPorTitulo(String titulo);
    public List<ContentValues> pesquisarPorAno(int ano);
    public List<ContentValues> pesquisarPorTodos();
    public long inserir(ContentValues cv);

    // Método atualizado com o novo parâmetro 'editora'
    public void alterarRegistro(int id, String titulo, String autor, String editora, int ano);

    public void deletarRegistro(int id);
}