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

public class Dormitorio extends AppCompatActivity {
    //Inicializacion de variables TextView, Switch, DatabaseRefence, Cambio_base, TTSManager
    TextView txtLuces;
    Switch swtLuces;
    TextView txtPersianas;
    Switch swtPersinas;
    private DatabaseReference housetic;
    TTSManager ttsManager=null;
    Cambio_base fire = new Cambio_base();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ttsManager=new TTSManager(); //creacion del objeto que ejecutara la voz de la aplicacion
        ttsManager.init(this);
        setContentView(R.layout.activity_dormitorio);
        txtLuces = (TextView) findViewById(R.id.textView_Luces2); //llamado del TextView del activity
        txtPersianas = (TextView) findViewById(R.id.textView_Persianas2);
        swtPersinas = (Switch) findViewById(R.id.switch_Persianas2); //llamado del switch del activity
        swtLuces = (Switch) findViewById(R.id.switch_Luces2);

        String cuarto = "dormitorio"; //variables que se utilizara para ejecutar en la base de datos
        final String dispositivo1 = "luces";
        final String dispositivo2 = "persianas";
        housetic = FirebaseDatabase.getInstance().getReference(); //ingreso a la base de datos
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() {  //busqueada por padre e hijo del diapositivo
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaLuces= dataSnapshot.child(dispositivo1).getValue().toString();
                    if (resultadoConsultaLuces.equalsIgnoreCase("OFF")) {
                        swtLuces.setChecked(false);
                        txtLuces.setText("Desactivado");
                        mensaje("Conexión Establecida");
                    } else {
                        swtLuces.setChecked(true);
                        txtLuces.setText("Activado");
                        mensaje("Conexión Establecida");
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
                    String resultadoConsultaPersianas= dataSnapshot.child(dispositivo2).getValue().toString();
                    if (resultadoConsultaPersianas.equalsIgnoreCase("OFF")) {
                        swtPersinas.setChecked(false);
                        txtPersianas.setText("Desactivado");
                        mensaje("Conexión Establecida");
                    } else {
                        swtPersinas.setChecked(true);
                        txtPersianas.setText("Activado" );
                        mensaje("Conexión Establecida");
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

    public void onclicPersianas(View view){ //implementacion de funcion onClic para las persianas

        if (view.getId() == R.id.switch_Persianas2){
            if (swtPersinas.isChecked()){
                txtPersianas.setText("Activado");
                fire.FireCambioBase("dormitorio","persianas","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Persianas elevadas"); //Ejecucion de voz del cambio de estado
                mensaje("Persianas elevadas");
            }else{
                txtPersianas.setText("Desactivado");
                fire.FireCambioBase("dormitorio","persianas","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                ttsManager.initQueue("Persianas desplegadas"); //Ejecucion de voz del cambio de estado
                mensaje("Persianas desplegadas");
            }
        }
    }

    public void onclicLuces(View view){

        if (view.getId() == R.id.switch_Luces2){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");
                fire.FireCambioBase("dormitorio","luces","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Luces encendidas"); //Ejecucion de voz del cambio de estado
                mensaje("Luces encendidas");
            }else{
                txtLuces.setText("Desactivado");
                fire.FireCambioBase("dormitorio","luces","OFF"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                ttsManager.initQueue("Luces apagadas"); //Ejecucion de voz del cambio de estado
                mensaje("Luces apagadas");
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
        Toast.makeText(Dormitorio.this,text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String comando = result.get(0);
                    if(comando.equalsIgnoreCase("Encender luces")){
                        fire.FireCambioBase("dormitorio","luces","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Luces encendidas"); //Ejecucion de voz del cambio de estado
                        mensaje("Luces encendidas");
                    } else if( comando.equalsIgnoreCase("Apagar luces")){
                        fire.FireCambioBase("dormitorio","luces","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Luces apagadas"); //Ejecucion de voz del cambio de estado
                        mensaje("Luces apagadas");
                    }else if(comando.equalsIgnoreCase("Elevar persianas")){
                        fire.FireCambioBase("dormitorio","persianas","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Persianas elevadas"); //Ejecucion de voz del cambio de estado
                        mensaje("Persianas elevadas");
                    } else if( comando.equalsIgnoreCase("Bajar persianas")){
                        fire.FireCambioBase("dormitorio","persianas","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Persianas desplegadas"); //Ejecucion de voz del cambio de estado
                        mensaje("Persianas desplegadas");
                    }
                }
                break;
        }
    }

}
