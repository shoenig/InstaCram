package com.happykrappy.instacram;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

public class EditDeckActivity extends Activity {
	
	private static final String TAG = "EditDeckActivity";
	final Context context = this;
	
	private TextView mNameTextVew;
	private Bitmap mFrontThumbnail;
	private Bitmap mBackThumbnail;
	private ImageButton mFrontThumbnailImageButton;
	private ImageButton mBackThumbnailImageButton;
	private Button mSaveCardButton;
	private Button mFinishedButton;
	private static final int RETURN_FROM_FRONT_PHOTO = 11;
	private static final int RETURN_FROM_BACK_PHOTO = 22;
	private ImageButton mDeleteDeckButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deck);
        
        mNameTextVew = (TextView) findViewById(R.id.deck_name);
        mFrontThumbnailImageButton = (ImageButton) findViewById(R.id.thumbnail_front);
        mBackThumbnailImageButton = (ImageButton) findViewById(R.id.thumbnail_back);
        mSaveCardButton = (Button) findViewById(R.id.save_card);
        mFinishedButton = (Button) findViewById(R.id.finished);
        mDeleteDeckButton = (ImageButton) findViewById(R.id.delete_deck);
        
        final int deckId;
        if (savedInstanceState == null) {
            if(getIntent().getExtras() == null) {
            	deckId= 0;
            } else {
            	deckId = Integer.parseInt(getIntent().getExtras().getString("DeckId"));
            }
        } else {
        	try {
        		deckId = Integer.parseInt((String) savedInstanceState.getSerializable("DeckId"));
        	} catch(Exception exe) {
        		// this sometimes happens when user clicks back while taking a picture
        		Log.w(TAG, "Exception thrown trying to get DeckId, giving up and launching main instead");
        		Intent i = new Intent(this, MainActivity.class);
        		startActivity(i);
        		return;
        	}
        	//deckId = -1; // make compiler happy, can never get here
        }
        
        Log.d(MainActivity.TAG, "viewDeck-DeckId: " + deckId);
        mNameTextVew.setText("" + deckId); // the key, how to get the name?
        
        mDeleteDeckButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.i(TAG, "Delete Deck button clicked, gonna delete deck id: " + deckId);
				final DatabaseHandler db = new DatabaseHandler(context);
		        db.deleteDeck(deckId);
        		Intent intent = new Intent(EditDeckActivity.this, MainActivity.class);
        		startActivity(intent);
			}
        });
        
        mFrontThumbnailImageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.i(TAG, "Front Thumbnail Button Clicked, gonna take a picture");
				dispatchTakePictureIntent(RETURN_FROM_FRONT_PHOTO);
				
				Log.i(TAG, "returned from taking photo for front");
			}
        });
        
        mBackThumbnailImageButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Log.i(TAG, "Back Thumbnail Button Clicked, gonna take a picture");
        		dispatchTakePictureIntent(RETURN_FROM_BACK_PHOTO);
        		Log.i(TAG, "returned from taking a photo for back");
        	}
        });
        
        mSaveCardButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Log.i(TAG, "Save Card Button Clicked, gonna store it and move to next card");
        		
        		//Create Card
        		Card card = new Card(deckId);
        		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		mFrontThumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        		byte[] byteArrayFront = stream.toByteArray();
        		card.setFront(byteArrayFront);
        		stream = new ByteArrayOutputStream();
        		mBackThumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        		byte[] byteArrayBack = stream.toByteArray();
        		card.setBack(byteArrayBack);
        		
        		//Add card to db
        		final DatabaseHandler db = new DatabaseHandler(context);
        		db.addCard(card);
        		
        		mFrontThumbnailImageButton.setImageResource(R.drawable.shoot_front);
        		mBackThumbnailImageButton.setImageResource(R.drawable.shoot_back);
        	}
        });
        
        mFinishedButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Log.i(TAG, "Finished Button Clicked, gonna stop editing and go to homescreen");
        		Intent intent = new Intent(EditDeckActivity.this, MainActivity.class);
        		startActivity(intent);
        	}
        });
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if(requestCode == RETURN_FROM_FRONT_PHOTO) {
    		Log.i(TAG, "onActivityResult(), RETURN_FROM_FRONT_PHOTO");
    		if(resultCode == RESULT_OK) {
    			// todo: use big image, not thumbnail
    			setFrontPhoto(intent);
    		}
    	} else if(requestCode == RETURN_FROM_BACK_PHOTO) {
    		Log.i(TAG, "onActivityResult(), RETURN_FROM_BACK_PHOTO");
    		if(resultCode == RESULT_OK) {
    			// todo: use big image, not thumbnail
    			setBackPhoto(intent);
    		}
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_view_deck, menu);
        return true;
    }
    
    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }
    
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    
    private void setFrontPhoto(Intent i) {
    	Bundle extras = i.getExtras();
    	mFrontThumbnail = (Bitmap) extras.get("data");
    	mFrontThumbnailImageButton.setImageBitmap(mFrontThumbnail);
    }    
    private void setBackPhoto(Intent i) {
    	Bundle extras = i.getExtras();
    	mBackThumbnail = (Bitmap) extras.get("data");
    	mBackThumbnailImageButton.setImageBitmap(mBackThumbnail);
    }
}
