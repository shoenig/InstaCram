package com.happykrappy.instacram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.UnsupportedOperationException;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "decksManager";
    
	static final String TABLE_DECKS = "decks";
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
    
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DECKS_TABLE = "CREATE TABLE " + TABLE_DECKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_DECKS_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new UnsupportedOperationException();
	}
	
	public void addDeck(Deck deck) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, deck.getName());
	 
	    // Inserting Row
	    db.insert(TABLE_DECKS, null, values);
	    db.close();
	}
	
	public void addCard(Card c) {
		
	}

	public int selectDeck(String deckName) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.query(TABLE_DECKS, new String[] { KEY_ID,
                KEY_NAME }, KEY_NAME + "=?",
                new String[] { String.valueOf(deckName) }, null, null, null, null);
		
        if (cursor != null)
            cursor.moveToFirst();
                
		return Integer.parseInt(cursor.getString(0));
	}
}
