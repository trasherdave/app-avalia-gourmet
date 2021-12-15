package com.example.avaliagourmetapp.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.avaliagourmetapp.dao.AvaliacaoDao;
import com.example.avaliagourmetapp.model.Avaliacao;

@Database(entities = {Avaliacao.class}, version = 1)
public abstract class Banco extends RoomDatabase {
    private static final String DATABASE_NAME = "pessoadb";
    private static Banco INSTANCE;

    public static Banco getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context, Banco.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public abstract AvaliacaoDao avaliacaoDao();
}

