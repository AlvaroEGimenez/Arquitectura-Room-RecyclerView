package com.example.alva.arquitecturaroom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Nota.class}, version = 1)
public abstract class Nota_DB extends RoomDatabase {

    private static Nota_DB intancia;

    public abstract NotaDAO notaDAO();

    public static synchronized Nota_DB getIntancia(Context context) {
        if (intancia == null) {
            intancia = Room.databaseBuilder(context.getApplicationContext(),
                    Nota_DB.class, "nota_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return intancia;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(intancia).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotaDAO notaDAO;

        private PopulateDbAsyncTask(Nota_DB db) {
            notaDAO = db.notaDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notaDAO.insert(new Nota("Titulo 1", "Descripcion 1", 1));
            notaDAO.insert(new Nota("Titulo 2", "Descripcion 2", 2));
            notaDAO.insert(new Nota("Titulo 3", "Descripcion 3", 3));
            return null;
        }
    }
}
