package br.com.alura.ceep.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private Long idNota;
    private String titulo;
    private String descricao;
    private String cor;
    private int posicao;


    public Nota(String titulo, String descricao, String cor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.posicao = -1;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCor(){
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}