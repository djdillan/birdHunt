package com.dillan.birdshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;
import android.content.Context;



public class Bird1 {
    Bitmap bird[] = new Bitmap[9]; //array of type bitmap of size 9 for 9 bird frames
    int birdX; //used to keep track of bird x coordinates
    int birdY; // used to keep track of bird y coordinates
    int velocity; //used for speed of bird
    int birdFrame; //initialised to keep track of current bird frame number from 0 - 8
    Random random;


    /**
     * @param context
     */
    public Bird1(Context context) {
        //initialising array elements in constructor
        bird[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a0), 200, 200, true); //drawing the birds.
        bird[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a1), 200, 200, true);
        bird[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a2), 200, 200, true);
        bird[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a3), 200, 200, true);
        bird[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a4), 200, 200, true);
        bird[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a5), 200, 200, true);
        bird[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a6), 200, 200, true);
        bird[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a7), 200, 200, true);
        bird[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.a8), 200, 200, true); //9 different animations in total
        random = new Random();
        birdFrame = 0;
        birdReset();
    }

    /**
     * @return
     */
    public Bitmap bitmapGet(){
        return bird[birdFrame];
    }

    /**
     * @return
     */
    public int heightGet(){
        return bird[0].getHeight();
    }

    /**
     * @return
     */
    public int widthGet(){
        return bird[0].getWidth();
    }

    /**
     *
     */
    public void birdReset(){ //once bird goes off the screen it reappears with random position and velocity
        velocity = 14 + random.nextInt(17); //new velocity
        birdX = viewGame.widthDev + random.nextInt(1200); //new x position
        birdY = random.nextInt(300); //new y position

    }
}
//test comment
