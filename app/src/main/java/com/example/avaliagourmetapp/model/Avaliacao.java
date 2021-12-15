package com.example.avaliagourmetapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Avaliacao implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String dados;

    public Avaliacao(int id, String dados) {
        this.id = id;
        this.dados = dados;
    }

    public Avaliacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    @Override
    public String toString() {
        return dados;
    }
}//Class
