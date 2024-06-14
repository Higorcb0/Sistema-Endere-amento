

        package com.example.cadastroendereco.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cadastroendereco.model.Local;
import com.example.cadastroendereco.model.Produtos;
import com.example.cadastroendereco.model.Endereco;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GeralBd";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_PRODUTOS = "produtos";
    private static final String COLUMN_PRODUTO_ID = "id";
    private static final String COLUMN_NOME_PRODUTO = "nomeproduto";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_QUANTIDADE = "quantidade";

    private static final String TABLE_LOCAL = "local";
    private static final String COLUMN_LOCAL_ID = "id";
    private static final String COLUMN_NOME_LOCAL = "nome";

    private static final String TABLE_ENDERECO = "endereco";
    private static final String COLUMN_ENDERECO_ID = "id";
    private static final String COLUMN_RUA = "rua";
    private static final String COLUMN_PREDIO = "predio";
    private static final String COLUMN_ANDAR = "andar";
    private static final String COLUMN_APARTAMENTO = "apartamento";
    private static final String COLUMN_USUARIO_ID = "usuario_id";
    private static final String COLUMN_LOCAL_ID_ENDERECO = "local_id";

    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela de usuários
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Criar tabela de produtos
        String CREATE_PRODUTOS_TABLE = "CREATE TABLE " + TABLE_PRODUTOS + "("
                + COLUMN_PRODUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOME_PRODUTO + " TEXT,"
                + COLUMN_DESCRICAO + " TEXT,"
                + COLUMN_QUANTIDADE + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUTOS_TABLE);

        // Criar tabela de local
        String CREATE_LOCAL_TABLE = "CREATE TABLE " + TABLE_LOCAL + "("
                + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOME_LOCAL + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_LOCAL_TABLE);

        // Criar tabela de endereco
        String CREATE_ENDERECO_TABLE = "CREATE TABLE " + TABLE_ENDERECO + "("
                + COLUMN_ENDERECO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_RUA + " TEXT,"
                + COLUMN_PREDIO + " TEXT,"
                + COLUMN_ANDAR + " TEXT,"
                + COLUMN_APARTAMENTO + " TEXT,"
                + COLUMN_USUARIO_ID + " INTEGER,"
                + COLUMN_LOCAL_ID_ENDERECO + " INTEGER,"
                + " FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "),"
                + " FOREIGN KEY (" + COLUMN_LOCAL_ID_ENDERECO + ") REFERENCES " + TABLE_LOCAL + "(" + COLUMN_LOCAL_ID + ")"
                + ")";
        db.execSQL(CREATE_ENDERECO_TABLE);

        Log.d(TAG, "Tabelas criadas com sucesso");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tabelas se existirem
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENDERECO);
        onCreate(db);
        Log.d(TAG, "Tabelas atualizadas com sucesso");
    }

    // Métodos para operações com Login

    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Métodos para operações com Produtos

    public void salvarProduto(Produtos produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOME_PRODUTO, produto.getNomeProduto());
            values.put(COLUMN_DESCRICAO, produto.getDescricao());
            values.put(COLUMN_QUANTIDADE, produto.getQuantidade());

            long result = db.insert(TABLE_PRODUTOS, null, values);
            if (result != -1) {
                Log.d(TAG, "Produto salvo com sucesso: " + produto);
            } else {
                Log.e(TAG, "Erro ao salvar o produto: " + produto);
            }
        } finally {
            db.close();
        }
    }

    public void alterarProduto(Produtos produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOME_PRODUTO, produto.getNomeProduto());
            values.put(COLUMN_DESCRICAO, produto.getDescricao());
            values.put(COLUMN_QUANTIDADE, produto.getQuantidade());

            String[] args = {String.valueOf(produto.getId())};
            int rowsAffected = db.update(TABLE_PRODUTOS, values, COLUMN_PRODUTO_ID + "=?", args);
            if (rowsAffected > 0) {
                Log.d(TAG, "Produto alterado com sucesso: " + produto);
            } else {
                Log.e(TAG, "Erro ao alterar o produto: " + produto);
            }
        } finally {
            db.close();
        }
    }

    public void deletarProduto(Produtos produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String[] args = {String.valueOf(produto.getId())};
            int rowsAffected = db.delete(TABLE_PRODUTOS, COLUMN_PRODUTO_ID + "=?", args);
            if (rowsAffected > 0) {
                Log.d(TAG, "Produto deletado com sucesso: " + produto);
            } else {
                Log.e(TAG, "Erro ao deletar o produto: " + produto);
            }
        } finally {
            db.close();
        }
    }

    public ArrayList<Produtos> getListaProdutos() {
        ArrayList<Produtos> produtos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COLUMN_PRODUTO_ID, COLUMN_NOME_PRODUTO, COLUMN_DESCRICAO, COLUMN_QUANTIDADE};
            cursor = db.query(TABLE_PRODUTOS, columns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Produtos produto = new Produtos();
                produto.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PRODUTO_ID)));
                produto.setNomeProduto(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME_PRODUTO)));
                produto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO)));
                produto.setQuantidade(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE)));
                produtos.add(produto);
            }
            Log.d(TAG, "Lista de produtos carregada com sucesso: " + produtos.size() + " itens.");
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar a lista de produtos", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return produtos;
    }

    // Métodos para operações com Local

    public long inserirLocal(String nomeLocal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME_LOCAL, nomeLocal);

        long id = -1;
        try {
            id = db.insert(TABLE_LOCAL, null, values);
            Log.d(TAG, "Local inserido com sucesso. ID: " + id);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao inserir local: " + e.getMessage(), e);
        } finally {
            db.close();
        }

        return id;
    }





    public ArrayList<Local> getLocais() {
        ArrayList<Local> locais = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COLUMN_LOCAL_ID, COLUMN_NOME_LOCAL};
            cursor = db.query(TABLE_LOCAL, columns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Local local = new Local();
                local.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LOCAL_ID)));
                local.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME_LOCAL)));
                locais.add(local);
            }
            Log.d(TAG, "Lista de locais carregada com sucesso: " + locais.size() + " itens.");
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar a lista de locais", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return locais;
    }

    // Métodos para operações com Endereco

    public long inserirEndereco(Endereco endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RUA, endereco.getRua());
        values.put(COLUMN_PREDIO, endereco.getPredio());
        values.put(COLUMN_ANDAR, endereco.getAndar());
        values.put(COLUMN_APARTAMENTO, endereco.getApartamento());
        values.put(COLUMN_USUARIO_ID, endereco.getUsuarioId());

        long id = db.insert(TABLE_ENDERECO, null, values);
        db.close();
        return id;
    }



    public ArrayList<Endereco> getEnderecos() {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] columns = {COLUMN_ENDERECO_ID, COLUMN_RUA, COLUMN_PREDIO, COLUMN_ANDAR, COLUMN_APARTAMENTO, COLUMN_USUARIO_ID, COLUMN_LOCAL_ID_ENDERECO};
            cursor = db.query(TABLE_ENDERECO, columns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Endereco endereco = new Endereco();
                endereco.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ENDERECO_ID)));
                endereco.setRua(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUA)));
                endereco.setPredio(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREDIO)));
                endereco.setAndar(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANDAR))); // Se for um campo inteiro no banco
                endereco.setApartamento(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APARTAMENTO)));
                endereco.setUsuarioId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USUARIO_ID)));
                endereco.setLocalId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LOCAL_ID_ENDERECO)));
                enderecos.add(endereco);
            }
            Log.d(TAG, "Lista de endereços carregada com sucesso: " + enderecos.size() + " itens.");
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar a lista de endereços", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return enderecos;
    }


    public void alterarEndereco(Endereco endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_RUA, endereco.getRua());
            values.put(COLUMN_PREDIO, endereco.getPredio());
            values.put(COLUMN_ANDAR, endereco.getAndar());
            values.put(COLUMN_APARTAMENTO, endereco.getApartamento());
            values.put(COLUMN_USUARIO_ID, endereco.getUsuarioId()); // Aqui alterei para long

            String[] args = {String.valueOf(endereco.getId())};
            int rowsAffected = db.update(TABLE_ENDERECO, values, COLUMN_ENDERECO_ID + "=?", args);
            if (rowsAffected > 0) {
                Log.d(TAG, "Endereço alterado com sucesso: " + endereco);
            } else {
                Log.e(TAG, "Erro ao alterar o endereço: " + endereco);
            }
        } finally {
            db.close();
        }
    }



    public void deletarEndereco(Endereco endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String[] args = {String.valueOf(endereco.getId())};
            int rowsAffected = db.delete(TABLE_ENDERECO, COLUMN_ENDERECO_ID + "=?", args);
            if (rowsAffected > 0) {
                Log.d(TAG, "Endereço deletado com sucesso: " + endereco);
            } else {
                Log.e(TAG, "Erro ao deletar o endereço: " + endereco);
            }
        } finally {
            db.close();
        }
    }
}
