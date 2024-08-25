package com.example.warung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text. TextUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddDataActivity extends Activity {

    private EditText id_produk, nama, jenis, harga, persediaan;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        id_produk = (EditText) findViewById(R.id.id_produk);
        nama = (EditText) findViewById(R.id.nama);
        jenis = (EditText) findViewById(R.id.jenis);
        harga = (EditText) findViewById(R.id.harga);
        persediaan = (EditText) findViewById(R.id.persediaan);

        submit_button = (Button) findViewById(R.id.submit_button);
        
        submit_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String idProduk = id_produk.getText().toString();
	            String namaProduk = nama.getText().toString();
	            String jenisProduk = jenis.getText().toString();
	            String hargaProduk = harga.getText().toString();
	            String persediaanProduk = persediaan.getText().toString();
	            
	            if (TextUtils.isEmpty(idProduk) || TextUtils.isEmpty(namaProduk) || TextUtils.isEmpty(jenisProduk) || TextUtils.isEmpty(hargaProduk) || TextUtils.isEmpty(persediaanProduk)) {
                    Toast.makeText(AddDataActivity.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                	sendDataToServer(idProduk,namaProduk,jenisProduk,hargaProduk,persediaanProduk);
                }
				
			}
		});
    }
    private void sendDataToServer(final String id_Produk, final String nama, final String jenis,final String harga, final String persediaan) {

    	new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				URL url = new URL("http://192.168.88.170:80/warung/insertData.php");
    				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    				conn.setRequestMethod("POST");
    				conn.setDoOutput(true);

    				// Construct data to send
    				String postData = "id_produk=" + id_Produk +
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
							}
						});

						// Setelah berhasil disimpan, kembali ke MainActivity
						Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}
					else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(AddDataActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
							}
						});
					}
    			} 
    			catch (Exception e) {
    				e.printStackTrace();
    				runOnUiThread(new Runnable(){
    					@Override
    					public void run(){
    						Toast.makeText(AddDataActivity.this, "Terjadi Kesalahan",Toast.LENGTH_SHORT).show(); 
    					}
    				});
    			}
    		}
    	}).start();
    }
}