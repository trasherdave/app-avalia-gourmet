package com.example.avaliagourmetapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.avaliagourmetapp.model.Avaliacao;

import java.util.List;


@Dao
public interface AvaliacaoDao {

    @Insert
    public long adicionar(Avaliacao a);

    @Query("SELECT * FROM avaliacao")
    public List<Avaliacao> listar();

    @Update
    public int atualizar(Avaliacao a);

    @Delete
    public int excluir(Avaliacao a);


}
