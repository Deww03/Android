package com.example.variabeldata;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText objnilai1 = (EditText) findViewById(R.id.angka1);
        final EditText objnilai2 = (EditText) findViewById(R.id.angka2);
        final Button objtombol = (Button) findViewById(R.id.btnhitung);
        final TextView objhasil = (TextView) findViewById(R.id.hasil);
        
        objtombol.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View arg0){
        		int valnilai1 = Integer
        				.parseInt(objnilai1.getText().toString());
        		
        		int valnilai2 = Integer
        				.parseInt(objnilai2.getText().toString());
        		int hasilpenjumlahan = valnilai1 * valnilai2;
        		
        		objhasil.setText(Integer.toString(hasilpenjumlahan));
        	}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
