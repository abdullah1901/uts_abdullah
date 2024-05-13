package com.abdullah.cerpenkita;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ListView cerpenListView;
    private ArrayList<CerpenItem> cerpenList;
    private ArrayAdapter<CerpenItem> adapter;
    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng jakartaLocation = new LatLng(-6.2088, 106.8456); // Lokasi Jakarta Kota

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cerpenListView = findViewById(R.id.cerpenListView);
        TextView tvProfile = findViewById(R.id.tv_profile);
        ImageView ivBack = findViewById(R.id.iv_back);
        TextView tvTentangKami = findViewById(R.id.tv_tentangKami);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        cerpenList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cerpenList);
        cerpenListView.setAdapter(adapter);

        cerpenList.add(new CerpenItem("An Occurrence at Owl Creek Bridge oleh Ambrose Bierce", "Seorang pria bernama Peyton Farquhar telah dijebak oleh tentara Union selama Perang Saudara Amerika. Cerita dimulai dengan detil-detil eksekusi gantungnya di Owl Creek Bridge, tetapi mengungkapkan bahwa sebenarnya dia hanya membayangkan pelariannya yang dramatis dari eksekusi tersebut."));
        cerpenList.add(new CerpenItem("The Lottery oleh Shirley Jackson", "Cerita dimulai dengan gambaran desa kecil yang bersiap-siap untuk sebuah undian tahunan yang tampak biasa. Namun, ketika pemenang undian diumumkan, terungkap bahwa \"hadiah\" tersebut sebenarnya adalah kematian dengan cara dilempari batu oleh sesama penduduk desa."));
        cerpenList.add(new CerpenItem("Harrison Bergeron oleh Kurt Vonnegut", "Kisah ini berlangsung di dunia di mana pemerintah telah mengatur kesetaraan mutlak di antara semua orang. Tokoh utamanya, Harrison Bergeron, memiliki kecerdasan dan kekuatan yang luar biasa, tetapi dikebiri secara fisik dan diberi berat tambahan untuk menjaga agar dia tidak melebihi kemampuan orang lain."));
        cerpenList.add(new CerpenItem("The Tell-Tale Heart oleh Edgar Allan Poe", "Seorang narator tak bernama membahas pembunuhan yang dilakukannya pada seorang tua yang memiliki mata kecil dan bermata jahat. Meskipun dia percaya bahwa dia telah melakukan pembunuhan dengan cerdas, suara jantung korban yang sudah mati menghantuinya dan membuatnya akhirnya mengakuinya."));
        cerpenList.add(new CerpenItem("The Gift of the Magi oleh O. Henry", "Della dan Jim adalah pasangan muda yang sangat mencintai satu sama lain, tetapi mereka hidup dalam kemiskinan. Della memotong rambutnya yang panjang dan indah untuk membeli jam tangan emas untuk Jim, sementara Jim menjual jam tangan kesayangannya untuk membeli sepasang sisir yang indah untuk rambut Della. Mereka akhirnya menyadari bahwa cinta mereka lebih berharga dari materi apa pun."));
        adapter.notifyDataSetChanged();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerpenListView.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                ivBack.setVisibility(View.GONE);
            }
        });

        tvTentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerpenListView.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cerpenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showcerpenDetail(cerpenList.get(position).getTitle(), cerpenList.get(position).getContent());
            }
        });
    }

    private void showcerpenDetail(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        googleMap.addMarker(new MarkerOptions()
                .position(jakartaLocation)
                .title("Jakarta Kota"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakartaLocation, 10));
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}