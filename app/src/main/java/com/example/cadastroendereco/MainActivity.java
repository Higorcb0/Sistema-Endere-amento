package com.example.cadastroendereco;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cadastroendereco.DBHelper.DBHelper;
import com.example.cadastroendereco.model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    DBHelper dbHelper;
    ArrayList<Produtos> listview_Produtos;
    Produtos produto;
    ArrayAdapter<Produtos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnCadastrar = findViewById(R.id.btn_Cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
                startActivity(intent);
            }
        });

        lista = findViewById(R.id.listview_Produtos);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produtos produtoEscolhido = (Produtos) adapter.getItem(position);
                Intent i = new Intent(MainActivity.this, FormularioProdutos.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produtos) adapter.getItemAtPosition(position);
                // Sua lógica aqui
                return false; // Retorne true se o evento foi consumido
            }
        });

        carregarProdutos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dbHelper = new DBHelper(MainActivity.this);
                dbHelper.deletarProduto(produto);
                dbHelper.close();
                carregarProdutos();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();
    }

    public void carregarProdutos() {
        dbHelper = new DBHelper(MainActivity.this);
        listview_Produtos = dbHelper.getListaProdutos();
        dbHelper.close();

        if (listview_Produtos != null) {
            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listview_Produtos);
            lista.setAdapter(adapter);
        }
    }
}
