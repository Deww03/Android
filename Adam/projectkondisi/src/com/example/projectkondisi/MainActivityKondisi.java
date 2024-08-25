package com.example.projectkondisi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivityKondisi extends Activity {
	
	EditText txtUAS, txtUTS, txtTugas;
	Button btnCheck;
	TextView txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main_activity_kondisi);
        final Button btnCheck = (Button) findViewById(R.id.btnCheck);
        
        final EditText txtUAS = (EditText) findViewById(R.id.txtUAS);
        final EditText txtUTS = (EditText) findViewById(R.id.txtUTS);
        final EditText txtTugas = (EditText) findViewById(R.id.txtTugas);
        final TextView txtHasil = (TextView) findViewById(R.id.txtHasil);
        
        btnCheck.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		int nilaiUAS = Integer.parseInt(txtUAS.getText().toString());
        		int nilaiUTS = Integer.parseInt(txtUTS.getText().toString());
        		int nilaiTugas = Integer.parseInt(txtTugas.getText().toString());
        		
        		double rataRata = (nilaiUAS + nilaiUTS + nilaiTugas)/3.0;
        		
        		String status;
        		String grade;
        		if (rataRata >= 90) {
        			status = "Lulus";
        			grade = "A";
        		} else if (rataRata >=80){
        			status = "Lulus";
        			grade = "B";
        		} else if (rataRata >=70){
        			status = "Lulus";
        			grade = "C";
        		} else if (rataRata >=60){
        			status = "Lulus";
        			grade = "D";
        		} else {
        			status = "Tidak Lulus";
        			grade = "E";
        		}
        		txtHasil.setText("Rata-rata Nilai: " + String.format("%.2f", rataRata) + "\nStatus: " + status + "\nGrade: " + grade);
        	}
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_kondisi, menu);
        return true;
    }
    
}
