package com.happykrappy.instacram;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "decksManager";


    // decks
	static final String TABLE_DECKS = "decks";
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	
	// cards
    static final String TABLE_CARDS = "cards";
    static final String BLOB_FRONT = "front";
    static final String BLOB_BACK = "back";
    static final String KEY_DECK_ID = "deck_id";
    
    // columns
    
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DECKS_TABLE = "CREATE TABLE " + TABLE_DECKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_DECKS_TABLE);
        
        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_CARDS + "("
                + KEY_DECK_ID + " INTEGER," + BLOB_FRONT + " BLOB, " +
                BLOB_BACK + " BLOB" +")";
        db.execSQL(CREATE_CARDS_TABLE);
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_DECK_ID, c.getDeckId());
		values.put(BLOB_FRONT, c.getFront());
		values.put(BLOB_BACK, c.getBack());
		
		db.insert(TABLE_CARDS, null, values);
		db.close();
	}

	public int selectDeck(String deckName) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.query(TABLE_DECKS, new String[] { KEY_ID,
                KEY_NAME }, KEY_NAME + "=?",
                new String[] { String.valueOf(deckName) }, null, null, null, null);
		
        if (cursor != null)
            cursor.moveToFirst();
                
        db.close();
        
		return Integer.parseInt(cursor.getString(0));
	}
	
	 public List<Deck> getAllDecks() {
		    List<Deck> deckList = new ArrayList<Deck>();
		    String selectQuery = "SELECT  * FROM " + TABLE_DECKS + " ORDER BY " + KEY_ID + " ASC";
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    if (cursor.moveToFirst()) {
		        do {
		            Deck deck = new Deck();
		            deck.setId(Integer.parseInt(cursor.getString(0)));
		            deck.setName(cursor.getString(1));
		            
		            // Adding contact to list
		            deckList.add(deck);
		        } while (cursor.moveToNext());
		    }
		 
		    db.close();
		    
		    return deckList;
		}

	public int countDecks() {
	    String countQuery = "SELECT COUNT(*) FROM " + TABLE_DECKS;
		 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(countQuery, null);
		
        if (cursor != null)
            cursor.moveToFirst();
        
        int num = Integer.parseInt(cursor.getString(0));
        
        db.close();
        
		return num;
	}

	public void deleteDeck(int deckId) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_DECKS, KEY_ID + " = ?",
	            new String[] { String.valueOf(deckId) });
	    db.close();
	}
	
	public void removeDeck(int deckId) {
		String deleteQuery = "DELETE FROM " + TABLE_DECKS + " WHERE " + KEY_ID + " = " + deckId;
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(deleteQuery, null);
		
        if (cursor != null)
            cursor.moveToFirst();
        
        db.close();
	}

	public Deck getNthDeck(int which) {
	    Deck deck = new Deck();
	    String selectQuery = "SELECT  * FROM " + TABLE_DECKS + " ORDER BY " + KEY_ID + " ASC";
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    int cur = 0;
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	if (cur == which) {
		            deck.setId(Integer.parseInt(cursor.getString(0)));
		            deck.setName(cursor.getString(1));        	
	        	}
	        	++cur;
	        } while (cursor.moveToNext());
	    }
	 
	    db.close();
	    
	    return deck;
	}

	public List<Card> getCards(int deckId) {
	    List<Card> cardList = new ArrayList<Card>();
	    String selectQuery = "SELECT  * FROM " + TABLE_CARDS + " WHERE " + KEY_DECK_ID + "= " + deckId;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	            Card card = new Card(deckId);
	            card.setFront(cursor.getBlob(1));
	            card.setBack(cursor.getBlob(2));
	            
	            // Adding contact to list
	            cardList.add(card);
	            Log.i(MainActivity.TAG, "Adding card for deckId: " + deckId);
	        } while (cursor.moveToNext());
	    }
	 
	    db.close();
	    
	    return cardList;
	}

	public String getDeckName(int deckId) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.query(TABLE_DECKS, new String[] { KEY_ID,
                KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(deckId) }, null, null, null, null);
		
        if (cursor != null)
            cursor.moveToFirst();
                
        db.close();
        
		return cursor.getString(1);
	}
	
}
