package com.example.tr.appsql;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * DECLARACION DE VARIABLE Y ASIGNACION DE VALORES
     * */

    private int timer=1500;
    ImageView picture;
    TextView materia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        /***
         *MUESTRA UN SPLASH SCREEN CON UNA DURACION DE 1.5  SEGUNDOS
         * **/

        picture= findViewById(R.id.presentacion);
        materia= findViewById(R.id.info);
        Animation blink= AnimationUtils.loadAnimation(this,R.anim.blink);
        picture.startAnimation(blink);
        materia.startAnimation(blink);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login= new Intent(MainActivity.this,Login.class);
                startActivity(login);
                finish();
            }
        },timer);
    }
}
