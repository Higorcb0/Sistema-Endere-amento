package com.example.cadastroendereco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastroendereco.DBHelper.DBHelper;
import com.example.cadastroendereco.model.Produtos;

public class FormularioProdutos extends AppCompatActivity {
    EditText editText_NomeProduto, editText_Descricao, editText_Quantidade;
    Button btn_polimorfismo, btnCadastrarEndereco, btnCadastrarLocal;
    Produtos editarProduto, produto;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_produtos);

        produto = new Produtos();
        dbHelper = new DBHelper(FormularioProdutos.this);

        Intent intent = getIntent();
        editarProduto = (Produtos) intent.getSerializableExtra("produto-escolhido");

        editText_NomeProduto = findViewById(R.id.editText_NomeProduto);
        editText_Descricao = findViewById(R.id.editText_Descricao);
        editText_Quantidade = findViewById(R.id.editText_Quantidade);
        btn_polimorfismo = findViewById(R.id.btn_polimorfismo);
        btnCadastrarEndereco = findViewById(R.id.btn_CadastrarEndereco);
        btnCadastrarLocal = findViewById(R.id.btn_CadastrarLocal);

        if (editarProduto != null) {
            btn_polimorfismo.setText("Modificar");
            editText_NomeProduto.setText(editarProduto.getNomeProduto());
            editText_Descricao.setText(editarProduto.getDescricao());
            editText_Quantidade.setText(String.valueOf(editarProduto.getQuantidade()));
            produto.setId(editarProduto.getId());
        } else {
            btn_polimorfismo.setText("Cadastrar");
        }

        btn_polimorfismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setNomeProduto(editText_NomeProduto.getText().toString());
                produto.setDescricao(editText_Descricao.getText().toString());
                produto.setQuantidade(Integer.parseInt(editText_Quantidade.getText().toString()));

                if (btn_polimorfismo.getText().toString().equals("Cadastrar")) {
                    dbHelper.salvarProduto(produto);
                } else {
                    dbHelper.alterarProduto(produto);
                }
                dbHelper.close();
                finish();
            }
        });

        btnCadastrarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioProdutos.this, EnderecoActivity.class);
                startActivity(intent);
            }
        });

        btnCadastrarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioProdutos.this, LocalActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}