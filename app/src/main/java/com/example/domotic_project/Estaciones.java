package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Estaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones);
    }
    public void sala(View v) {
        Intent i = new Intent(this, Sala.class );
        startActivity(i);
    }
    public void comedor(View v) {
        Intent i = new Intent(this, Comedor.class );
        startActivity(i);
    }public void jardin(View v) {
        Intent i = new Intent(this, Jardin.class );
        startActivity(i);
    }public void garaje(View v) {
        Intent i = new Intent(this, Garaje.class );
        startActivity(i);
    }public void dormitorio(View v) {
        Intent i = new Intent(this, Dormitorio.class );
        startActivity(i);

    }
    public void salir(View v) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }

}
