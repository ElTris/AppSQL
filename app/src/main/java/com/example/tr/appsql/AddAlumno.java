package com.example.tr.appsql;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddAlumno extends AppCompatActivity {
    Animation blink;
    ImageView picture_one;
    Pattern numericos=Pattern.compile("[0-9]+");
    Pattern letras=Pattern.compile("^[0-9a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$");
    ConectionDB conectionDB_one= new ConectionDB();
    EditText matriculaedt,nombreedt,paternoedt,maternoedt;
    Spinner Estadossp;
    Spinner Municipiossp;
    Spinner Localidadessp;
    TextView estadostext,municipiostext,localidadestext;
    Button agregarAlumno;
    public String EdoString,MunString,LocString;
    boolean bmatriculaAlumno=false,bnombreAlumno=false,bpaternoALumno=false,bmaternoAlumno=false,bestados=false,bmunicipios=false,blocalidades=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumno);

        blink = AnimationUtils.loadAnimation(this, R.anim.blink);
        picture_one = findViewById(R.id.pic_alumno);
        matriculaedt = findViewById(R.id.edtmatriculaAlumno);
        nombreedt = findViewById(R.id.edtnombreAlumno);
        paternoedt = findViewById(R.id.edtpaternoAlumno);
        maternoedt = findViewById(R.id.edtmaternoAlumno);
        Estadossp = findViewById(R.id.spEstados);
        Municipiossp = findViewById(R.id.spMunicipio);
        Localidadessp = findViewById(R.id.spLocalidades);
        estadostext = findViewById(R.id.textedos);
        municipiostext = findViewById(R.id.textmunicipio);
        localidadestext = findViewById(R.id.textlocalidades);
        agregarAlumno = findViewById(R.id.btnAgregarAlumno);

        picture_one.startAnimation(blink);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        getEstadosSpinner();

        Estadossp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EdoString = Estadossp.getSelectedItem().toString();
                conectionDB_one.setEstadosSpinner(EdoString);
                getsMunicipiosSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Municipiossp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MunString = Municipiossp.getSelectedItem().toString();
                conectionDB_one.setMunicipiosSpinner(MunString);
                getLocalidadesSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Localidadessp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LocString = Localidadessp.getSelectedItem().toString();
                conectionDB_one.setLocalidadesSpinner(LocString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }else
        {
            Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
        }

        agregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    getEstadosSpinner();

                if (numericos.matcher(matriculaedt.getText().toString()).matches() == false) {
                    bmatriculaAlumno = false;
                } else {
                    bmatriculaAlumno = true;
                }
                if (letras.matcher(nombreedt.getText().toString()).matches() == false) {
                    bnombreAlumno = false;
                } else {
                    bnombreAlumno = true;
                }
                if (letras.matcher(paternoedt.getText().toString()).matches() == false) {
                    bpaternoALumno = false;
                } else {
                    bpaternoALumno = true;
                }
                if (letras.matcher(maternoedt.getText().toString()).matches() == false) {
                    bmaternoAlumno = false;
                } else {
                    bmaternoAlumno = true;
                }
                if (bmatriculaAlumno && bnombreAlumno && bpaternoALumno && bmaternoAlumno) {
                    String MatriculaID = matriculaedt.getText().toString();
                    conectionDB_one.checkInRecordsAlumnos(MatriculaID);
                    if (conectionDB_one.confirmationAlumno) {
                        Toast.makeText(getApplicationContext(), "¡YA EXISTE ESE NICK!", Toast.LENGTH_LONG).show();
                        conectionDB_one.setConfirmationAlumno(false);
                    } else {
                        String mat, nom, pat, mater, edo, mun, loc;
                        mat = matriculaedt.getText().toString();
                        nom = nombreedt.getText().toString();
                        pat = paternoedt.getText().toString();
                        mater = maternoedt.getText().toString();
                        edo = conectionDB_one.setClaveEstadoQuery();
                        mun = conectionDB_one.setClaveMunicipioQuery();
                        loc = conectionDB_one.setClaveLocalidadesQuery();
                        conectionDB_one.insertarAlumno(mat, nom, pat, mater, edo, mun, loc);
                        if (conectionDB_one.confirmationInsert) {
                            matriculaedt.setText("");
                            nombreedt.setText("");
                            paternoedt.setText("");
                            maternoedt.setText("");
                            getEstadosSpinner();
                            getsMunicipiosSpinner();
                            getLocalidadesSpinner();
                            Toast.makeText(getApplicationContext(), "DATOS INSERTADOS CORRECTAMENTE", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "DATOS NO INSERTADOS", Toast.LENGTH_LONG).show();
                        }


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "¡LO SENTIMOS, LLENE LOS CAMPOS!", Toast.LENGTH_SHORT).show();
                }


            }
                else {
                    Toast.makeText(getApplicationContext(),"Verifique conexión a internet",Toast.LENGTH_SHORT).show();
                }


        }
        });
    }

    public void getEstadosSpinner(){
        ArrayAdapter<CharSequence> adapterEstados= new ArrayAdapter(this,R.layout.item_special,conectionDB_one.getDataEstados());
        Estadossp.setAdapter(adapterEstados);
    }
    public void getsMunicipiosSpinner(){
        /***
         * METODO ENCARGADO DE LLENAR EL SPINNER PERTENECIENTE A MUNICIPIOS UNA VEZ QUE SEA SELECCIONADO UN ITEM DE SPINNER
         * */
        ArrayAdapter<CharSequence> adapterMunicipios= new ArrayAdapter(this,R.layout.item_special,conectionDB_one.getDataMunicipios());
        Municipiossp.setAdapter(adapterMunicipios);
    }
    public void getLocalidadesSpinner(){
        ArrayAdapter<CharSequence> adapterLocalidades= new ArrayAdapter(this,R.layout.item_special,conectionDB_one.getDataLocalidades());
        Localidadessp.setAdapter(adapterLocalidades);
    }
}
