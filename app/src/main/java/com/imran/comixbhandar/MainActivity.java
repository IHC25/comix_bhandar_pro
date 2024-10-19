package com.imran.comixbhandar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity {

    LinearLayout sovietTintinBtn, ascharjontuBtn, danpiteKhaduBtn, eksringaObhijanBtn, moiNiyeHoiChoiBtn, bappaRayBadamiChairBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign Variables
        sovietTintinBtn = findViewById(R.id.sovietTintinBtn);
        ascharjontuBtn = findViewById(R.id.ascharjontuBtn);
        danpiteKhaduBtn = findViewById(R.id.danpiteKhaduBtn);
        eksringaObhijanBtn = findViewById(R.id.eksringaObhijanBtn);
        moiNiyeHoiChoiBtn = findViewById(R.id.moiNiyeHoiChoiBtn);
        bappaRayBadamiChairBtn = findViewById(R.id.bappaRayBadamiChairBtn);


        //Comic Click Listener
        sovietTintinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "soviet_deshe_tintin.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        ascharjontuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "ascharjontu.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        danpiteKhaduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "danpite_khandu_samagra.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        eksringaObhijanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "eksringa_abhijaan.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        moiNiyeHoiChoiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "moi_niye_hoi_choi.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        bappaRayBadamiChairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "bappa_ray_badami_chair.pdf";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });


    }
}