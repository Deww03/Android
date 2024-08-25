package com.example.warung2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddDataActivity extends Activity {

 private EditText id_produk, nama, jenis, harga, persediaan;
 private Button submitButton;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_add_data);

     id_produk = (EditText) findViewById(R.id.id_produk);
     nama = (EditText) findViewById(R.id.nama);
     jenis = (EditText) findViewById(R.id.jenis);
     harga = (EditText) findViewById(R.id.harga);
     persediaan = (EditText) findViewById(R.id.persediaan);
     submitButton = (Button) findViewById(R.id.submit_button);

     submitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String id_produkStr = id_produk.getText().toString();
             String namaStr = nama.getText().toString();
             String jenisStr = jenis.getText().toString();
             String hargaStr = harga.getText().toString();
             String persediaanStr = persediaan.getText().toString();

             if (TextUtils.isEmpty(id_produkStr) || TextUtils.isEmpty(namaStr) || 
            		 TextUtils.isEmpty(jenisStr) || TextUtils.isEmpty(hargaStr) || 
            		 TextUtils.isEmpty(persediaanStr)) {
                 Toast.makeText(AddDataActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
             } else {
                 // Mengirim data ke server
                 sendDataToServer(id_produkStr, namaStr, jenisStr, hargaStr, persediaanStr);
             }
         }
     });
 }

 private void sendDataToServer(final String id_produk, final String nama, final String jenis, 
		 final String harga, final String persediaan) {
     new Thread(new Runnable() {
         @Override
         public void run() {
             try {
                 URL url = new URL("http://192.168.30.224:80/warung/insertdata.php");
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 conn.setRequestMethod("POST");
                 conn.setDoOutput(true);
                 // Construct data to send
                 String postData = "id_produk=" + id_produk +
                                   "&nama=" + nama +
                                   "&jenis=" + jenis +
                                   "&harga=" + harga +
                                   "&persediaan=" + persediaan;

                 OutputStream os = conn.getOutputStream();
                 os.write(postData.getBytes());
                 os.flush();
                 os.close();

                 int responseCode = conn.getResponseCode();
                 if (responseCode == HttpURLConnection.HTTP_OK) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(AddDataActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                             // Setelah berhasil disimpan, kembali ke MainActivity
                             Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                             finish(); // Menutup AddDataActivity
                         }
                     });
                 } else {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(AddDataActivity.this, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }
             } catch (Exception e) {
                 e.printStackTrace();
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Toast.makeText(AddDataActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         }
     }).start();
 }
}

