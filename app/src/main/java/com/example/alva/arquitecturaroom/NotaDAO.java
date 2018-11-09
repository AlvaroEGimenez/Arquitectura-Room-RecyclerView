package com.example.alva.arquitecturaroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NotaDAO {

    @Insert
    void  insert (Nota nota);

    @Update
    void update (Nota nota);

    @Delete
    void delelte (Nota nota);

    @Query("DELETE FROM tabla_nota")
    void deleteALL ();

    @Query("SELECT * FROM tabla_nota ORDER BY prioridad DESC")
    LiveData<List<Nota>> getALL ();
}
