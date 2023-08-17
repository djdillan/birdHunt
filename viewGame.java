package com.dillan.birdshooter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;

public class viewGame extends View {
    Bitmap background;
    Rect rect;
    static int widthDev, heightDev; //device width and device height
    Handler handle; //schedule runnable in the future
    Runnable run; //provide code that should run later
    final long milliUPD = 30; //variable of type long to work with handler
    ArrayList<Bird1> bird1; //declaring bird 1 array list
    ArrayList<Bird2> bird2; //declaring bird 2 array list
    Bitmap rocket, target; //declaring bitmap references for the rocket and target
    int lives = 20;
    int highScore = 0;
    int score = 0;
    float rocketX;
    float rocketY; //declaring variable for rocket coordinates
    float storeX;
    float storeY; //store x and y coordinates of when player touches screen
    float totalX;
    float totalY; //stores the running totals of dx and dy
    float dispX;
    float dispY; //stores rockets displacement in x direction and y direction
    float dragX;
    float dragY; //store x and y coordinates for when player drags and lifts finger

    Paint paintScore;
    Paint paintBorder; //declaring paint object for area player can shoot in
    Context context;
    MediaPlayer bird1_hit, bird2_hit, bird_miss, rocket_throw; //creating 4 media player object references
    boolean stateGame = true; //defining a boolean variable for the game state

    /**
     * @param context
     */
    public viewGame(Context context) {
        super(context);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.b1);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay(); //creating display object to specify areas of screen
        Point size = new Point(); //point can hold 2 int coordinates. call getsize method on display object with point obj as parameter, screensize will be stored as x and y
        display.getSize(size);
        widthDev = size.x; //got x coordinates of screen
        heightDev = size.y; // got y coordinates of screen
        rect = new Rect(0, 0, widthDev, heightDev);
        handle = new Handler();
        run = new Runnable() {

            /**
             *
             */
            @Override
            public void run() {
                invalidate();
            }
        };
        bird1 = new ArrayList<>(); //instantiating array list for bird 1
        bird2 = new ArrayList<>(); //instantiating array list for bird 2
        for (int i=0; i<2; i++){
            Bird1 duck_1 = new Bird1(context);
            bird1.add(duck_1);
            Bird2 duck_2 = new Bird2(context);
            bird2.add(duck_2);
        }
        paintScore = new Paint();
        paintScore.setColor(Color.WHITE);
        paintScore.setStyle(Paint.Style.FILL);
        paintScore.setTextSize(50);

