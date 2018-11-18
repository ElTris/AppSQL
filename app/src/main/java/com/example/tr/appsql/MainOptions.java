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
        /*
        * declaration of elements used in the interface
        * */
        menus=findViewById(R.id.menutext);
        slides= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        btnAdmin= findViewById(R.id.insertAdmin);
        btnAlumno=findViewById(R.id.insertAlumnos);
        btnReporte=findViewById(R.id.report);
        /*
        * in the part below of us, we have some declarations of animations buttons
        * */
        menus.startAnimation(slides);
        btnAdmin.startAnimation(slides);
        btnAlumno.startAnimation(slides);
        btnReporte.startAnimation(slides);

        /*
        * some methods implemented in the interface for clicked objects (Buttons)
        * */
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,AddAdmin.class);
                startActivity(intent);
            }
        });

        btnAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,AddAlumno.class);
                startActivity(intent);

            }
        });

        btnReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainOptions.this,MainReport.class);
                startActivity(intent);
            }
        });

    }
}
