package com.abdullah.cerpenkita;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private ImageButton ibCamera;
    private ImageView ivFoto,ivBack;
    private TextView tvNama,tvEmail;

    private String currentEmail, currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ibCamera = findViewById(R.id.ib_camera);
        ivFoto = findViewById(R.id.iv_foto);
        ivBack = findViewById(R.id.iv_back);
        tvNama = findViewById(R.id.tv_nama2);
        tvEmail = findViewById(R.id.tv_email2);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        currentEmail = sharedPreferences.getString("current_user_email", "");
        currentUser = sharedPreferences.getString("current_user_nama_lengkap","");
        tvNama.setText(currentUser);
        tvEmail.setText(currentEmail);
        displayProfilePhoto();

        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            saveImageToInternalStorage(imageBitmap, currentEmail);

            ivFoto.setImageBitmap(imageBitmap);

            Toast.makeText(ProfileActivity.this, "Gambar berhasil disimpan", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProfilePhoto() {
        File directory = getApplicationContext().getDir("profile_images", Context.MODE_PRIVATE);
        File file = new File(directory, currentEmail + ".jpg");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivFoto.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Gambar profil tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageToInternalStorage(Bitmap imageBitmap, String email) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("profile_images", Context.MODE_PRIVATE);
        File imagePath = new File(directory, email + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}