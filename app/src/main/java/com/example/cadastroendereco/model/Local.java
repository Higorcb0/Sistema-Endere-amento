package com.example.cadastroendereco.model;

public class Local {

    private long id;
    private String nome;

    public Local() {
        // Construtor vazio necessário para SQLite
    }

    public Local(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}

