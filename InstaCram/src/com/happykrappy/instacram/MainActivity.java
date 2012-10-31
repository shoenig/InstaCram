package com.happykrappy.instacram;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button newDeckButton;
	private Button viewDeckButton;
	private Button editDeckButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newDeckButton = (Button) findViewById(R.id.new_deck);
        newDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(MainActivity.this, "New Deck Button Clicked", Toast.LENGTH_SHORT).show();
        	}
		});
        Button editDeckButton = (Button) findViewById(R.id.edit_deck);
        editDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(MainActivity.this, "Edit Deck Button Clicked", Toast.LENGTH_SHORT).show();
        	}
		});
        Button viewDeckButton = (Button) findViewById(R.id.view_deck);
        viewDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(MainActivity.this, "View Deck Button Clicked", Toast.LENGTH_SHORT).show();
        	}
		});        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
