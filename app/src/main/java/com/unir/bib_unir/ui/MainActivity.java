package com.unir.bib_unir.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.unir.bib_unir.R;
import com.unir.bib_unir.database.LivroDAO;

public class MainActivity extends AppCompatActivity {

    private Button btnPesquisar, btnCadastrar, btnRemover, btnAtualizar;
    private RadioGroup rd_PesquisarPor;
    private TextInputEditText edtiPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        btnPesquisar = findViewById(R.id.btnPesquisar);
        edtiPesquisar = findViewById(R.id.edtTituloPesquisa);
        rd_PesquisarPor = findViewById(R.id.radioGroup);
        btnRemover = findViewById(R.id.btnRemover);
        btnAtualizar = findViewById(R.id.btnAtualizar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PesquisaActivity.class);
                int tipo = rd_PesquisarPor.getCheckedRadioButtonId();
                String busca = edtiPesquisar.getText().toString();
                intent.putExtra("tipo", tipo);
                intent.putExtra("busca", busca);
                startActivity(intent);
            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remover livro");
                builder.setIcon(R.drawable.unir);
                builder.setMessage("Informe o ID do livro:");
                EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Ex: 123");
                builder.setView(input);
                builder.setNegativeButton("Cancelar", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LivroDAO livroDAO = new LivroDAO(MainActivity.this);
                        int id = (input.getText().toString().isEmpty()) ? 0 : Integer.parseInt(input.getText().toString());
                        livroDAO.deletarRegistro(id);
                        Toast.makeText(MainActivity.this, "Registro removido com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Atualizar livro");
                builder.setIcon(R.drawable.unir);
                builder.setMessage("Informe o ID do livro:");
                EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Ex: 123");
                builder.setView(input);
                builder.setNegativeButton("Cancelar", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = (input.getText().toString().isEmpty()) ? 0 : Integer.parseInt(input.getText().toString());
                        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });
                builder.create().show();
            }
        });

    }
}