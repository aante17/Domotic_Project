package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Sala extends AppCompatActivity {
    TextView txtLuces;
    Switch swtLuces;
    TextView txtVentilador;
    Switch swtVentilador;
    ProgressBar progressBarCircular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);
        txtLuces = (TextView) findViewById(R.id.textView_Luces1);
        txtVentilador = (TextView) findViewById(R.id.textView_Ventilador1);
        swtVentilador= (Switch) findViewById(R.id.switch_Ventilador1);
        swtLuces = (Switch) findViewById(R.id.switch_Luces1);

        String resultadoConsultaLuces = new Ingreso().lectura_ingreso(11);

        if (Integer.parseInt(resultadoConsultaLuces) == 0) {
            swtLuces.setChecked(false);
            txtLuces.setText("Desactivado");
            Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtLuces.setChecked(true);
            txtLuces.setText("Activado" );
            Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


        String resultadoConsultaVentilador = new Ingreso().lectura_ingreso(12);

        if (Integer.parseInt(resultadoConsultaVentilador) == 0) { 
            swtVentilador.setChecked(false);
            txtVentilador.setText("Desactivado");
            Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtVentilador.setChecked(true);
            txtVentilador.setText("Activado");
            Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }

    }

    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class);
        startActivity(i);

    }


    public void onclicVentilador(View view){
        progressBarCircular = (ProgressBar)findViewById(R.id.progressBar2);
        progressBarCircular.setVisibility(View.VISIBLE);
        if (view.getId() == R.id.switch_Ventilador1){
            if (swtVentilador.isChecked()){
                txtVentilador.setText("Activado");
                Cambio_base cambio=new Cambio_base(12,1);
                Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                progressBarCircular.setVisibility(View.INVISIBLE);

            }else{
                txtVentilador.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(12,0);
                Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                progressBarCircular.setVisibility(View.INVISIBLE);

            }
        }
    }

    public void onclicLuces(View view){

        if (view.getId() == R.id.switch_Luces1){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                Cambio_base cambio=new Cambio_base(11,1);
                Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(11,0);
                Toast.makeText(Sala.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
