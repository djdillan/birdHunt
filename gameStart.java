package com.dillan.birdshooter;

import androidx.annotation.Nullable;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;




public class gameStart extends AppCompatActivity {

    //creating a class level object of game view
    viewGame viewGame;
    MediaPlayer bg_music; //declare media player reference for background music

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(bg_music != null){ //if media player object is not null, stop playing music
            bg_music.stop();
            bg_music.release(); //release media player object
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewGame = new viewGame(this); //instantiating in onCreate
        setContentView(viewGame); //calling setContentView with new object
        bg_music = MediaPlayer.create(this, R.raw.background); //plays background music
        if(bg_music != null){
            bg_music.start(); //if media player object is not null, start playing music
        }
    }
}
