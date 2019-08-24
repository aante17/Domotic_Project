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

public class Garaje extends AppCompatActivity {
    TextView txtLuces;
    Switch swtLuces;
    TextView txtPuerta;
    Switch swtPuerta;
    private DatabaseReference housetic;
    TTSManager ttsManager=null;
    Cambio_base fire = new Cambio_base();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garaje);

        txtLuces = (TextView) findViewById(R.id.textView_Luces3);
        txtPuerta = (TextView) findViewById(R.id.textView_Puerta3);
        swtPuerta = (Switch) findViewById(R.id.switch_Puerta3);
        swtLuces = (Switch) findViewById(R.id.switch_Luces3);
        ttsManager=new TTSManager();
        ttsManager.init(this);

        String cuarto = "garage";
        final String dispositivo1 = "luces";
        final String dispositivo2 = "puertas";
        housetic = FirebaseDatabase.getInstance().getReference();
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaLuces= dataSnapshot.child(dispositivo1).getValue().toString();
                    if (resultadoConsultaLuces.equalsIgnoreCase("OFF")) {
                        swtLuces.setChecked(false);
                        txtLuces.setText("Desactivado");
                        Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtLuces.setChecked(true);
                        txtLuces.setText("Activado" );
                        Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        housetic.child(cuarto).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String resultadoConsultaPuerta= dataSnapshot.child(dispositivo2).getValue().toString();
                    if (resultadoConsultaPuerta.equalsIgnoreCase("OFF")) {
                        swtPuerta.setChecked(false);
                        txtPuerta.setText("Desactivado");
                        Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    } else {
                        swtPuerta.setChecked(true);
                        txtPuerta.setText("Activado");
                        Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void estaciones(View v) {
        Intent i = new Intent(this, Estaciones.class );
        startActivity(i);

    }
    public void onclicPuerta(View view){

        if (view.getId() == R.id.switch_Puerta3){
            if (swtPuerta.isChecked()){
                txtPuerta.setText("Activado");

                fire.FireCambioBase("garage","puertas","ON");
                ttsManager.initQueue("Puertas abiertas");
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtPuerta.setText("Desactivado");

                fire.FireCambioBase("garage","puertas","OFF");
                ttsManager.initQueue("Puertas cerradas");
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onclicLuces(View view){

        if (view.getId() == R.id.switch_Luces3){
            if (swtLuces.isChecked()){
                txtLuces.setText("Activado");

                fire.FireCambioBase("garage","luces","ON");
                ttsManager.initQueue("Luces encendidas");
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
            }else{
                txtLuces.setText("Desactivado");

                fire.FireCambioBase("garage","luces","OFF");
                ttsManager.initQueue("Luces apagadas");
                Toast.makeText(Garaje.this,"Conexión Establecida", Toast.LENGTH_LONG).show();
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
        Toast.makeText(Garaje.this,text, Toast.LENGTH_LONG).show();
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
                        fire.FireCambioBase("garage","luces","ON");
                        ttsManager.initQueue("Luces encendidas");
                        mensaje("Luces encendidas");
                    } else if( comando.equalsIgnoreCase("Apagar luces")){
                        fire.FireCambioBase("garage","luces","OFF");
                        ttsManager.initQueue("Luces apagadas");
                        mensaje("Luces apagadas");
                    }else if(comando.equalsIgnoreCase("Abrir puertas")){
                        fire.FireCambioBase("garage","puertas","ON");
                        ttsManager.initQueue("Puertas abiertas");
                        mensaje("Puertas abiertas");
                    } else if( comando.equalsIgnoreCase("Cerrar puertas")){
                        fire.FireCambioBase("garage","puertas","OFF");
                        ttsManager.initQueue("Puertas cerradas");
                        mensaje("Puertas cerradas");
                    }
                }
                break;
        }
    }
}
