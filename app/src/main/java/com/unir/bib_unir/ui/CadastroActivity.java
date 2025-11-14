package com.unir.bib_unir.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.unir.bib_unir.R;
import com.unir.bib_unir.database.LivroDAO;

public class CadastroActivity extends AppCompatActivity {

    private Button btnVoltar, btnSalvar;
    private TextInputEditText edtTitulo, edtAno, edtAutor, edtEditora;
    private LivroDAO livroDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVoltar = findViewById(R.id.btnVoltarCadastro);
        btnSalvar = findViewById(R.id.btnSalvarCadastro);
        edtAno = findViewById(R.id.edtAno);
        edtAutor = findViewById(R.id.edtAutor);
        edtEditora = findViewById(R.id.edtEditora);
        edtTitulo = findViewById(R.id.edtTitulo);
        livroDAO = new LivroDAO(CadastroActivity.this);

        // Verifica se é uma edição (veio um ID na intent)
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
        if (id != 0){
            // Preenche os campos com os dados existentes
            ContentValues cv = livroDAO.pesquisarPorId(id);
            edtTitulo.setText(cv.getAsString("titulo"));
            edtAutor.setText(cv.getAsString("autor"));
            edtEditora.setText(cv.getAsString("editora"));
            edtAno.setText(String.valueOf(cv.getAsInteger("ano")));
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = edtTitulo.getText().toString();
                String autor = edtAutor.getText().toString();
                String editora = edtEditora.getText().toString();
                String anoStr = edtAno.getText().toString();

                // 1. Validação de Campos
                if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(autor) ||
                        TextUtils.isEmpty(editora) || TextUtils.isEmpty(anoStr)) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int ano = Integer.parseInt(anoStr);

                if (id != 0){
                    // Atualizar com editora
                    livroDAO.alterarRegistro(id, titulo, autor, editora, ano);
                    Toast.makeText(CadastroActivity.this, "Atualizado!", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put("titulo", titulo);
                    cv.put("autor", autor);
                    cv.put("editora", editora);
                    cv.put("ano", ano);
                    long res = livroDAO.inserir(cv);
                    if (res > 0){
                        Toast.makeText(CadastroActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro.", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

        btnVoltar.setOnClickListener(v -> finish());

    }
}