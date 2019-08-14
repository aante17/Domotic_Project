package com.example.domotic_project;

import java.util.concurrent.ExecutionException;

public class Ingreso {
    private String serverIP = "remotemysql.com";
    private String port = "3306";
    private String userMySQL = "OoQlolIILm";
    private String pwdMySQL = "Z5JzvRroWj";
    private String database = "OoQlolIILm";
    private String[] datosConexion = null;

    public String lectura_ingreso(Integer dispositvo) {
        String[] resultadoSQL = null;
        {


            datosConexion = new String[]{
                    serverIP,
                    port,
                    database,
                    userMySQL,
                    pwdMySQL,
                    "",
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
                datosConexion[5] = "SELECT * FROM Dispositivo where idDispositivo=" + dispositvo + ";";
                resultadoSQL = new AsyncQuery().execute(datosConexion).get();


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String resultadoConsulta = resultadoSQL[0].split(",")[3];
        return resultadoConsulta;
    }

}
