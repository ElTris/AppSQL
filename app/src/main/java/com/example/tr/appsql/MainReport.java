package com.example.tr.appsql;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainReport extends AppCompatActivity {
    private Button reporte;
    private GeneradorPdf generadorPdf;
    ConectionDB conectionDB;
    private String[] header={"Matricula","Nombre","Apellido Paterno","Apellido Materno","Estado","Municipio","Localidad"};
    String fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report);

        reporte=findViewById(R.id.btnGenerarReporteGen);
        permisoWrite();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();

        fecha = dateFormat.format(date);
        /**
         * CUANDO EL BOTON ES PRECIONADO GENERA UN PDF LLAMANDO A LAS CLASES ConectionDB y GeneradorPdf
         * */
        reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Generando reporte...", Toast.LENGTH_SHORT).show();
                    conectionDB = new ConectionDB();
                    generadorPdf = new GeneradorPdf(getApplicationContext());
                    generadorPdf.openDocumento();
                    generadorPdf.addMetaData("REPORTE GENERAL", "REPORTE GENERAL BD DISTRIBUIDAS", "ISMAEL TRISTAN ROMERO");
                    generadorPdf.addTitles("REPORTE GENERAL DE ALUMNOS", "Bases de datos distribuidas", fecha);
                    generadorPdf.createTable(header, conectionDB.reportDataGeneral());
                    generadorPdf.closeDocument();
                    generadorPdf.ViewPdf();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * SOLICITANDO PERMISOS DE ESCRITURA AL DISPOSITIVO Y USUARIO
     * */
    private void permisoWrite(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},1000);
        }else {}
    }
}
