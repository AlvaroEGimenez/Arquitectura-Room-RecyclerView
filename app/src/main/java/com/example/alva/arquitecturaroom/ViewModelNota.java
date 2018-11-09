package com.example.alva.arquitecturaroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ViewModelNota extends AndroidViewModel {
    private RepositorioNota repositorio;
    private LiveData<List<Nota>> allNotes;

    public ViewModelNota(@NonNull Application application) {
        super(application);
        repositorio = new RepositorioNota(application);
        allNotes = repositorio.getAllNotas();
    }

    public void insert(Nota nota){
        repositorio.insert(nota);
    }

    public void  update(Nota nota){
        repositorio.update(nota);
    }

    public void delete(Nota nota){
        repositorio.delete(nota);
    }

    public void  deleteALl(){
        repositorio.deleteAll();
    }

    public LiveData<List<Nota>> getAllNotes() {
        return allNotes;
    }
}