        rocket = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.r), 100, 200, true); //instantiating bitmap object for rocket
        target = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.t), 100, 100, true); //instantiating bitmap object for target
        dragX = dragY = storeX = storeY = 0; //initiating
        rocketX = rocketY = 0;
        totalX = totalY = 0;
        dispX = dispY = 0; //initiating
        paintBorder = new Paint(); //instantiating paint object
        paintBorder.setStrokeWidth(5); //stroke width of the object
        paintBorder.setColor(Color.BLUE); //set colour to blue
        this.context = context;
        bird1_hit = MediaPlayer.create(context, R.raw.explosion1_hit); //plays when bird 1 is hit
        bird2_hit = MediaPlayer.create(context, R.raw.explosion2_hit); //plays when bird 2 is hit
        bird_miss = MediaPlayer.create(context, R.raw.miss); //plays when bird is missed by rocket
        rocket_throw = MediaPlayer.create(context, R.raw.missile_throw); //plays when rocket is shot

    }
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                dragX = event.getX(); //initialising x coordinate for motion event when player drags his finger
                dragY = event.getY(); //initialising y coordinate for motion event when player drags his finger
                break;
            case MotionEvent.ACTION_UP:
                dragX = event.getX(); //initialising x coordinate for motion event when player lifts finger
                dragY = event.getY(); //initialising y coordinate for motion event when player lifts finger
                rocketX = event.getX(); //initialise x, rocket will start moving when it is shot
                rocketY = event.getY(); //initialise y, rocket will start moving when it is shot
                dispX = dragX - storeX;
                dispY = dragY - storeY;
                break;
            case MotionEvent.ACTION_DOWN:
                dispX = dispY = dragX = dragY = totalX = totalY = 0; //needed so that everytime player taps on the screen, rocket will reset
                storeX = event.getX(); //initialising x coordinate for motion event when player first taps on screen
                storeY = event.getY(); //initialising y coordinate for motion event when player first taps on screen
                break;
        }
        return true; //tells android system that already handled touch event
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) { //method takes canvas object as parameter
        super.onDraw(canvas);

        //FireBase download data
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    highScore =  dataSnapshot.getValue(Integer.class);
                    System.out.println("High Score "+highScore); //printing highscore
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(lives < 1){

            //Comparing before the high score is assigned
            if(score > highScore){
                highScore=score;
            }

            //Firebase Upload data
            FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("HighScore")
                    .setValue(highScore)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    });

            //when life = 0 score is put into intent object, launch game over activity with intent and finish current activity
            stateGame = false; //when life is less than 1, game state becomes false
            Intent intent = new Intent(context, gameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity)context).finish(); //finish the current activity
        }


        canvas.drawBitmap(background, null, rect, null);
        for (int i = 0; i< bird1.size(); i++){ //iterates from 0 to the size of array list
            canvas.drawBitmap(bird1.get(i).bitmapGet(), bird1.get(i).birdX, bird1.get(i).birdY, null); //drawing the bird bitmap. bird.get gets the object from the array list
            bird1.get(i).birdFrame++; //go to next bird frame
            if(bird1.get(i).birdFrame > 8){ //if completed 8  frames of animation, reset back to first frame
                bird1.get(i).birdFrame = 0; //back to first frame
            }
            bird2.get(i).birdX -= bird2.get(i).velocity; //decrementing bird by velocity
            if(bird2.get(i).birdX < -bird2.get(i).widthGet()){ //checking if bird has come off left side of the screen
                bird2.get(i).birdReset(); //call reset position of bird
                lives--; //lose life when bird goes past screen
                if(bird_miss != null) //play bird miss sound
                    bird_miss.start();
            }

            bird1.get(i).birdX -= bird1.get(i).velocity; //decrementing bird by velocity
            if(bird1.get(i).birdX < -bird1.get(i).widthGet()){ //checking if bird has come off left side of the screen
                bird1.get(i).birdReset(); //call reset position of bird
                lives--; //lose life when bird goes past screen
                if(bird_miss != null) //play bird miss sound
                    bird_miss.start();
            }

            canvas.drawBitmap(bird2.get(i).bitmapGet(), bird2.get(i).birdX, bird2.get(i).birdY, null); //drawing the bird bitmap. bird.get gets the object from the array list
            bird2.get(i).birdFrame++; //go to next bird frame
            if(bird2.get(i).birdFrame > 8){ //if completed 8  frames of animation, reset back to first
                bird2.get(i).birdFrame = 0; //back to first frame
            }

            //collision has occured if following conditions are met
            if(rocketX <= (bird2.get(i).birdX + bird2.get(i).widthGet()) //rockets left edge is less than birds right edge
                    && rocketX + rocket.getWidth() >= bird2.get(i).birdX //rockets right edge is greater than birds left edge
                    && rocketY <= (bird2.get(i).birdY + bird2.get(i).heightGet()) //rockets y coordinate is less than birds top edge
                    && rocketY >= bird2.get(i).birdY){ //rockets y coordinate is greater than birds bottom edge
                bird2.get(i).birdReset(); //birds position has reset once collision
                score++; //score increases if collision
                if(bird2_hit != null)
                    bird2_hit.start(); //play bird hit sound
            }

            if(rocketX <= (bird1.get(i).birdX + bird1.get(i).widthGet()) //rockets left edge is less than birds right edge
                    && rocketX + rocket.getWidth() >= bird1.get(i).birdX //rockets right edge is greater than birds left edge
                    && rocketY <= (bird1.get(i).birdY + bird1.get(i).heightGet()) //rockets y coordinate is less than birds top edge
                    && rocketY >= bird1.get(i).birdY){ //rockets y coordinate is greater than birds bottom edge
                bird1.get(i).birdReset(); //birds position has reset once collision
                score++; //score increases if collision
                if(bird1_hit != null)
                    bird1_hit.start(); //play bird hit sound
            }

        }
        if((Math.abs(dispX) > 10 || Math.abs(dispY) > 10) && storeY > heightDev * .75f //draw rocket if absolute value of dx or dy is greater than 10. Increment tempx and tempy with dx and dy respectively to move rocket forward in a direction
                && dragY > heightDev * .75f){
            rocketY = dragY - rocket.getHeight()/2 - totalY;
            rocketX = dragX - rocket.getWidth()/2 - totalX;
            canvas.drawBitmap(rocket, rocketX, rocketY, null);
            totalY += dispY; //incrementing tempy with dy
            totalX += dispX; //incrementing tempx with dx
        }
        if(storeX > 0 && storeY > heightDev * .75f){ // draw target if player taps on the screen
            canvas.drawBitmap(target, storeX - target.getWidth()/2, storeY - target.getHeight()/2, null); //drawing the target
        }

        if((Math.abs(dragX - storeX) > 0 || Math.abs(dragY - storeY) > 0) && dragY > 0 && dragY > heightDev * .75f){ //target bitmap draw along with the finger when value is greater than 0. only draws if within the border area
            canvas.drawBitmap(target, dragX - target.getWidth()/2, dragY - target.getHeight()/2, null); //value of fx is positive if dragging left to right and negative when right to left
        }
        canvas.drawLine(0, heightDev *.75f, widthDev, heightDev *.75f, paintBorder); //drawing the shooting border line
        if(stateGame)
            handle.postDelayed(run, milliUPD); //infinite game loop

        //Printing the score board
        canvas.drawText("SCORE: " + score, 70, 60, paintScore);
        canvas.drawText("HIGHSCORE: " + highScore, 350, 60, paintScore); //taken from firebase database
    }


}
