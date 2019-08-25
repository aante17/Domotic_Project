package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class Estaciones extends AppCompatActivity {
    ProgressBar progressBarCircular; //inicializacion del loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones);
    }
    public void sala(View v) { //Implementacion de funcion sala
        Intent i = new Intent(this, Sala.class ); //creacion del Intent para un redireccionamiento de estaciones a sala
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar); //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }
    public void comedor(View v) {  //Implementacion de funcion comedor
        Intent i = new Intent(this, Comedor.class );  //creacion del Intent para un redireccionamiento de estaciones a comedor
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar); //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void jardin(View v) { //Implementacion de funcion jardin
        Intent i = new Intent(this, Jardin.class ); //creacion del Intent para un redireccionamiento de estaciones a jardin
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);  //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void garaje(View v) { //Implementacion de funcion garaje
        Intent i = new Intent(this, Garaje.class ); //creacion del Intent para un redireccionamiento de estaciones a garaje
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);  //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);
    }public void dormitorio(View v) { //Implementacion de funcion dormitorio
        Intent i = new Intent(this, Dormitorio.class ); //creacion del Intent para un redireccionamiento de estaciones a dormitorio
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);  //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);

    }
    public void instrucciones(View v) { //Implementacion de funcion instrucciones
        Intent i = new Intent(this, Instrucciones.class ); //creacion del Intent para un redireccionamiento de estaciones a instrucciones
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar);  //ejecuacion del loading
        progressBarCircular.setVisibility(View.VISIBLE);
        startActivity(i);

    }

    public void salir(View v) { //Implementacion de funcion salir
        Intent i = new Intent(this, LoginActivity.class ); //creacion del Intent para un redireccionamiento de estaciones a LoginActivity
        startActivity(i);
    }

}
