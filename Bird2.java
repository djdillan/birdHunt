package com.dillan.birdshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;


public class Bird2 extends Bird1 {
//bird 2 has separate bitmap array
    Bitmap[] birds = new Bitmap[9]; //array of type bitmap of size 9 for 9 bird frames

    /**
     * @param context
     */
    public Bird2(Context context) {
        super(context);
        //initialising array elements in constructor.
        birds[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s0), 200, 200, true); //initialises bird x and y position
        birds[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s1), 200, 200, true);
        birds[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s2), 200, 200, true);
        birds[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s3), 200, 200, true);
        birds[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s4), 200, 200, true);
        birds[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s5), 200, 200, true);
        birds[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s6), 200, 200, true);
        birds[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s7), 200, 200, true);
        birds[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.s8), 200, 200, true); //9 different frames in total for the bird
        birdReset(); //go back to first bird animation frame
    }

    //using different bitmap bird array, need to override all methods where array is being referenced

    /**
     * @return
     */
    @Override
    public Bitmap bitmapGet() {

        return birds[birdFrame];
    }

    /**
     * @return
     */
    @Override
    public int heightGet() {
        return birds[0].getHeight();
    }

    /**
     * @return
     */
    @Override
    public int widthGet() {
        return birds[0].getWidth();
    }


    /**
     *
     */
    @Override
    public void birdReset() { //once bird goes off the screen it reappears with random position and velocity
        birdX = viewGame.widthDev + random.nextInt(1500); //new x position
        birdY = random.nextInt(400); //new y position
        velocity = 16 + random.nextInt(19); //new velocity
    }
}
