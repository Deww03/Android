package com.example.uasadam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView listView;
    private CustomAdapter adapter;
    private ArrayList<String> barangList;
    private Handler handler = new Handler();
    private Button TambahButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        barangList = new ArrayList<String>();
        adapter = new CustomAdapter(this, barangList);
        listView.setAdapter(adapter);

        getData();

        TambahButton = (Button) findViewById(R.id.TambahData);
        TambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = "http://192.168.30.224:80/mahasiswa/getdata.php";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    urlConnection.disconnect();

                    parseJSON(stringBuilder.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void parseJSON(final String json) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    barangList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String npm = jsonObject.getString("npm");
                        String nama = jsonObject.getString("nama");
                        String alamat = jsonObject.getString("alamat");
                        String jurusan = jsonObject.getString("jurusan");

                        String item = "Npm: " + npm + "\nNama: " + nama + "\nalamat: " + alamat + "\nJurusan: " + jurusan;
                        barangList.add(item);
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteData(final String npm, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlString = "http://192.168.30.224:80/mahasiswa/hapus.php";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    String postData = "npm=" + npm;
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
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String message = jsonResponse.getString("message");
                                if (message.contains("berhasil")) {
                                    barangList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class CustomAdapter extends BaseAdapter {
        private Activity context;
        private ArrayList<String> items;

        public CustomAdapter(Activity context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                // Create a new LinearLayout
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layout.setPadding(8, 8, 8, 8);

                // Create a TextView
                TextView itemText = new TextView(context);
                LinearLayout.LayoutParams itemTextParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                itemText.setLayoutParams(itemTextParams);
                itemText.setTextSize(16);
                layout.addView(itemText);

                // Create Ubah button
                Button buttonUbah = new Button(context);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                buttonParams.setMargins(8, 0, 8, 0); // Add some margins
                buttonUbah.setLayoutParams(buttonParams);
                buttonUbah.setText("Ubah");
                buttonUbah.setTextSize(7); // Set text size smaller
                layout.addView(buttonUbah);

                // Create Hapus button
                Button buttonHapus = new Button(context);
                buttonHapus.setLayoutParams(buttonParams);
                buttonHapus.setText("Hapus");
                buttonHapus.setTextSize(7); // Set text size smaller
                layout.addView(buttonHapus);

                convertView = layout;
            }

            TextView itemText = (TextView) ((LinearLayout) convertView).getChildAt(0);
            Button buttonUbah = (Button) ((LinearLayout) convertView).getChildAt(1);
            Button buttonHapus = (Button) ((LinearLayout) convertView).getChildAt(2);

            final String item = items.get(position);
            itemText.setText(item);

            buttonUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle Ubah button click
                    String npm = item.split("\n")[0].split(": ")[1];
                    String nama = item.split("\n")[1].split(": ")[1];
                    String alamat = item.split("\n")[2].split(": ")[1];
                    String jurusan = item.split("\n")[3].split(": ")[1];

                    Intent intent = new Intent(context, UpdateDataActivity.class);
                    intent.putExtra("npm", npm);
                    intent.putExtra("nama", nama);
                    intent.putExtra("alamat", alamat);
                    intent.putExtra("jurusan", jurusan);
                    context.startActivity(intent);
                }
            });

            buttonHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle Hapus button click
                    String npm = item.split("\n")[0].split(": ")[1];
                    deleteData(npm, position);
                }
            });

            return convertView;
        }
    }
}
