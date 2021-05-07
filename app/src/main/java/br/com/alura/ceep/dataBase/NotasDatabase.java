package br.com.alura.ceep.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class NotasDatabase  extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "notas.db";
    public abstract NotaDAO getNotaDAO();

    public static NotasDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, NotasDatabase.class, NOME_BANCO_DE_DADOS)
                .build();
    }
}