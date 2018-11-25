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

    /**
     * SE DECLARAN CADA UNO DE LOS ELEMENTOS A UTILIZAR DENTRO DE ESTE ACTIVITY DE ACUERDO A SU TIPO
     * **/
    Animation blink;
    Button AdminIngresar;
    ImageView Adminpicture;
    ConectionDB conectionDB= new ConectionDB();
    EditText AdNick,AdNombre,Adpaterno,Admaterno,AdPassword;
    TextInputLayout TILnick,TILnombre,TILpaterno,TILmaterno,TILpassword;

    /**
     * CREACIÓN DE EXPRESIONES REGULARES PARA VALIDACIÓN DE CAJAS DE TEXTO DE PANTALLA : 'Login'
     * **/
    Pattern numericos=Pattern.compile("[0-9]+");
    Pattern letras=Pattern.compile("^[0-9a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$");


    /**
     * DECLARACION DE VARIABLES GLOBALES LAS CUALES PERMITIRAN VALIDAR CAJAS DE TEXTO 'SÍ ESTANN LLENAS O VACIAS'
     * **/
    public boolean bnickNameAdmin=false,bnombreAdmin=false,bpaternoAdmin=false,bmaternoAdmin=false,bpasswordAdmin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        /**
         *ASIGNACION DE LAS VARIABLES CREADAS PREVIAMENTE Y REFERENCIA A ARCHIVOS XML DONDE ESTAN LOS id DE CADA ELEMENTO UTILIZADO
         * CON FUNCIONALIDAD.
         * **/
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



        /**
         * SE CREA UN EVENTO DE TIPO 'setOnClickListener' EL CUAL SE ENCARGA DE VERIFICAR SI EL BOTON ES PRECIONADO.
         * **/
        AdminIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * VERIFICACION DE CONEXION A UNA RED
                 * **/
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                /**
                 * SÍ EXISTE ALGUNA CONEXIÓN EN EL DISPOSITIVO PROCEDERA CON LA VALIDACIÓN DE CAMPOS
                 * **/
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

                    /**
                     * INSTACIACION DE LA CLASE ConectionDB PARA ACCEDER AL METODO 'checkInRecordsAdmin(snick)'.
                     * **/
                    conectionDB.checkInRecordsAdmin(snick);

                    /**
                     * VERIFICACION DE USUARIOS CON MISMA CLAVE PRIMARIA
                     * **/
                    if (conectionDB.confirmationNick) {
                        /**
                         * SI EL NICK YA ESTA ASIGNADO RESIVIRÁ UN MENSAJE '¡YA EXISTE ESE NICK!'
                         * **/
                        Toast.makeText(getApplicationContext(), "¡YA EXISTE ESE NICK!", Toast.LENGTH_LONG).show();
                        conectionDB.setConfirmationNick(false);
                    } else {
                        /**
                         * SI EL NICK NO ESTA ASIGNADO SE INSERTARAN LOS DATOS Y UN MENSAJE 'DATOS INSERTADOS CORRECTAMENTE'
                         * **/
                        String nicksin, nombrein, paternoin, maternoin, passwordin;
                        nicksin = AdNick.getText().toString();
                        nombrein = AdNombre.getText().toString();
                        paternoin = Adpaterno.getText().toString();
                        maternoin = Admaterno.getText().toString();
                        passwordin = AdPassword.getText().toString();
                        conectionDB.insertAdmin(nicksin, nombrein, paternoin, maternoin, passwordin);

                        /**
                         * SE VALIDA SI LOS DATOS FUERON AGREGADOS A LA BASE DE DATOS Y POSTERIOR MENTE SE MUESTRA UN MENSAJE
                         * 'DATOS INSERTADOS CORRECTAMENTE' EN CASO CONTRARIO 'DATOS NO INSERTADOS'
                         * **/
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

                    /**
                     * SI LOS CAMPOS ESTAN VACIOS SE MOSTRARÁ UN MENSAJE 'CAMPOS VACIOS'
                     * **/
                    Toast.makeText(getApplicationContext(), "CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                }


            }
            else {
                    Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }
            });


    }

    /**
     * METODO UTILIZADO PARA VALIDAR CAJAS DE TEXTO
     * **/
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
