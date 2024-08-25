package com.example.uasadam;


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
    private EditText npmEditText, namaEditText, alamatEditText, jurusanEditText;
    private Button saveButton;
    private String npm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        npmEditText = (EditText) findViewById(R.id.npmEditText);
        namaEditText = (EditText) findViewById(R.id.namaEditText);
        alamatEditText = (EditText) findViewById(R.id.alamatEditText);
        jurusanEditText = (EditText) findViewById(R.id.jurusanEditText);
        saveButton = (Button) findViewById(R.id.saveButton);

        Intent intent = getIntent();
        npm = intent.getStringExtra("npm");
        npmEditText.setText(npm);
        namaEditText.setText(intent.getStringExtra("nama"));
        alamatEditText.setText(intent.getStringExtra("alamat"));
        jurusanEditText.setText(intent.getStringExtra("jurusan"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        final String nama = namaEditText.getText().toString();
        final String alamat = alamatEditText.getText().toString();
        final String jurusan = jurusanEditText.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = "http://192.168.30.224:80/mahasiswa/update.php";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    String postData = "npm=" + npm + "&nama=" + nama + "&alamat=" + alamat + "&jurusan=" + jurusan;
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
