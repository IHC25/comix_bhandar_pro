package com.imran.comixbhandar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ProgressBar progressBar;
    ArrayList< HashMap<String,String> > comicCollections = new ArrayList<>();
    HashMap<String,String> comicData = new HashMap<>();

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

        //Assign Variable
        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progressBar);

        //Load Comic Data from Server
        LoadDataFromServer();
    }

    //=================================================================================================================//
    //Making of a Custom Adapter for the GridView
    //=================================================================================================================//
    public class MyAdapter extends BaseAdapter{
        LayoutInflater inflater;

        @Override
        public int getCount() {
            return comicCollections.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //Inflate the layout for each list item
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View myView = inflater.inflate(R.layout.comic, viewGroup, false);

            //Assign Variables
            ImageView comicCover = myView.findViewById(R.id.comicCover);
            TextView comicTitle = myView.findViewById(R.id.comicTitle);
            LinearLayout layComicBtn = myView.findViewById(R.id.layComicBtn);

            //Assign Data
            HashMap<String,String> hashMap = comicCollections.get(position);
            String cover = hashMap.get("cover");
            String title = hashMap.get("title");
            String url = hashMap.get("url");
            Picasso.get()
                    .load(cover)
                    .resize(150, 150)
                    .into(comicCover);
            comicTitle.setText(title);

            //Comic Click Listener
            layComicBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PdfLoader.pdfFileName = url;
                    Intent myIntent = new Intent(MainActivity.this , PdfLoader.class);
                    startActivity(myIntent);
                }
            });
            return myView;
        }
    }

    //Method to load Data from server
    private void LoadDataFromServer() {
        //server address
        String url = "http://imranhchy.whf.bz/apps/comixbhangar.json";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Parse the response here
                try {
                    for(int x = 0; x < response.length(); x++){
                        JSONObject object = response.getJSONObject(x);
                        String cover = object.getString("cover");
                        String title = object.getString("title");
                        String url = object.getString("url");
                        comicData = new HashMap<>();
                        comicData.put("cover", cover);
                        comicData.put("title", title);
                        comicData.put("url", url);
                        comicCollections.add(comicData);
                    }
                    //Assign Adapter
                    MyAdapter adapter = new MyAdapter();
                    gridView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this,"Server Error: Try Again Later",Toast.LENGTH_LONG).show();
                Log.d("serverError", "onErrorResponse: "+error.getMessage());
            }
        });

        //Add the request to the RequestQueue
        requestQueue.add(arrayRequest);

    }
}