package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Comedor extends AppCompatActivity {
    TextView txtLuces;
    Switch swtLuces;
    TextView txtVentilador;
    Switch swtVentilador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor);
        txtLuces = (TextView) findViewById(R.id.textView_Luces5);
        txtVentilador = (TextView) findViewById(R.id.textView_Ventilador5);
        swtVentilador= (Switch) findViewById(R.id.switch_Ventilador5);
        swtLuces = (Switch) findViewById(R.id.switch_Luces5);

        String resultadoConsultaLuces = new Ingreso().lectura_ingreso(52);

        if (Integer.parseInt(resultadoConsultaLuces) == 0) {
            swtLuces.setChecked(false);
            txtLuces.setText("Desactivado");
            Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtLuces.setChecked(true);
            txtLuces.setText("Activado" );
            Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


        String resultadoConsultaVentilador = new Ingreso().lectura_ingreso(51);

        if (Integer.parseInt(resultadoConsultaVentilador) == 0) {
            swtVentilador.setChecked(false);
            txtVentilador.setText("Desactivado");
            Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtVentilador.setChecked(true);
            txtVentilador.setText("Activado");
            Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


    }
    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }

    public void onclicVentilador(View view){
        if (view.getId() == R.id.switch_Ventilador5){
            if (swtVentilador.isChecked()){
                txtVentilador.setText("Activado");
                Cambio_base cambio=new Cambio_base(51,1);
                Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtVentilador.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(51,0);
                Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){
        if (view.getId() == R.id.switch_Luces5){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                Cambio_base cambio=new Cambio_base(52,1);
                Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(52,0);
                Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
