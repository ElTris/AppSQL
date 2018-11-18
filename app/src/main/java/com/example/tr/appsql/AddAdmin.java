package com.example.tr.appsql;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddAdmin extends AppCompatActivity {
    Animation blink;
    Button AdminIngresar;
    ImageView Adminpicture;
    ConectionDB conectionDB= new ConectionDB();
    Pattern numericos=Pattern.compile("[0-9]+");
    Pattern letras=Pattern.compile("^[0-9a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$");
    EditText AdNick,AdNombre,Adpaterno,Admaterno,AdPassword;
    TextInputLayout TILnick,TILnombre,TILpaterno,TILmaterno,TILpassword;
    public boolean bnickNameAdmin=false,bnombreAdmin=false,bpaternoAdmin=false,bmaternoAdmin=false,bpasswordAdmin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        AdminIngresar=findViewById(R.id.btnIngresarAdmin);
        blink= AnimationUtils.loadAnimation(this,R.anim.blink);
        Adminpicture=findViewById(R.id.picadimn);
        AdNick=findViewById(R.id.edtnickAdmin);
        AdNombre=findViewById(R.id.edtnombreAdmin);
        Adpaterno=findViewById(R.id.edtPaternoAdmin);
        Admaterno=findViewById(R.id.edtMaternoAdmin);
        AdPassword=findViewById(R.id.edtPasswordAdmin);
        TILnick=findViewById(R.id.inputnicknameAdmin);
        TILnombre=findViewById(R.id.inputnombreAdmin);
        TILpaterno=findViewById(R.id.inputPaternoAdmin);
        TILmaterno=findViewById(R.id.inputMaternoAdmin);
        TILpassword=findViewById(R.id.inputPasswordAdmin);
        Adminpicture.startAnimation(blink);


        AdminIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                validacionEditText();
                /**
                 *
                 * si todas las condiciones de validacion se cumplen se preocede a hacer la revicion de nicks en
                 * la base de datos para evitar errores de duplicacion de claves primarias dentro de la tabla admin
                 *
                 * */
                if (bnickNameAdmin && bnombreAdmin && bpaternoAdmin && bmaternoAdmin && bpasswordAdmin) {
                    TILpassword.setError("");
                    String snick = AdNick.getText().toString();
                    conectionDB.checkInRecordsAdmin(snick);
                    if (conectionDB.confirmationNick) {
                        Toast.makeText(getApplicationContext(), "¡YA EXISTE ESE NICK!", Toast.LENGTH_LONG).show();
                        conectionDB.setConfirmationNick(false);
                    } else {
                        String nicksin, nombrein, paternoin, maternoin, passwordin;
                        nicksin = AdNick.getText().toString();
                        nombrein = AdNombre.getText().toString();
                        paternoin = Adpaterno.getText().toString();
                        maternoin = Admaterno.getText().toString();
                        passwordin = AdPassword.getText().toString();
                        conectionDB.insertAdmin(nicksin, nombrein, paternoin, maternoin, passwordin);
                        if (conectionDB.confirmationInsert) {
                            AdNick.setText("");
                            AdNombre.setText("");
                            Adpaterno.setText("");
                            Admaterno.setText("");
                            AdPassword.setText("");
                            Toast.makeText(getApplicationContext(), "DATOS INSERTADOS CORRECTAMENTE", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "DATOS NO INSERTADOS", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                }


            }
            else {
                    Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }
            });


    }

    public void validacionEditText(){
        if (numericos.matcher(AdNick.getText().toString()).matches()==false){
            bnickNameAdmin=false;
        }
        else {
            bnickNameAdmin=true;
        }

        if (letras.matcher(AdNombre.getText().toString()).matches()==false){
            bnombreAdmin=false;
        }
        else {
            bnombreAdmin=true;
        }

        if (letras.matcher(Adpaterno.getText().toString()).matches()==false){
            bpaternoAdmin=false;
        }
        else {
            bpaternoAdmin=true;
        }

        if (letras.matcher(Admaterno.getText().toString()).matches()==false){
            bmaternoAdmin=false;
        }
        else {
            bmaternoAdmin=true;
        }

        if (letras.matcher(AdPassword.getText().toString()).matches()==false){
            TILpassword.setError("NO PERMITIDO");
            bpasswordAdmin=false;
        }
        else {
            bpasswordAdmin=true;
        }

    }

}
