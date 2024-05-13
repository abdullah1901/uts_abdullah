package com.abdullah.cerpenkita;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNamaLengkap, etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaLengkap = findViewById(R.id.et_namaLengkap);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirmPassword);
        Button btnDaftar = findViewById(R.id.btn_daftar);
        TextView tvLogin = findViewById(R.id.tv_login);
        ImageView ivBack = findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data dari EditText
                String namaLengkap = etNamaLengkap.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validasi data sebelum menyimpan
                if (TextUtils.isEmpty(namaLengkap) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    // Menampilkan pesan error jika ada field yang kosong
                    Toast.makeText(RegisterActivity.this, "Harap lengkapi semua field!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Menampilkan pesan error jika email tidak valid
                    Toast.makeText(RegisterActivity.this, "Masukkan email yang valid!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    // Menampilkan pesan error jika password dan konfirmasi password tidak sesuai
                    Toast.makeText(RegisterActivity.this, "Password dan konfirmasi password tidak sesuai!", Toast.LENGTH_SHORT).show();
                } else {
                    // Pengecekan apakah email sudah terdaftar sebelumnya
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    boolean emailExists = false;

// Ambil semua entri yang tersimpan di SharedPreferences
                    Map<String, ?> allEntries = sharedPreferences.getAll();

// Iterasi semua entri untuk memeriksa apakah email sudah terdaftar
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        String key = entry.getKey();
                        // Jika kunci entry adalah kunci penyimpanan email
                        if (key.startsWith("user_") && key.endsWith("_email")) {
                            String existingEmail = sharedPreferences.getString(key, "");
                            // Jika email yang dimasukkan sudah terdaftar
                            if (existingEmail.equals(email)) {
                                emailExists = true;
                                break; // Keluar dari loop karena email sudah terdaftar
                            }
                        }
                    }

                    if (emailExists) {
                        // Menampilkan pesan error jika email sudah terdaftar
                        Toast.makeText(RegisterActivity.this, "Email sudah terdaftar!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Menyimpan data user menggunakan Shared Preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_" + email + "_nama_lengkap", namaLengkap);
                        editor.putString("user_" + email + "_email", email);
                        editor.putString("user_" + email + "_password", password);
                        editor.apply();

                        // Pindah ke halaman login
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Tutup aktivitas saat ini agar tidak bisa kembali ke aktivitas RegisterActivity dengan tombol back
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}