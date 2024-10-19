package com.imran.comixbhandar;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    LinearLayout internetLost_layout;
    Button tryAgainBtn;
    public static String pdfFileName = ""; // URL of the PDF file
    public static int pdfPage = 0;
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
        internetLost_layout = findViewById(R.id.internetLost_layout);
        tryAgainBtn = findViewById(R.id.try_again_btn);

        // Initial Visibility
        pdfView.setVisibility(View.INVISIBLE);
        pdfLoading.setVisibility(View.VISIBLE);
        internetLost_layout.setVisibility(View.GONE);

        // Retrieve the last viewed page from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ComicPreferences", MODE_PRIVATE);
        pdfPage = sharedPreferences.getInt(pdfFileName, 0); // Use the pdfFileName (URL) as the key

        // Load or download the PDF
        downloadOrLoadPdf(pdfFileName);
    }

    // Method to download the PDF from URL or load it from cache if it exists
    private void downloadOrLoadPdf(String url) {
        // Define the file name for the cached PDF
        File file = new File(getCacheDir(), getFileNameFromUrl(url));

        // Check if the PDF is already cached
        if (file.exists()) {
            // If the file exists, load it directly from the cache
            loadPdfFromFile(file);
        } else {
            // If the file doesn't exist, download it from the URL
            downloadPdfFromUrl(url, file);
        }
    }

    // Method to download the PDF from URL
    private void downloadPdfFromUrl(String url, File file) {
        // Check Internet Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) PdfLoader.this.getSystemService(CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager) PdfLoader.this.getApplicationContext().getSystemService(WIFI_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if(networkInfo != null && networkInfo.isConnected() || wifiManager.isWifiEnabled() && wifiInfo.getNetworkId() != -1){
            // Download the PDF from the URL
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PdfLoader.this, "Download Failed! Try again later.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        // Save the downloaded PDF to the cache directory
                        try (InputStream inputStream = response.body().byteStream();
                             FileOutputStream outputStream = new FileOutputStream(file)) {
                            byte[] buffer = new byte[2048];
                            int length;
                            while ((length = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, length);
                            }
                        }

                        // Load the PDF into PDFView on the main thread after downloading
                        new Handler(Looper.getMainLooper()).post(() -> loadPdfFromFile(file));
                    }
                }
            });
        }
        else {
            // If no internet connection, Guide the user to check internet
            internetLost_layout.setVisibility(View.VISIBLE);
            pdfLoading.setVisibility(View.GONE);
            pdfView.setVisibility(View.INVISIBLE);
            Toast.makeText(PdfLoader.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        //Handle Try Again Button
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the loading animation and hide the internet lost layout
                internetLost_layout.setVisibility(View.GONE);
                pdfLoading.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.INVISIBLE);
                //try from the beginning
                downloadOrLoadPdf(pdfFileName);
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
                        internetLost_layout.setVisibility(View.GONE);
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
        editor.putInt(fileName, pageNumber); // Save page number using URL (fileName) as the key
        editor.apply(); // Apply changes asynchronously
    }

    // Helper method to generate a unique file name from a URL
    private String getFileNameFromUrl(String url) {
        return url.hashCode() + ".pdf"; // Generates a unique file name based on the URL's hash code
    }
}
