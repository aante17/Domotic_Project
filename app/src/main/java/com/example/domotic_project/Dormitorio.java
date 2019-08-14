package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Dormitorio extends AppCompatActivity {

    TextView txtLuces;
    Switch swtLuces;
    TextView txtPersianas;
    Switch swtPersinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitorio);
        txtLuces = (TextView) findViewById(R.id.textView_Luces2);
        txtPersianas = (TextView) findViewById(R.id.textView_Persianas2);
        swtPersinas = (Switch) findViewById(R.id.switch_Persianas2);
        swtLuces = (Switch) findViewById(R.id.switch_Luces2);

        String resultadoConsultaPersianas = new Ingreso().lectura_ingreso(22);

        if (Integer.parseInt(resultadoConsultaPersianas) == 0) {

            swtPersinas.setChecked(false);
            txtPersianas.setText("Desactivado");
            Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtPersinas.setChecked(true);
            txtPersianas.setText("Activado" );
            Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


        String resultadoConsultaLuces = new Ingreso().lectura_ingreso(21);

        if (Integer.parseInt(resultadoConsultaLuces) == 0) {
            swtLuces.setChecked(false);
            txtLuces.setText("Desactivado");
            Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtLuces.setChecked(true);
            txtLuces.setText("Activado");
            Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }
    }

    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }

    public void onclicPersianas(View view){
        if (view.getId() == R.id.switch_Persianas2){
            if (swtPersinas.isChecked()){
                txtPersianas.setText("Activado");
                Cambio_base cambio=new Cambio_base(22,1);
                Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtPersianas.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(22,0);
                Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){
        if (view.getId() == R.id.switch_Luces2){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                Cambio_base cambio=new Cambio_base(21,1);
                Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(21,0);
                Toast.makeText(Dormitorio.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
