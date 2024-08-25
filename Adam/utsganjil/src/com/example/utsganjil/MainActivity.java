package com.example.utsganjil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	EditText txtJasa, txtOli, txtSparepart;
	Button btnHitung;
	TextView txtHasil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 final Button btnHitung = (Button) findViewById(R.id.btnHitung);  
	     final EditText txtJasa = (EditText) findViewById(R.id.txtJasa);
	     final EditText txtOli = (EditText) findViewById(R.id.txtOli);
	     final EditText txtSparepart = (EditText) findViewById(R.id.txtSparepart);
	     final TextView txtHasil = (TextView) findViewById(R.id.txtHasil);
	        
	        btnHitung.setOnClickListener(new View.OnClickListener(){
	        	@Override
	        	public void onClick(View v) {
	        		int nilaiJasa = Integer.parseInt(txtJasa.getText().toString());
	        		int nilaiOli = Integer.parseInt(txtOli.getText().toString());
	        		int nilaiSparepart = Integer.parseInt(txtSparepart.getText().toString());
	        		double discount;
	        		double biaya = nilaiJasa + nilaiOli + nilaiSparepart;
	        		double total;
	        		
	        		String discountText;
	        		if (biaya > 2000000){
	        			discountText = "Mendapatkan Discount 25%";
	        			discount = biaya * 0.25;
	        			total = biaya - discount;
	        		} else if (biaya > 1000000) {
	        			discountText = "Mendapatkan Discount 10%";
	        			discount = biaya * 0.10;
	        			total = biaya - discount;
	        		} else {
	        			discountText = "Tidak Mendapatkan Discount";
	        			discount = 0;
	        			total = biaya - discount;
	        		}
	        		txtHasil.setText("Biaya : " + String.format("%.2f", biaya) + "\nDiscount: " + discountText + "\n\nTotal: " + total);
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
