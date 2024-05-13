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

        cerpenList.add(new CerpenItem("Cerpen 1", "Isi cerpen 1"));
        cerpenList.add(new CerpenItem("Cerpen 2", "Isi cerpen 2"));
        cerpenList.add(new CerpenItem("Cerpen 3", "Isi cerpen 3"));
        cerpenList.add(new CerpenItem("Cerpen 4", "Isi cerpen 4"));
        cerpenList.add(new CerpenItem("Cerpen 5", "Isi cerpen 5"));
        cerpenList.add(new CerpenItem("Cerpen 6", "Isi cerpen 6"));
        cerpenList.add(new CerpenItem("Cerpen 7", "Isi cerpen 7"));
        cerpenList.add(new CerpenItem("Cerpen 8", "Isi cerpen 8"));
        cerpenList.add(new CerpenItem("Cerpen 9", "Isi cerpen 9"));
        cerpenList.add(new CerpenItem("Cerpen 10", "Isi cerpen 10"));
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