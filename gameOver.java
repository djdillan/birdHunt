package com.dillan.birdshooter;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;


public class gameOver extends AppCompatActivity {

    TextView scoreSet;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over); //setting the content view of the activity

        try{
            int score = getIntent().getExtras().getInt("score"); //declare variable and store the score as extra
            scoreSet = findViewById(R.id.tvScore); //getting handle
            scoreSet.setText(""+score); //setting the text and score

        }
        catch (Exception e ){
            Toast toast=Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT);
            toast.setMargin(50,50);
            toast.show();
        }

    }

    /**
     * @param view
     */
    public void restart(View view) {
        Intent intent = new Intent(gameOver.this, Activity.class); //creating an intent object and launch main activity using this
        startActivity(intent);
        finish(); //finish current game over activity
    }
}
