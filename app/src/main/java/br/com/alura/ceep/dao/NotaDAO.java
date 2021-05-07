package br.com.alura.ceep.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.ceep.model.Nota;


@Dao
public interface NotaDAO {

    @Insert
    void insere(Nota... notas);

    @Query("SELECT * FROM Nota " +
            "ORDER BY posicao ASC")
    List<Nota> todos();

    @Query("SELECT * FROM Nota " +
            "WHERE idNota = :idNota")
    Nota buscaNota(long idNota);

    @Query("SELECT * FROM Nota " +
            "WHERE posicao = :posicao")
    Nota buscaNotaPelaPosicao(int posicao);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void altera(List<Nota> todasNotas);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void alteraNota(Nota nota);

    @Delete
    void remove(Nota nota);

    @Query("DELETE FROM Nota where posicao = :posicao")
    void removePelaPosicao(int posicao);

    @Query("SELECT COUNT(*) FROM Nota")
    int ultimaPosicao();
}
