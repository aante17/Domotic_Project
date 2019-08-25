package com.example.domotic_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Comedor extends AppCompatActivity {
    //Inicializacion de variables TextView, Switch, DatabaseRefence, Cambio_base, TTSManager
    TextView txtLuces;
    Switch swtLuces;
    TextView txtVentilador;
    Switch swtVentilador;
    private DatabaseReference housetic;
    TTSManager ttsManager=null;
    Cambio_base fire = new Cambio_base();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor);
        txtLuces = (TextView) findViewById(R.id.textView_Luces5); //llamado del TextView del activity
        txtVentilador = (TextView) findViewById(R.id.textView_Ventilador5);
        swtVentilador= (Switch) findViewById(R.id.switch_Ventilador5); //llamado del switch del activity
        swtLuces = (Switch) findViewById(R.id.switch_Luces5);
        ttsManager=new TTSManager(); //creacion del objeto que ejecutara la voz de la aplicacion
        ttsManager.init(this);

        final String cuarto = "comedor"; //variables que se utilizara para ejecutar en la base de datos
        final String dispositivo1 = "luces";
        final String dispositivo2 = "ventilador";
        housetic = FirebaseDatabase.getInstance().getReference(); //ingreso a la base de datos
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() {  //busqueada por padre e hijo del diapositivo
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaLuces= dataSnapshot.child(dispositivo1).getValue().toString();
                    if (resultadoConsultaLuces.equalsIgnoreCase("OFF")) {
                        swtLuces.setChecked(false);
                        txtLuces.setText("Desactivado");
                        Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtLuces.setChecked(true);
                        txtLuces.setText("Activado");
                        Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() { //busqueada por padre e hijo del diapositivo
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaVentilador= dataSnapshot.child(dispositivo2).getValue().toString();
                    if (resultadoConsultaVentilador.equalsIgnoreCase("OFF")) {
                        swtVentilador.setChecked(false);
                        txtVentilador.setText("Desactivado");
                        Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtVentilador.setChecked(true);
                        txtVentilador.setText("Activado");
                        Toast.makeText(Comedor.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    public void estaciones(View v) { //implementacion de funcion estaciones, redirrecionamiento del cuarto a estaciones
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }

    public void onclicVentilador(View view){ //implementacion de funcion onClic para el ventilador

        if (view.getId() == R.id.switch_Ventilador5){
            if (swtVentilador.isChecked()){
                txtVentilador.setText("Activado");

                fire.FireCambioBase("comedor","ventilador","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Ventilador encendido");

                Toast.makeText(Comedor.this,"Ventilador encendido", Toast.LENGTH_LONG).show();
            }else{
                txtVentilador.setText("Desactivado");

                fire.FireCambioBase("comedor","ventilador","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                ttsManager.initQueue("Ventilador apagado");
                Toast.makeText(Comedor.this,"Ventilador apagado", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){

        if (view.getId() == R.id.switch_Luces5){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");

                fire.FireCambioBase("comedor","luces","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Luces encendidas"); //Ejecucion de voz del cambio de estado
                Toast.makeText(Comedor.this,"Luces encendidas", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");

                fire.FireCambioBase("comedor","luces","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                ttsManager.initQueue("Luces apagadas"); //Ejecucion de voz del cambio de estado
                Toast.makeText(Comedor.this,"Luces apagadas", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void getInputSpeech(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent, 10);
        } else {
            mensaje("Tu dispositivo no reconoce entrada de voz");
        }
    }

    public void mensaje(String text){
        Toast.makeText(Comedor.this,text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String comando = result.get(0);
                    if(comando.equalsIgnoreCase("Encender luces")){// validacion de comando de voz
                        fire.FireCambioBase("comedor","luces","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Luces encendidas"); //Ejecucion de voz del cambio de estado
                        mensaje("Luces encendidas");
                    } else if( comando.equalsIgnoreCase("Apagar luces")){// validacion de comando de voz
                        fire.FireCambioBase("comedor","luces","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Luces apagadas"); //Ejecucion de voz del cambio de estado
                        mensaje("Luces apagadas");
                    }else if(comando.equalsIgnoreCase("Encender ventilador")){// validacion de comando de voz
                        fire.FireCambioBase("comedor","ventilador","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Ventilador encendido"); //Ejecucion de voz del cambio de estado
                        mensaje("Ventilador encendido");
                    } else if( comando.equalsIgnoreCase("Apagar ventilador")){// validacion de comando de voz
                        fire.FireCambioBase("comedor","ventilador","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Ventilador apagado"); //Ejecucion de voz del cambio de estado
                        mensaje("Ventilador apagado");
                    }
                }
                break;
        }
    }
}
