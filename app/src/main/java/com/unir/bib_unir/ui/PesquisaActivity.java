package com.unir.bib_unir.ui;

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

import com.unir.bib_unir.R;
import com.unir.bib_unir.database.LivroDAO;

import java.util.ArrayList;
import java.util.List;

public class PesquisaActivity extends AppCompatActivity {

    private TableLayout tabela;
    private Button btn_voltar;
    private LivroDAO livroDAO;

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
        tabela = findViewById(R.id.tableLayout);
        btn_voltar = findViewById(R.id.btnVoltarPesquisar);

        Intent intent = getIntent();
        int tipo = intent.getIntExtra("tipo", 0);
        String busca = intent.getStringExtra("busca");
        List<ContentValues> lista = new ArrayList<ContentValues>();
        livroDAO = new LivroDAO(PesquisaActivity.this);

        if (tipo == R.id.rbTitulo){
            lista = livroDAO.pesquisarPorTitulo(busca);
        }else if (tipo == R.id.rbAno){
            lista = livroDAO.pesquisarPorAno(Integer.parseInt(busca));
        }else if(tipo == R.id.rbTodos){
            lista = livroDAO.pesquisarPorTodos();
        }

        if (lista != null && lista.size() > 0){
            for (ContentValues cv : lista){
                //Cria cada linha tabela, adicionando suas colunas do tipo TextView;
                TableRow tr = new TableRow(getApplicationContext());
                TextView col1 = new TextView(getApplicationContext());
                col1.setText(String.valueOf(cv.getAsInteger("id")));
                tr.addView(col1);

                TextView col2 = new TextView(getApplicationContext());
                col2.setText(cv.getAsString("titulo"));
                tr.addView(col2);

                TextView col3 = new TextView(getApplicationContext());
                col3.setText(cv.getAsString("autor"));
                tr.addView(col3);

                TextView col4 = new TextView(getApplicationContext());
                col4.setText(String.valueOf(cv.getAsString("ano")));
                tr.addView(col4);

                tabela.setStretchAllColumns(true);
                tabela.setShrinkAllColumns(true);
                tabela.addView(tr);
            }
        }

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}