package com.example.tr.appsql;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.github.barteksc.pdfviewer.PDFView;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;

public class LectorPdfGeneral extends AppCompatActivity {

    /**
     * CREACION DE VARIABLE A INICIALIZACION DE LAS MISMAS
     * */
    private File file;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LA VIASUALIZACION DEL PDF SERÁ REALIZADA EN PANTALLA COMPLETA
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lector_pdf_general);

        /**
         * ESPESIFICA LAS CARACTERISTICAS QUE TENDRA EL LECTO DEL PDF
         * */
        pdfView= findViewById(R.id.viewerPDF);
        permisoRead();
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            file= new File(bundle.getString("path"));
        }
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }

    /**
     * VERIFICACION DE PERMISOS OTORGADOS POR EL USUARIO Y COMPROBACION DE ACCESO A LECTURA INTERNA Y EXTENA
     * DEL DISPOSITIVO
     * **/
    private void permisoRead(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,},1000);
        }
    }
}
