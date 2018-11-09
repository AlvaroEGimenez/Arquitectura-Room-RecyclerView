package com.example.alva.arquitecturaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NuevaEditarNotaActivity extends AppCompatActivity {
    public static final String EDIT_ID = "com.example.alva.arquitecturaroom.TITULO_ID";
    public static final String EDIT_TITULO = "com.example.alva.arquitecturaroom.TITULO_EXTRA";
    public static final String EDIT_DESCRIPCION = "com.example.alva.arquitecturaroom.EDIT_DESCRIPCION";
    public static final String EDIT_PRIORIDAD = "com.example.alva.arquitecturaroom.EDIT_PRIORIDAD";

    private EditText editTexttitulo;
    private EditText editTextDescripcion;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);

        editTextDescripcion = findViewById(R.id.etDescripcion);
        editTexttitulo = findViewById(R.id.etTitulo);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EDIT_ID)){
            setTitle("Edit Note");
            editTexttitulo.setText(intent.getStringExtra(EDIT_TITULO));
            editTextDescripcion.setText(intent.getStringExtra(EDIT_DESCRIPCION));
            numberPicker.setValue(intent.getIntExtra(EDIT_PRIORIDAD,1));
        }
        else {
            setTitle("Agregar Nota");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.agregar_nota_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.guardar:
                guardarNota();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void guardarNota() {
        String titulo = editTexttitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        Integer prioroidad = numberPicker.getValue();

        if (titulo.trim().isEmpty() || descripcion.trim().isEmpty()){
            Toast.makeText(this,"Ingresar titulo y Descripcion",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent datos = new Intent();
        datos.putExtra(EDIT_TITULO,titulo);
        datos.putExtra(EDIT_DESCRIPCION,descripcion);
        datos.putExtra(EDIT_PRIORIDAD,prioroidad);

        int id = getIntent().getIntExtra(EDIT_ID,-1);
        if (id != -1){
            datos.putExtra(EDIT_ID,id);
        }

        setResult(RESULT_OK,datos);
        finish();


    }
}
