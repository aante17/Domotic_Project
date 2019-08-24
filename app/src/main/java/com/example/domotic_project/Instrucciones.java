package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Instrucciones extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrucciones);
        txt =(TextView) findViewById(R.id.textViewInstrucciones);
        txt.setText("Luces:            Encender luces" +"\n"+
                    "                        Apagar luces"+"\n"+"\n"+
                    "Ventilador:    Encender ventilador"+"\n"+
                    "                       Apagar ventilador"+"\n"+"\n"+
                    "Persinas:      Elevar persianas" +"\n"+
                    "                      Bajar persianas"+"\n"+"\n"+
                    "Puertas:       Abrir puertas" +"\n"+
                    "                      Cerrar puertas"+"\n"+"\n"+
                    "Alarma:        Activar alarma" +"\n"+
                    "                      Desactivar alarma"+"\n");

    }
    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);
    }


}
