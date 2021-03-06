package com.happykrappy.instacram;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {

	final Context context = this;
	static final String TAG = "InstaCram Tag";
	
	private Button newDeckButton;
	private Button viewDeckButton;
	private Button editDeckButton;
	
	DialogFragment df;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final DatabaseHandler db = new DatabaseHandler(this);
        
        newDeckButton = (Button) findViewById(R.id.new_deck);
        newDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		showAddDeckAlert(new AlertDialog.Builder(MainActivity.this), db, false);
        	}
        	
		});
        editDeckButton = (Button) findViewById(R.id.edit_deck);
        editDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
                final DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                int numDecks = db.countDecks();
                if (numDecks == 0) {
                	showAddDeckAlert(new AlertDialog.Builder(MainActivity.this), db, true);
                }
                else {
                	DeckSelector newFragment = new DeckSelector();
                	newFragment.setEditModeRequested(true);
                	newFragment.show(getSupportFragmentManager(), "Deck Selector Opened In Edit Mode");
                }
        	}
		});
        viewDeckButton = (Button) findViewById(R.id.view_deck);
        viewDeckButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
                final DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                int numDecks = db.countDecks();
                if (numDecks == 0) {
                	showAddDeckAlert(new AlertDialog.Builder(MainActivity.this), db, true);
                }
                else {
            		DeckSelector newFragment = new DeckSelector();
            		newFragment.setEditModeRequested(false);
            	    newFragment.show(getSupportFragmentManager(), "Deck Selector Opened in View Mode");
                }
        	}
		});        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    void showAddDeckAlert(AlertDialog.Builder alert,final DatabaseHandler db, boolean startingFromScratch) {
    	if (startingFromScratch)
    		alert.setTitle(R.string.no_decks_available);
    	else
    		alert.setTitle(R.string.adding_new_deck);
		alert.setMessage(R.string.input_new_deck_name);

		// Set an EditText view to get user input 
		final EditText input = new EditText(MainActivity.this);
		alert.setView(input);

		alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			Editable value = input.getText();

				//add newDeck to our databases of decks
				Log.d(TAG, "Inserting ..");
				db.addDeck(new Deck(value.toString()));
				int newDeckId = db.selectDeck(value.toString());
				
				//ViewDeckActivity
				Intent i = new Intent(MainActivity.this, EditDeckActivity.class);
				Log.d(TAG, value.toString());
				i.putExtra("DeckId", ""+newDeckId);
				startActivity(i); 
    		}
		});

		alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
    }
}
