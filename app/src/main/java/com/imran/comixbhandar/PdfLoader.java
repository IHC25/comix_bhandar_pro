package com.imran.comixbhandar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PdfLoader extends AppCompatActivity {

    PDFView pdfView;
    LottieAnimationView pdfLoading;
    public static String pdfFileName = "";
    public static int pdfPage = 0;
    public static boolean isPdfFromLink = false;

    private OkHttpClient client = new OkHttpClient(); // For downloading PDF

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

        // Assigning variables
        pdfView = findViewById(R.id.pdfView);
        pdfLoading = findViewById(R.id.pdfLoading);

        // Initial Visibility
        pdfView.setVisibility(View.INVISIBLE);
        pdfLoading.setVisibility(View.VISIBLE);

        // Retrieve the last viewed page from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ComicPreferences", MODE_PRIVATE);
        pdfPage = sharedPreferences.getInt(pdfFileName, 0); // Use the pdfFileName as the key

        // Loading PDF
        if (isPdfFromLink) {
            // Here is the code for loading pdf from the link
            downloadPdfFromUrl(pdfFileName);
        } else {
            loadPdfFromAsset();
        }
    }

    // Method to load PDF from assets
    private void loadPdfFromAsset() {
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
                        savePageNumber(pdfFileName, page);
                    }
                })
                .defaultPage(pdfPage)
                .enableAnnotationRendering(false)
                .enableAntialiasing(true)
                .load();
    }

    // Method to download the PDF from URL
    private void downloadPdfFromUrl(String url) {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Save PDF to cache
                    File file = new File(getCacheDir(), "downloaded.pdf");
                    try (InputStream inputStream = response.body().byteStream();
                         FileOutputStream outputStream = new FileOutputStream(file)) {
                        byte[] buffer = new byte[2048];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, length);
                        }
                    }

                    // Load the PDF into PDFView on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> loadPdfFromFile(file));
                }
            }
        });
    }

    // Method to load PDF from a file
    private void loadPdfFromFile(File file) {
        pdfView.fromFile(file)
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
