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

public class Jardin extends AppCompatActivity {
    //Inicializacion de variables TextView, Switch, DatabaseRefence, Cambio_base, TTSManager
    TextView txtLuces;
    Switch swtLuces;
    TextView txtAlarma;
    Switch swtAlarma;
    private DatabaseReference housetic;
    TTSManager ttsManager=null;
    Cambio_base fire = new Cambio_base();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jardin);
        txtLuces = (TextView) findViewById(R.id.textView_Luces4); //llamado del TextView del activity
        txtAlarma = (TextView) findViewById(R.id.textView_Alarma4);
        swtAlarma= (Switch) findViewById(R.id.switch_Alarma4); //llamado del switch del activity
        swtLuces = (Switch) findViewById(R.id.switch_Luces4);
        ttsManager=new TTSManager(); //creacion del objeto que ejecutara la voz de la aplicacion
        ttsManager.init(this);

        String cuarto = "jardin"; //variables que se utilizara para ejecutar en la base de datos
        final String dispositivo1 = "luces";
        final String dispositivo2 = "alarma";
        housetic = FirebaseDatabase.getInstance().getReference();//ingreso a la base de datos
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() {  //busqueada por padre e hijo del diapositivo
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaLuces= dataSnapshot.child(dispositivo1).getValue().toString();
                    if (resultadoConsultaLuces.equalsIgnoreCase("OFF")) {
                        swtLuces.setChecked(false);
                        txtLuces.setText("Desactivado");
                        Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtLuces.setChecked(true);
                        txtLuces.setText("Activado" );
                        Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
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
                    String resultadoConsultaAlarma= dataSnapshot.child(dispositivo2).getValue().toString();
                    if (resultadoConsultaAlarma.equalsIgnoreCase("OFF")) {
                        swtAlarma.setChecked(false);
                        txtAlarma.setText("Desactivado");
                        Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtAlarma.setChecked(true);
                        txtAlarma.setText("Activado");
                        Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
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
    public void onclicAlarma(View view){ //implementacion de funcion onClic para la alarma

        if (view.getId() == R.id.switch_Alarma4){
            if (swtAlarma.isChecked()){
                txtAlarma.setText("Activado");

                fire.FireCambioBase("jardin","alarma","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Alarma activada");//Ejecucion de voz del cambio de estado
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtAlarma.setText("Desactivado");
                ttsManager.initQueue("Alarma desactivada");//Ejecucion de voz del cambio de estado
                fire.FireCambioBase("jardin","alarma","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){

        if (view.getId() == R.id.switch_Luces4){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");

                fire.FireCambioBase("jardin","luces","ON"); //implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                ttsManager.initQueue("Luces encendidas"); //Ejecucion de voz del cambio de estado
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");

                fire.FireCambioBase("jardin","luces","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                ttsManager.initQueue("Luces apagadas");//Ejecucion de voz del cambio de estado
                Toast.makeText(Jardin.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
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
        Toast.makeText(Jardin.this,text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String comando = result.get(0);
                    if(comando.equalsIgnoreCase("Encender luces")){//Ejecucion de voz del cambio de estado
                        fire.FireCambioBase("jardin","luces","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Luces encendidas");// validacion de comando de voz
                        mensaje("Luces encendidas");
                    } else if( comando.equalsIgnoreCase("Apagar luces")){//Ejecucion de voz del cambio de estado
                        fire.FireCambioBase("jardin","luces","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Luces apagadas");// validacion de comando de voz
                        mensaje("Luces apagadas");
                    }else if(comando.equalsIgnoreCase("Activar alarma")){//Ejecucion de voz del cambio de estado
                        fire.FireCambioBase("jardin","alarma","ON");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a ON
                        ttsManager.initQueue("Alarma activada");// validacion de comando de voz
                        mensaje("Alarma activada");
                    } else if( comando.equalsIgnoreCase("Desactivar alarma")){//Ejecucion de voz del cambio de estado
                        fire.FireCambioBase("jardin","alarma","OFF");//implementacion del FireCambioBase, cambiara el estado del disposito en la base de datos a OFF
                        ttsManager.initQueue("Alarma desactivada");// validacion de comando de voz
                        mensaje("Alarma desactivada");
                    }
                }
                break;
        }
    }
}
