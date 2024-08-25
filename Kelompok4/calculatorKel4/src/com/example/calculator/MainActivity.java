package com.example.calculator;
//Kelompok 4
//Anggota :
//Adam Kurniawan
//Aldi Maulana Bahari
//Aldi Yudha Novaldi
//Andre Stefanus
//Kisah Tegar Putra Abdi
//Wisnu Tri Nugroho

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	Button satu,dua,tiga,empat,lima,enam,tujuh,delapan,sembilan,nol,tambah,kurang,bagi,kali,koma,delete,samadengan;
	EditText textfield;
	float angka1,angka2;
	boolean btambah,bkurang,bbagi,bkali;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		satu=(Button)findViewById(R.id.buttonsatu);
		dua=(Button)findViewById(R.id.buttondua);
		tiga=(Button)findViewById(R.id.buttontiga);
		empat=(Button)findViewById(R.id.buttonempat);
		lima=(Button)findViewById(R.id.buttonlima);
		enam=(Button)findViewById(R.id.buttonenam);
		tujuh=(Button)findViewById(R.id.buttontujuh);
		delapan=(Button)findViewById(R.id.buttondelapan);
		sembilan=(Button)findViewById(R.id.buttonsembilan);
		nol=(Button)findViewById(R.id.buttonnol);
		koma=(Button)findViewById(R.id.buttonkoma);
		tambah=(Button)findViewById(R.id.buttontambah);
		kurang=(Button)findViewById(R.id.buttonkurang);
		bagi=(Button)findViewById(R.id.buttonbagi);
		kali=(Button)findViewById(R.id.buttonkali);
		delete=(Button)findViewById(R.id.buttondelete);
		samadengan=(Button)findViewById(R.id.buttonsamadengan);
		textfield=(EditText)findViewById(R.id.editText1);
		
		satu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"1");
			}
		});
		dua.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"2");
			}
		});
		tiga.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"3");
			}
		});
		empat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"4");
			}
		});
		lima.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"5");
			}
		});
		enam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"6");
			}
		});
		tujuh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"7");
			}
		});
		delapan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"8");
			}
		});
		sembilan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"9");
			}
		});
		nol.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+"0");
			}
		});
		koma.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText(textfield.getText()+".");
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textfield.setText("");
			}
		});
		tambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (textfield==null){textfield.setText("");}
				else {
					angka1=Float.parseFloat(textfield.getText()+"");
					btambah=true;
					textfield.setText("");
				}
			}
		});
		kurang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (textfield==null){textfield.setText("");}
				else {
					angka1=Float.parseFloat(textfield.getText()+"");
					bkurang=true;
					textfield.setText("");
				}
			}
		});
		bagi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (textfield==null){textfield.setText("");}
				else {
					angka1=Float.parseFloat(textfield.getText()+"");
					bbagi=true;
					textfield.setText("");
				}
			}
		});
		kali.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (textfield==null){textfield.setText("");}
				else {
					angka1=Float.parseFloat(textfield.getText()+"");
					bkali=true;
					textfield.setText("");
				}
			}
		});
		samadengan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				angka2=Float.parseFloat(textfield.getText()+"");
				textfield.setText("");
				if (btambah==true){textfield.setText(angka1+angka2+"");btambah=false;}
				if (bkurang==true){textfield.setText(angka1-angka2+"");bkurang=false;}
				if (bbagi==true){textfield.setText(angka1/angka2+"");bbagi=false;}
				if (bkali==true){textfield.setText(angka1*angka2+"");bkali=false;}
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
