package com.example.uasadam;

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

 private EditText npm, nama, alamat, jurusan;
 private Button submitButton;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_add_data);

     npm = (EditText) findViewById(R.id.npm);
     nama = (EditText) findViewById(R.id.nama);
     alamat = (EditText) findViewById(R.id.alamat);
     jurusan = (EditText) findViewById(R.id.jurusan);
     submitButton = (Button) findViewById(R.id.submit_button);

     submitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String npmStr = npm.getText().toString();
             String namaStr = nama.getText().toString();
             String alamatStr = alamat.getText().toString();
             String jurusanStr = jurusan.getText().toString();

             if (TextUtils.isEmpty(npmStr) || TextUtils.isEmpty(namaStr) || 
            		 TextUtils.isEmpty(alamatStr) || TextUtils.isEmpty(jurusanStr)){
                 Toast.makeText(AddDataActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
             } else {
                 // Mengirim data ke server
                 sendDataToServer(npmStr, namaStr, alamatStr, jurusanStr);
             }
         }
     });
 }

 private void sendDataToServer(final String npm, final String nama, final String alamat, 
		 final String jurusan) {
     new Thread(new Runnable() {
         @Override
         public void run() {
             try {
                 URL url = new URL("http://192.168.30.224:80/mahasiswa/insertdata.php");
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 conn.setRequestMethod("POST");
                 conn.setDoOutput(true);
                 // Construct data to send
                 String postData = "npm=" + npm +
                                   "&nama=" + nama +
                                   "&alamat=" + alamat +
                                   "&jurusan=" + jurusan;

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

