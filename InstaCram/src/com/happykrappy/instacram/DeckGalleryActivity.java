package com.happykrappy.instacram;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class DeckGalleryActivity extends Activity {

    private ImageView selectedImageView;
    private ImageView leftArrowImageView;
    private ImageView rightArrowImageView;
    private Gallery gallery;
    private int selectedImagePosition = 0;
    private List<Drawable> drawables;
    private List<Drawable> backDrawables;
    private DeckGalleryAdapter galImageAdapter;
    public boolean onBackOfCard = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_gallery);

        getDrawablesList();
        setupUI();
    }

    private void setupUI() {
        selectedImageView = (ImageView) findViewById(R.id.selected_imageview);
        leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
        rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
        gallery = (Gallery) findViewById(R.id.gallery);

        selectedImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Log.i(MainActivity.TAG, "onBackOfCard: " + onBackOfCard);
                if (onBackOfCard == false) {
                	onBackOfCard = true;
                	BitmapDrawable bd = (BitmapDrawable) backDrawables.get(selectedImagePosition);
                    Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
                    selectedImageView.setImageBitmap(b);
                    selectedImageView.setScaleType(ScaleType.FIT_XY);
                }
                else {
                	onBackOfCard = false;
                	BitmapDrawable bd = (BitmapDrawable) drawables.get(selectedImagePosition);
                    Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
                    selectedImageView.setImageBitmap(b);
                    selectedImageView.setScaleType(ScaleType.FIT_XY);
                }
                //setSelectedImage(selectedImagePosition);
                //gallery.setSelection(selectedImagePosition, true);
            }
        });
        
        leftArrowImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selectedImagePosition > 0) {
                    --selectedImagePosition;
                }
                onBackOfCard = false;
                gallery.setSelection(selectedImagePosition, false);
            }
        });
        
        rightArrowImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (selectedImagePosition < drawables.size() - 1) {
                    ++selectedImagePosition;
                }
                onBackOfCard = false;
                gallery.setSelection(selectedImagePosition, false);
            }
        });

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedImagePosition = pos;

                if (selectedImagePosition > 0 && selectedImagePosition < drawables.size() - 1) {
                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_enabled));
                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_enabled));

                } else if (selectedImagePosition == 0) {
                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_disabled));

                } else if (selectedImagePosition == drawables.size() - 1) {
                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
                }

                changeBorderForSelectedImage(selectedImagePosition);
                setSelectedImage(selectedImagePosition);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        galImageAdapter = new DeckGalleryAdapter(this, drawables);
        gallery.setAdapter(galImageAdapter);

        if (drawables.size() > 0) {
            gallery.setSelection(selectedImagePosition, false);
        }

        if (drawables.size() == 1) {
            rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
        }
    }

    private void changeBorderForSelectedImage(int selectedItemPos) {
        int count = gallery.getChildCount();

        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) gallery.getChildAt(i);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
            imageView.setPadding(3, 3, 3, 3);
        }

        ImageView imageView = (ImageView) gallery.getSelectedView();
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
        imageView.setPadding(3, 3, 3, 3);
    }

    private void getDrawablesList() {
        drawables = new ArrayList<Drawable>();
        drawables.add(getResources().getDrawable(R.drawable.delete));
        drawables.add(getResources().getDrawable(R.drawable.shoot_back));
        drawables.add(getResources().getDrawable(R.drawable.shoot_front));
        
        backDrawables = new ArrayList<Drawable>();
        backDrawables.add(getResources().getDrawable(R.drawable.blue));
        backDrawables.add(getResources().getDrawable(R.drawable.blue));
        backDrawables.add(getResources().getDrawable(R.drawable.blue));
    }

    private void setSelectedImage(int selectedImagePosition) {
    	onBackOfCard = false;
    	BitmapDrawable bd = (BitmapDrawable) drawables.get(selectedImagePosition);
        Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
        selectedImageView.setImageBitmap(b);
        selectedImageView.setScaleType(ScaleType.FIT_XY);
    }
}
