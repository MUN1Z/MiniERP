package br.com.teste.minierp.minierp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Felip on 21/11/2017.
 */

public class Produto {

    @SerializedName("produtoId")
    private int produtoId;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("codBarras")
    private String codBarras;

    @SerializedName("valor")
    private float valor;

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Produto() {
    }
}
