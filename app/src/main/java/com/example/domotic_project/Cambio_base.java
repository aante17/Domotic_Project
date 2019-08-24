package com.example.domotic_project;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

public class Cambio_base {


    static DatabaseReference housetic;

    public Cambio_base() {

    }


    public static void FireCambioBase(String cuarto,String dispositvo,String estado){
        //Cambio_base.ambio_baseC();º
        housetic = FirebaseDatabase.getInstance().getReference();
        housetic.child(cuarto).child(dispositvo).setValue(estado);
    }
}

