package com.example.cadastroendereco.model;

import java.io.Serializable;

public class Produtos implements Serializable {
    private Long id;
    private String nomeProduto;
    private String descricao;
    private int quantidade;

    public Produtos() {
        // Construtor vazio necessário para operações com o banco de dados
    }

    public Produtos(String nomeProduto, String descricao, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto: " + nomeProduto + ", Descrição: " + descricao + ", Quantidade: " + quantidade;
    }
}
