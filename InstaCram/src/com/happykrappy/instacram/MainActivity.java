package com.happykrappy.instacram;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button newDeckButton;
	private Button viewDeckButton;
	private Button editDeckButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newDeckButton = (Button) findViewById(R.id.new_deck);
        newDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		//Toast.makeText(MainActivity.this, "New Deck Button Clicked", Toast.LENGTH_SHORT).show();
        		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        		alert.setTitle("New Deck");
        		alert.setMessage("Input Deck Name");

        		// Set an EditText view to get user input 
        		final EditText input = new EditText(MainActivity.this);
        		alert.setView(input);

        		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int whichButton) {
        		  Editable value = input.getText();
        		  Deck newDeck = new Deck(value.toString());
        		  
        		  //add newDeck to our databases of decks
        		  
        		  //redirect user to deck page with First Card Button
        		  //ViewDeckActivity
        		  Intent i = new Intent(MainActivity.this, ViewDeckActivity.class);
        		  i.putExtra("DeckName", value.toString());
        		  startActivity(i); 
        		  }
        		});

        		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  public void onClick(DialogInterface dialog, int whichButton) {
        		    // Canceled.
        		  }
        		});

        		alert.show();
        	}
        	
        	
		});
        editDeckButton = (Button) findViewById(R.id.edit_deck);
        editDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText(MainActivity.this, "Edit Deck Button Clicked", Toast.LENGTH_SHORT).show();
        	}
		});
        viewDeckButton = (Button) findViewById(R.id.view_deck);
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
