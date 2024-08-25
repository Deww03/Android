package com.example.warung;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class MainActivity extends Activity {
	
	private Button TambahData;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> barangList;
	private Handler handler = new Handler();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TambahData = (Button)findViewById(R.id.TambahData);
		listView = (ListView) findViewById(R.id.listView);
		barangList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,barangList);
		listView.setAdapter(adapter);
		
		getData();
		
	TambahData.setOnClickListener(new View.OnClickListener() {
		
		
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	});
	}
	
	private void getData() {
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
				String urlString = "http://http://192.168.88.170:80/warung/getData.php";
				try {
					URL url = new URL(urlString);
					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setRequestMethod("GET");
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					StringBuilder stringBuilder = new StringBuilder();
					String line;
					while ((line = reader.readLine()) !=null){
						stringBuilder.append(line);
					}
					
					reader.close();
					urlConnection.disconnect();
					
					parseJSON(stringBuilder.toString());
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	private void parseJSON (final String json){
		handler.post(new Runnable(){
			@Override
			public void run(){
				try{
					JSONArray jsonArray = new JSONArray(json);
					for (int i = 0; i < jsonArray.length(); i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String kode = jsonObject.getString("id_produk");
						String nama = jsonObject.getString("nama_produk");
						String harga = jsonObject.getString("harga");
						String stok = jsonObject.getString("stok");
						String jenis = jsonObject.getString("jenis");
						
						String item ="Kode : " + kode + "\nNama : " + nama + "\nHarga : " + harga + "\nStok : " + stok + "\nJenis : " + jenis;
						barangList.add(item);
					}
					adapter.notifyDataSetChanged();
				}catch (JSONException e){
					e.printStackTrace();
				}
			}
		});
	}
	
}
