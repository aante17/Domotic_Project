package com.example.domotic_project;

import android.content.Context;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ingreso {

    private DatabaseReference housetic;//inicializacion de DatabaseRefenrence


    public void lectura_ingreso(final String cuarto, final String dispositivo, final TextView txt, final Switch swt) {//ingreso de funcion que hara lectura de base de datos

        housetic = FirebaseDatabase.getInstance().getReference(); //ingreso a base de datos
        housetic.child(cuarto);
        housetic.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {//buscar a traves del padre e hijo el estado del dispositivo
                if (dataSnapshot.exists()) {
                    String resultadoConsulta = dataSnapshot.child(dispositivo).getValue().toString();
                    if (resultadoConsulta.equalsIgnoreCase("OFF")) {
                        swt.setChecked(false);
                        txt.setText("Desactivado");
                    } else {
                        swt.setChecked(true);
                        txt.setText("Activado");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
