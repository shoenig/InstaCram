package com.happykrappy.instacram;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class DeckSelector extends DialogFragment {
	boolean editMode;
	
	DeckSelector(boolean editModeRequested) {
		this.editMode = editModeRequested;
	}
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        //Get all decks...
        final DatabaseHandler db = new DatabaseHandler(getActivity());
        int numDecks = db.countDecks();
        Log.i(MainActivity.TAG, "numDecks: " + numDecks);
        CharSequence[] deckNames = new CharSequence[numDecks];
        
        List<Deck> decks = db.getAllDecks();
        int index = 0;
        for (Deck d : decks) {
        	Log.i(MainActivity.TAG, d.getName());
        	deckNames[index++] = d.getName();
        }
        
        builder.setTitle(R.string.select_deck)
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // User cancelled the dialog
           }
        })
        .setItems(deckNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // The 'which' argument contains the index position of the selected item
            Intent i;
			if (editMode) {
				i = new Intent(getActivity(), EditDeckActivity.class);
			}
			else
				i = new Intent(getActivity(), DeckGalleryActivity.class);
			
			//Get deck from index selected
			final DatabaseHandler db = new DatabaseHandler(getActivity());
			Deck deckSelected = db.getNthDeck(which);
			
			Log.d(MainActivity.TAG, "User picked deck with id: " + deckSelected.getId());
			i.putExtra("DeckId", ""+ deckSelected.getId());
			startActivity(i); 
        }
 });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
