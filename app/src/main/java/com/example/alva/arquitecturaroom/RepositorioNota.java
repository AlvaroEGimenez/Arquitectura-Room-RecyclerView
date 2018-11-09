package com.example.alva.arquitecturaroom;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RepositorioNota {

    private NotaDAO notaDAO;
    private LiveData<List<Nota>> allNotas;

    public  RepositorioNota (Application application){
        Nota_DB nota_db = Nota_DB.getIntancia(application);
        notaDAO =nota_db.notaDAO();
        allNotas = notaDAO.getALL();
    }

    public void insert (Nota nota){
        new InsertNotaAsyncTask(notaDAO).execute(nota);

    }

    public void  update (Nota nota){
        new UpdateNotaAsyncTask(notaDAO).execute(nota);

    }

    public  void delete (Nota nota){
        new DeleteNotaAsyncTask(notaDAO).execute(nota);

    }

    public void  deleteAll (){
        new DeleteAllNotaAsyncTask(notaDAO).execute();

    }

    public LiveData<List<Nota>> getAllNotas() {
        return allNotas;
    }

    private static class  InsertNotaAsyncTask extends AsyncTask<Nota,Void,Void>{
        private NotaDAO notaDAO;

        private InsertNotaAsyncTask(NotaDAO notaDAO){
            this.notaDAO = notaDAO;
        }
        @Override
        protected Void doInBackground(Nota... notas) {
            notaDAO.insert(notas[0]);
            return null;
        }
    }

    private static class  UpdateNotaAsyncTask extends AsyncTask<Nota,Void,Void>{
        private NotaDAO notaDAO;

        private UpdateNotaAsyncTask(NotaDAO notaDAO){
            this.notaDAO = notaDAO;
        }
        @Override
        protected Void doInBackground(Nota... notas) {
            notaDAO.update(notas[0]);
            return null;
        }
    }

    private static class  DeleteNotaAsyncTask extends AsyncTask<Nota,Void,Void>{
        private NotaDAO notaDAO;

        private DeleteNotaAsyncTask(NotaDAO notaDAO){
            this.notaDAO = notaDAO;
        }
        @Override
        protected Void doInBackground(Nota... notas) {
            notaDAO.delelte(notas[0]);
            return null;
        }
    }

    private static class  DeleteAllNotaAsyncTask extends AsyncTask<Void,Void,Void>{
        private NotaDAO notaDAO;

        private DeleteAllNotaAsyncTask(NotaDAO notaDAO){
            this.notaDAO = notaDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            notaDAO.deleteALL();
            return null;
        }
    }
}
