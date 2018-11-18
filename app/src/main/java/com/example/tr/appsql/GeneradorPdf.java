package com.example.tr.appsql;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GeneradorPdf {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font ftitle= new Font(Font.TIMES_ROMAN,22,Font.BOLD);
    private Font fsubtitle= new Font(Font.TIMES_ROMAN,18,Font.BOLD);
    private Font ftext= new Font(Font.TIMES_ROMAN,15,Font.BOLD);
    private Font fHighText= new Font(Font.TIMES_ROMAN,15,Font.BOLD);

    public GeneradorPdf(Context context) {
        this.context=context;
    }

    public void openDocumento(){
        createFile();
        try{
            document= new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();
        }
        catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    private void createFile(){
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"/REPORTES BD DISTRIBUIDAS");
        if (folder.exists()){
            folder.mkdirs();
            pdfFile= new File(folder,"REPORTE.pdf");
        }
        else {
            folder.mkdirs();
            pdfFile= new File(folder,"REPORTE.pdf");
        }
    }

    public void addMetaData(String title,String subject,String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title,String subTitle, String Date){
        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, ftitle));
            addChildP(new Paragraph(subTitle, fsubtitle));
            addChildP(new Paragraph("Generado: " + Date, fHighText));
            paragraph.setSpacingAfter(10);
            document.add(paragraph);
        }

        catch (Exception e){
            Log.e("addTitle",e.toString());
        }
    }

    public void closeDocument(){

        document.close();
    }

    public void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, ftext);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }
        catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> clients){
        try {
            paragraph= new Paragraph();
            paragraph.setFont(ftext);
            PdfPTable pdfPTable= new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setSpacingBefore(20);
            PdfPCell pdfPCell;
            int  indexC=0;
            while (indexC<header.length){
                pdfPCell= new PdfPCell(new Phrase(header[indexC++],fHighText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPCell.setBackgroundColor(harmony.java.awt.Color.GRAY);
                pdfPTable.addCell(pdfPCell);
            }
            for (int indexR=0; indexR<clients.size();indexR++){
                String[] row= clients.get(indexR);
                for (indexC=0; indexC<header.length;indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }
        catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }

    public void ViewPdf(){
        Intent intent= new Intent(context, LectorPdfGeneral.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
