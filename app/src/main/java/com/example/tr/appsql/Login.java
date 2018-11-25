package com.example.tr.appsql;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {


    ImageView pictus;
    Button inside;
    Animation animation;
    TextInputLayout nombresNICK,passwordsLog;
    EditText NICKSNAMES,PASSWORDSE;
    boolean nicks=false,pass=false;
    ConectionDB conectionDB= new ConectionDB();
    CheckBox recordatorio;
    SharedPreferences savepreferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        pictus = findViewById(R.id.userphoto);
        pictus.startAnimation(animation);
        inside = findViewById(R.id.btniside);
        nombresNICK = findViewById(R.id.Dedt1);
        passwordsLog = findViewById(R.id.Dedt2);
        NICKSNAMES = findViewById(R.id.nick);
        PASSWORDSE = findViewById(R.id.passwords);
        recordatorio = findViewById(R.id.creedenciales);

        inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    Pattern patron1 = Pattern.compile("[0-9]+");
                    if (patron1.matcher(NICKSNAMES.getText().toString()).matches() == false) {
                        nombresNICK.setError("NICK NAME INVALIDO");
                        nicks = false;
                    } else {
                        nicks = true;
                    }
                    Pattern patron = Pattern.compile("^[0-9a-zA-ZáÁéÉíÍóÓúÚñÑüÜ]+$");
                    if (patron.matcher(PASSWORDSE.getText().toString()).matches() == false) {
                        passwordsLog.setError("PASSWORD INVALIDA");
                        pass = false;
                    } else {
                        pass = true;
                    }
                    /**
                     * Start if the booleans are true
                     *
                     * */
                    if (nicks & pass) {
                        String val1, val2;
                        val1 = NICKSNAMES.getText().toString();
                        val2 = PASSWORDSE.getText().toString();

                        conectionDB.queryAdminServers(val1, val2);

                        if (conectionDB.getAdmin()) {
                            Intent intent = new Intent(Login.this, MainOptions.class);
                            startActivity(intent);
                            finish();
                            if (recordatorio.isChecked()) {
                                savepreferencias = getSharedPreferences("preferencia1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = savepreferencias.edit();
                                editor.putBoolean("Check", true);
                                editor.putString("nicksnames", NICKSNAMES.getText().toString());
                                editor.putString("passwords", PASSWORDSE.getText().toString());
                                editor.commit();
                            } else {
                                savepreferencias = getSharedPreferences("preferencia1", MODE_PRIVATE);
                                SharedPreferences.Editor editor = savepreferencias.edit();
                                editor.putBoolean("Check", false);
                                editor.putString("nicksnames", "");
                                editor.putString("passwords", "");
                                editor.commit();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "ADMINISTRADOR O PASSWORD NO VALIDOS", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }
        });
        savepreferencias = getSharedPreferences("preferencia1", MODE_PRIVATE);
        boolean check = savepreferencias.getBoolean("Check", false);
        if (check == true) {
            NICKSNAMES.setText(savepreferencias.getString("nicksnames", ""));
            PASSWORDSE.setText(savepreferencias.getString("passwords", ""));
            recordatorio.setChecked(true);
        }

    }

}
