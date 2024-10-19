package com.imran.comixbhandar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class PdfLoader extends AppCompatActivity {

    PDFView pdfView;
    LottieAnimationView pdfLoading;
    public static String pdfFileName = "";
    public static int pdfPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pdf_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assigning variables
        pdfView = findViewById(R.id.pdfView);
        pdfLoading = findViewById(R.id.pdfLoading);

        //Initial Visibility
        pdfView.setVisibility(View.INVISIBLE);
        pdfLoading.setVisibility(View.VISIBLE);

        // Retrieve the last viewed page from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ComicPreferences", MODE_PRIVATE);
        pdfPage = sharedPreferences.getInt(pdfFileName, 0); // Use the pdfFileName as the key


        //Loading PDF
        pdfView.fromAsset(pdfFileName)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pdfView.setVisibility(View.VISIBLE);
                        pdfLoading.setVisibility(View.GONE);
                    }
                })
                .pageFitPolicy(FitPolicy.BOTH)
                .autoSpacing(true)
                .pageSnap(true)
                .pageFling(true)
                .nightMode(false)
                .swipeHorizontal(false)
                .enableSwipe(true)
                .enableDoubletap(true)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        pdfPage = page;
                        // Save the current page number in SharedPreferences
                        savePageNumber(pdfFileName, page);
                    }
                })
                .defaultPage(pdfPage)
                .enableAnnotationRendering(false)
                .enableAntialiasing(true)
                .load();
    }

    // Save the current page number to SharedPreferences
    public void savePageNumber(String fileName, int pageNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("ComicPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(fileName, pageNumber); // Save page number using file name as the key
        editor.apply(); // Apply changes asynchronously
    }
}