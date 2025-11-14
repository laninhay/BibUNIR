package com.unir.bib_unir.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unir.bib_unir.R;
import com.unir.bib_unir.database.LivroDAO;
import com.unir.bib_unir.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class PesquisaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LivroDAO livroDAO;
    private LivroAdapter adapter;
    private List<Livro> listaLivrosModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesquisa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewLivros);
        Button btn_voltar = findViewById(R.id.btnVoltarPesquisar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        livroDAO = new LivroDAO(this);

        carregarDados();

        btn_voltar.setOnClickListener(view -> finish());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega os dados caso tenha voltado de uma edição
        carregarDados();
    }

    private void carregarDados() {
        Intent intent = getIntent();
        int tipo = intent.getIntExtra("tipo", 0);
        String busca = intent.getStringExtra("busca");
        List<ContentValues> listaCV;

        if (tipo == R.id.rbTitulo){
            listaCV = livroDAO.pesquisarPorTitulo(busca);
        } else if (tipo == R.id.rbAno && !busca.isEmpty()){
            listaCV = livroDAO.pesquisarPorAno(Integer.parseInt(busca));
        } else {
            listaCV = livroDAO.pesquisarPorTodos();
        }

        // Converter ContentValues para List<Livro> (Model)
        listaLivrosModel = new ArrayList<>();
        if (listaCV != null) {
            for (ContentValues cv : listaCV) {
                // Verifica se a coluna existe para evitar erros se o DB não atualizou corretamente
                String editora = cv.containsKey("editora") ? cv.getAsString("editora") : "";

                listaLivrosModel.add(new Livro(
                        cv.getAsInteger("id"),
                        cv.getAsString("titulo"),
                        cv.getAsString("autor"),
                        editora,
                        cv.getAsInteger("ano")
                ));
            }
        }

        // Configura Adapter com lógica de Clique e Long Clique
        adapter = new LivroAdapter(listaLivrosModel, new LivroAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Livro livro) {
                // 3. Clique Curto: Atualizar
                Intent intent = new Intent(PesquisaActivity.this, CadastroActivity.class);
                intent.putExtra("id", livro.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Livro livro) {
                // 3. Clique Longo: Remover
                new AlertDialog.Builder(PesquisaActivity.this)
                        .setTitle("Remover Livro")
                        .setMessage("Deseja remover '" + livro.getTitulo() + "'?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            livroDAO.deletarRegistro(livro.getId());
                            carregarDados(); // Recarrega a lista
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}