package com.example.cadastroendereco.model;

import java.io.Serializable;

public class Endereco implements Serializable {
    private long id; // Alterado para long, se necessário
    private String rua;
    private String predio;
    private int andar;
    private String apartamento;
    private long usuarioId;
    private long localId;

    public Endereco() {}

    public Endereco(long id, String rua, String predio, int andar, String apartamento, int usuarioId, int localId) {
        this.id = id;
        this.rua = rua;
        this.predio = predio;
        this.andar = andar;
        this.apartamento = apartamento;
        this.usuarioId = usuarioId;
        this.localId = localId;
    }

    // Getters e setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public int getAndar() {
        return andar;
    }

    // Método setAndar que aceita tanto int quanto String
    public void setAndar(int andar) {
        this.andar = andar;
    }

    public void setAndar(String andarStr) {
        try {
            this.andar = Integer.parseInt(andarStr);
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Ou trate o erro conforme necessário
        }
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", rua='" + rua + '\'' +
                ", predio='" + predio + '\'' +
                ", andar=" + andar +
                ", apartamento='" + apartamento + '\'' +
                ", usuarioId=" + usuarioId +
                ", localId=" + localId +
                '}';
    }
}
