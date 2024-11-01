package com.imran.comixbhandar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
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

        //Assign Grid View
        gridView = findViewById(R.id.gridView);

        //Create Comic Data
        CreateComicData();

        //Assign Adapter
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
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
            int resourceId = getResources().getIdentifier(cover.substring(1), "drawable", getPackageName());
            comicCover.setImageResource(resourceId);
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

    //Method to Insert Comic Data in the Array
    private void CreateComicData() {
        //Soviet Tintin
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/soviet_deshe_tintin");
        comicData.put("title", "সোভিয়েত দেশে টিনটিন");
        comicData.put("url", "https://www.dropbox.com/scl/fi/v73s63n3bkfvtyy6pgfdy/01.SOVIET-DESHE-TINTIN.pdf?rlkey=q0y933txnaz98xynldd7aowte&st=iq2088vw&dl=1");
        comicCollections.add(comicData);
        //Aschar Jontu
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/ascharjontu");
        comicData.put("title", "আশ্চার্জন্তু");
        comicData.put("url", "https://www.dropbox.com/scl/fi/i00j7ltqjtbx8ol3rpz33/ascharjontu-bengalipdfcomics.blogspot.in.pdf?rlkey=jlbsrigf7xs400nv58li948un&st=5w178sh9&dl=1");
        comicCollections.add(comicData);
        //Danpite Khadu
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/danpite_khadu");
        comicData.put("title", "ডানপিটে খাঁদু সমগ্র");
        comicData.put("url", "https://www.dropbox.com/scl/fi/uv4bf20346zazefctkdaw/Danpite-Khandu-Samagra-bengalipdfcomics.blogspot.in.pdf?rlkey=9q64ysbguyut6poh8okeuctsc&st=1yh8x587&dl=1");
        comicCollections.add(comicData);
        //eksringa obhijan
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/eksringa_obhijan");
        comicData.put("title", "একশৃঙ্গ অভিযান");
        comicData.put("url", "https://www.dropbox.com/scl/fi/xrkxbwkjd2frrrzbokang/eksringa-abhijaan-bengalipdfcomics.blogspot.in.pdf?rlkey=rsa8j1ahbqptayzly8rjuo15x&st=9s4s1heu&dl=1");
        comicCollections.add(comicData);
        //Moi Niye Hoi Choi
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/moi_niye_hoi_choi");
        comicData.put("title", "মই নিয়ে হৈচৈ");
        comicData.put("url","https://www.dropbox.com/scl/fi/gs0ppr6z0iecqoxblddwi/Moi-Niye-Hoi-Choi.pdf?rlkey=7d5vxvcedigd9q7wz7pd2jgny&st=8hekrg9n&dl=1");
        comicCollections.add(comicData);
        //Rappa Ray Badami Chair
        comicData = new HashMap<>();
        comicData.put("cover", "@drawable/bappa_ray_badami_chair");
        comicData.put("title", "রাপ্পা রায় ও বাদামি চেয়ার");
        comicData.put("url","https://www.dropbox.com/scl/fi/jjwpoq251hbx1d5mkwlfj/_-_-_-_.pdf?rlkey=uvz3a3yw1e3tq6rxd44ybe5r9&st=hd581mcf&dl=1");
        comicCollections.add(comicData);

    }
}