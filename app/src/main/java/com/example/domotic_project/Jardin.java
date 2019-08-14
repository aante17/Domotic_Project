package com.example.domotic_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Jardin extends AppCompatActivity {
    TextView txtLuces;
    Switch swtLuces;
    TextView txtAlarma;
    Switch swtAlarma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jardin);
        txtLuces = (TextView) findViewById(R.id.textView_Luces4);
        txtAlarma = (TextView) findViewById(R.id.textView_Alarma4);
        swtAlarma= (Switch) findViewById(R.id.switch_Alarma4);
        swtLuces = (Switch) findViewById(R.id.switch_Luces4);

        String resultadoConsultaLuces = new Ingreso().lectura_ingreso(42);

        if (Integer.parseInt(resultadoConsultaLuces) == 0) {
            swtLuces.setChecked(false);
            txtLuces.setText("Desactivado");
            Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtLuces.setChecked(true);
            txtLuces.setText("Activado" );
            Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


        String resultadoConsultaAlarma = new Ingreso().lectura_ingreso(41);

        if (Integer.parseInt(resultadoConsultaAlarma) == 0) {
            swtAlarma.setChecked(false);
            txtAlarma.setText("Desactivado");
            Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        } else {
            swtAlarma.setChecked(true);
            txtAlarma.setText("Activado");
            Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
        }


    }
    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }
    public void onclicAlarma(View view){
        if (view.getId() == R.id.switch_Alarma4){
            if (swtAlarma.isChecked()){
                txtAlarma.setText("Activado");
                Cambio_base cambio=new Cambio_base(41,1);
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtAlarma.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(41,0);
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){
        if (view.getId() == R.id.switch_Luces4){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                Cambio_base cambio=new Cambio_base(42,1);
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");
                Cambio_base cambio=new Cambio_base(42,0);
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
