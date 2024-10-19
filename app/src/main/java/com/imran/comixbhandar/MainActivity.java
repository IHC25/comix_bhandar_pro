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
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/v73s63n3bkfvtyy6pgfdy/01.SOVIET-DESHE-TINTIN.pdf?rlkey=q0y933txnaz98xynldd7aowte&st=iq2088vw&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        ascharjontuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/i00j7ltqjtbx8ol3rpz33/ascharjontu-bengalipdfcomics.blogspot.in.pdf?rlkey=jlbsrigf7xs400nv58li948un&st=5w178sh9&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        danpiteKhaduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/uv4bf20346zazefctkdaw/Danpite-Khandu-Samagra-bengalipdfcomics.blogspot.in.pdf?rlkey=9q64ysbguyut6poh8okeuctsc&st=1yh8x587&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        eksringaObhijanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/xrkxbwkjd2frrrzbokang/eksringa-abhijaan-bengalipdfcomics.blogspot.in.pdf?rlkey=rsa8j1ahbqptayzly8rjuo15x&st=9s4s1heu&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        moiNiyeHoiChoiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/gs0ppr6z0iecqoxblddwi/Moi-Niye-Hoi-Choi.pdf?rlkey=7d5vxvcedigd9q7wz7pd2jgny&st=8hekrg9n&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });

        bappaRayBadamiChairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfLoader.pdfFileName = "https://www.dropbox.com/scl/fi/jjwpoq251hbx1d5mkwlfj/_-_-_-_.pdf?rlkey=uvz3a3yw1e3tq6rxd44ybe5r9&st=hd581mcf&dl=1";
                PdfLoader.pdfPage = 0;
                Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                startActivity(myIntent);
            }
        });


    }
}