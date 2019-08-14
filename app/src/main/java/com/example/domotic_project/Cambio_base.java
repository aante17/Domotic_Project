package com.example.domotic_project;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Cambio_base {

    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "OoQlolIILm";
    private String pwdMySQL = "Z5JzvRroWj";
    private String database = "OoQlolIILm";
    private String[] datosConexion = null;


    public Cambio_base(Integer dispositivo,Integer num) {

                {
                    String[] resultadoSQL = null;
                    datosConexion = new String[]{
                            serverIP,
                            port,
                            database,
                            userMySQL,
                            pwdMySQL,
                            "UPDATE `Dispositivo` SET `State` = '"+num+"' WHERE `Dispositivo`.`idDispositivo` = "+dispositivo+";",
                    };
                    String driver = "com.mysql.jdbc.Driver";
                    try {
                        Class.forName(driver).newInstance();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        resultadoSQL = new AsyncQueryUpdate().execute(datosConexion).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


    }
}

