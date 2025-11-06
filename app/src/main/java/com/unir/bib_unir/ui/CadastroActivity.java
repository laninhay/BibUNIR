package com.unir.bib_unir.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
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
    private TextInputEditText edtTitulo, edtAno, edtAutor;
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
        edtTitulo = findViewById(R.id.edtTitulo);
        livroDAO = new LivroDAO(CadastroActivity.this);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
        if (id != 0){
            ContentValues cv = livroDAO.pesquisarPorId(id);
            String titulo = cv.getAsString("titulo");
            String autor = cv.getAsString("autor");
            int ano = cv.getAsInteger("ano");
            edtTitulo.setText(titulo);
            edtAutor.setText(autor);
            edtAno.setText(String.valueOf(ano));
        }
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                String titulo = edtTitulo.getText().toString();
                int ano = Integer.parseInt(edtAno.getText().toString());
                String autor = edtAutor.getText().toString();
                if (id != 0){
                    livroDAO.alterarRegistro(id, titulo, autor, ano);
                    msg = "Cadastro atualizado com sucesso!";
                    Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put("titulo", titulo); //o nome da chave deve ser o mesmo do campo da tabela;
                    cv.put("autor", autor);
                    cv.put("ano", ano);
                    long res = livroDAO.inserir(cv);
                    if (res > 0){
                        msg = "Cadastro efetuado com sucesso!";
                        Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }else{
                        msg = "Erro ao efetuar cadastro!";
                        Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                edtTitulo.setText("");
                edtAno.setText("");
                edtAutor.setText("");
                edtTitulo.requestFocus();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}