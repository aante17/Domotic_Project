package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class Estaciones extends AppCompatActivity {
    ProgressBar progressBarCircular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones);
    }
    public void sala(View v) {
        Intent i = new Intent(this, Sala.class );
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }
    public void comedor(View v) {
        Intent i = new Intent(this, Comedor.class );
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void jardin(View v) {
        Intent i = new Intent(this, Jardin.class );
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void garaje(View v) {
        Intent i = new Intent(this, Garaje.class );
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void dormitorio(View v) {
        Intent i = new Intent(this, Dormitorio.class );
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);

    }
    public void salir(View v) {
        Intent i = new Intent(this, LoginActivity.class );
        startActivity(i);
    }

}
