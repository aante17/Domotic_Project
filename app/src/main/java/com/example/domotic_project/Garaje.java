package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Garaje extends AppCompatActivity {
    TextView txtLuces;
    Switch swtLuces;
    TextView txtPuerta;
    Switch swtPuerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garaje);

        txtLuces = (TextView) findViewById(R.id.textView_Luces3);
        txtPuerta = (TextView) findViewById(R.id.textView_Puerta3);
        swtPuerta = (Switch) findViewById(R.id.switch_Puerta3);
        swtLuces = (Switch) findViewById(R.id.switch_Luces3);

        String resultadoConsultaLuces = new Ingreso().lectura_ingreso(32);

        if (Integer.parseInt(resultadoConsultaLuces) == 0) {
            swtLuces.setChecked(false);
            txtLuces.setText("Desactivado");
            Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtLuces.setChecked(true);
            txtLuces.setText("Activado" );
            Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


        String resultadoConsultaPuerta = new Ingreso().lectura_ingreso(31);

        if (Integer.parseInt(resultadoConsultaPuerta) == 0) {
            swtPuerta.setChecked(false);
            txtPuerta.setText("Desactivado");
            Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtPuerta.setChecked(true);
            txtPuerta.setText("Activado");
            Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }

    }
    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }
    public void onclicPuerta(View view){
        if (view.getId() == R.id.switch_Puerta3){
            if (swtPuerta.isChecked()){
                txtPuerta.setText("Activado");
                Cambio_base cambio=new Cambio_base(31,1);
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtPuerta.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(31,0);
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){
        if (view.getId() == R.id.switch_Luces3){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                Cambio_base cambio=new Cambio_base(32,1);
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(32,0);
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
