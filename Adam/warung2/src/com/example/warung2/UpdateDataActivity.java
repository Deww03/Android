package com.example.warung2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDataActivity extends Activity {
    private EditText id_produkEditText, namaEditText, jenisEditText, hargaEditText, persediaanEditText;
    private Button saveButton;
    private String id_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        id_produkEditText = (EditText) findViewById(R.id.id_produkEditText);
        namaEditText = (EditText) findViewById(R.id.namaEditText);
        jenisEditText = (EditText) findViewById(R.id.jenisEditText);
        hargaEditText = (EditText) findViewById(R.id.hargaEditText);
        persediaanEditText = (EditText) findViewById(R.id.persediaanEditText);
        saveButton = (Button) findViewById(R.id.saveButton);

        Intent intent = getIntent();
        id_produk = intent.getStringExtra("id_produk");
        id_produkEditText.setText(id_produk);
        namaEditText.setText(intent.getStringExtra("nama"));
        jenisEditText.setText(intent.getStringExtra("jenis"));
        hargaEditText.setText(intent.getStringExtra("harga"));
        persediaanEditText.setText(intent.getStringExtra("persediaan"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        final String nama = namaEditText.getText().toString();
        final String jenis = jenisEditText.getText().toString();
        final String harga = hargaEditText.getText().toString();
        final String persediaan = persediaanEditText.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = "http://192.168.30.224:80/warung/update.php";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    String postData = "id_produk=" + id_produk + "&nama=" + nama + "&jenis=" + jenis + "&harga=" + harga + "&persediaan=" + persediaan;
                    urlConnection.getOutputStream().write(postData.getBytes("UTF-8"));

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    urlConnection.disconnect();

                    final String response = stringBuilder.toString();
                    Log.d("UpdateDataActivity", "Response: " + response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Handle response
                            Intent intent = new Intent(UpdateDataActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                } catch (Exception e) {
                    Log.e("UpdateDataActivity", "Error: " + e.getMessage(), e);
                }
            }
        }).start();
    }
}
