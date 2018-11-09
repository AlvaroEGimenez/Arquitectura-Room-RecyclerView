package com.example.alva.arquitecturaroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int AGREGAR_NOTA_REQUEST = 1;
    public static final int EDITAR_NOTA_REQUEST = 2;

    private ViewModelNota viewModelNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.btnFloating);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NuevaEditarNotaActivity.class);
                startActivityForResult(intent, AGREGAR_NOTA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AdaptadorNota adaptadorNota = new AdaptadorNota();
        recyclerView.setAdapter(adaptadorNota);

        viewModelNota = ViewModelProviders.of(this).get(ViewModelNota.class);
        viewModelNota.getAllNotes().observe(this, new Observer<List<Nota>>() {
            @Override
            public void onChanged(@Nullable List<Nota> notas) {
                //Actuliza el RecyclerView
                adaptadorNota.submitList(notas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direccion) {
                viewModelNota.delete(adaptadorNota.getNotaEn(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Nota borrada", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adaptadorNota.setOnItemClickListener(new AdaptadorNota.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent intent = new Intent(MainActivity.this, NuevaEditarNotaActivity.class);
                intent.putExtra(NuevaEditarNotaActivity.EDIT_ID, nota.getId());
                intent.putExtra(NuevaEditarNotaActivity.EDIT_TITULO, nota.getTitulo());
                intent.putExtra(NuevaEditarNotaActivity.EDIT_DESCRIPCION, nota.getDescripcion());
                intent.putExtra(NuevaEditarNotaActivity.EDIT_PRIORIDAD, nota.getPrioridad());
                startActivityForResult(intent, EDITAR_NOTA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AGREGAR_NOTA_REQUEST && resultCode == RESULT_OK) {
            String titulo = data.getStringExtra(NuevaEditarNotaActivity.EDIT_TITULO);
            String descripcion = data.getStringExtra(NuevaEditarNotaActivity.EDIT_DESCRIPCION);
            int prioridad = data.getIntExtra(NuevaEditarNotaActivity.EDIT_PRIORIDAD, 1);

            Nota nota = new Nota(titulo, descripcion, prioridad);
            viewModelNota.insert(nota);
            Toast.makeText(this, "Nota Agregada", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDITAR_NOTA_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NuevaEditarNotaActivity.EDIT_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "La nota no se pudo actualizar", Toast.LENGTH_SHORT).show();
                return;
            }

            String titulo = data.getStringExtra(NuevaEditarNotaActivity.EDIT_TITULO);
            String descripcion = data.getStringExtra(NuevaEditarNotaActivity.EDIT_DESCRIPCION);
            int prioridad = data.getIntExtra(NuevaEditarNotaActivity.EDIT_PRIORIDAD, 1);

            Nota nota = new Nota(titulo, descripcion, prioridad);
            nota.setId(id);
            viewModelNota.update(nota);
            Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borrarTodo:
                viewModelNota.deleteALl();
                Toast.makeText(this, "Se borraron todas las notas", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
