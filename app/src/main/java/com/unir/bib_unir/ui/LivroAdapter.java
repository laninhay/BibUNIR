package com.unir.bib_unir.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unir.bib_unir.R;
import com.unir.bib_unir.model.Livro;
import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    private List<Livro> listaLivros;
    private OnItemClickListener listener;

    // Interface para comunicar cliques com a Activity
    public interface OnItemClickListener {
        void onEditClick(Livro livro);
        void onDeleteClick(Livro livro);
    }

    public LivroAdapter(List<Livro> listaLivros, OnItemClickListener listener) {
        this.listaLivros = listaLivros;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livro, parent, false);
        return new LivroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        Livro livro = listaLivros.get(position);
        holder.txtId.setText("ID: " + livro.getId());
        holder.txtTitulo.setText(livro.getTitulo());
        holder.txtAutor.setText("Autor: " + livro.getAutor());
        holder.txtEditora.setText("Ed: " + livro.getEditora());
        holder.txtAno.setText(String.valueOf(livro.getAno()));

        // Clique Curto: Editar
        holder.itemView.setOnClickListener(v -> listener.onEditClick(livro));

        // Clique Longo: Remover
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDeleteClick(livro);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaLivros.size();
    }

    static class LivroViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtTitulo, txtAutor, txtEditora, txtAno;

        public LivroViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtItemId);
            txtTitulo = itemView.findViewById(R.id.txtItemTitulo);
            txtAutor = itemView.findViewById(R.id.txtItemAutor);
            txtEditora = itemView.findViewById(R.id.txtItemEditora);
            txtAno = itemView.findViewById(R.id.txtItemAno);
        }
    }
}