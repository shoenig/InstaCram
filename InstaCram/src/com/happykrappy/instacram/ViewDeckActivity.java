package com.happykrappy.instacram;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewDeckActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deck);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_deck, menu);
        return true;
    }
}
