package com.example.tr.appsql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainOptions extends AppCompatActivity {
    Animation slides;
    LinearLayout btnAdmin,btnAlumno,btnReporte,btnCustomReport;
    TextView menus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);
        /**
        * DECLARACION DE ELEMENTOS USADOS EN LA INTERFACE
        * */
        menus=findViewById(R.id.menutext);
        slides= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        btnAdmin= findViewById(R.id.insertAdmin);
        btnAlumno=findViewById(R.id.insertAlumnos);
        btnReporte=findViewById(R.id.report);
        /**
        * INICIO DE ANIMACIONES A LOS ELEMENTOS ABAJO MOSTRADOS
        * */
        menus.startAnimation(slides);
        btnAdmin.startAnimation(slides);
        btnAlumno.startAnimation(slides);
        btnReporte.startAnimation(slides);

        /**
        * CUANDO EL BOTON ES PRECIONADO INICIA UNA NUEVA ACTIVIDAD 'AddAdmin'
        * */
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,AddAdmin.class);
                startActivity(intent);
            }
        });

        /**
         * CUANDO EL BOTON ES PRECIONADO INICIA UNA NUEVA ACTIVIDAD 'AddAlumno'
         * */
        btnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,AddAlumno.class);
                startActivity(intent);

            }
        });

        /**
         * CUANDO EL BOTON ES PRECIONADO INICIA UNA NUEVA ACTIVIDAD 'MainReport'
         * */
        btnReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,MainReport.class);
                startActivity(intent);
            }
        });

    }
}
