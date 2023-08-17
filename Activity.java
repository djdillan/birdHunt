package com.dillan.birdshooter;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class Activity extends AppCompatActivity {

    /**
     * @param view
     */
    public void gameStart(View view) {
        Intent gIntent = new Intent(this, gameStart.class);
        startActivity(gIntent); // calling start game activity method with intent object
        finish(); // when new activity starts we want main activity to be closed
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}