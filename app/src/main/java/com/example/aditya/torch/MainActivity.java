package com.example.aditya.torch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button create,open;
    EditText mtext,add,obj,edu,exp;
    public static final int STORAGE_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create=findViewById(R.id.create);
        open=findViewById(R.id.open);
        mtext=findViewById(R.id.name);
        add=findViewById(R.id.address);
        obj=findViewById(R.id.objective);
        edu=findViewById(R.id.education);
        exp=findViewById(R.id.experience);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                        String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,STORAGE_CODE);
                    }
                    else{
                        createPdf();
                    }
                }
                else {
                    createPdf();
                }
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf();
            }
        });
    }
    public void createPdf(){
        com.itextpdf.text.Document document=new com.itextpdf.text.Document();
        String FileName=mtext.getText().toString();
        String FilePath=Environment.getExternalStorageDirectory()+"/"+FileName+".pdf";
        Font font=new Font();
        font.setSize(20.0f);
        font.setColor(BaseColor.RED);
        try{
            PdfWriter.getInstance(document,new FileOutputStream(FilePath));
            document.open();
            String text=mtext.getText().toString();
            Paragraph para=new Paragraph();
            para.setFont(font);
            para.add(text);
            document.add(para);
            text="[ "+add.getText().toString()+" ]";
            document.add(new Paragraph(text));
            document.add(new Paragraph("OBJECTIVE : "));
            text=obj.getText().toString();
            document.add(new Paragraph(text));
            document.add(new Paragraph("Education :"));
            text=edu.getText().toString();
            document.add(new Paragraph(text));
            document.add(new Paragraph("Experience :"));
            text=exp.getText().toString();
            document.add(new Paragraph(text));
            document.close();
            Toast.makeText(this,FileName+".pdf\nis saved to\n"+FilePath,Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void openPdf(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    createPdf();
                }
                else {
                    Toast.makeText(this,"Permission Not Granted...!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
