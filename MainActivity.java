package com.mendez.doganimation;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private AnimationDrawable dogAnimation;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        dogAnimation = (AnimationDrawable) imageView.getBackground();

        // Start the animation when the activity is created
        imageView.post(new Runnable() {
            @Override
            public void run() {
                dogAnimation.start();
            }
        });

        // Initialize GestureDetector
        gestureDetector = new GestureDetector(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // Swipe right
                        moveAnimationRight();
                    } else {
                        // Swipe left
                        moveAnimationLeft();
                    }
                    return true;
                }
            } else {
                if (diffY < 0) {
                    // Swipe up
                    jumpAnimation();
                    return true;
                }
            }

            return false;
        }
    }

    // Move the animation left
    private void moveAnimationLeft() {
        imageView.animate().translationXBy(-100).setDuration(500).start();
    }

    // Move the animation right
    private void moveAnimationRight() {
        imageView.animate().translationXBy(100).setDuration(500).start();
    }

    // Jump the animation
    private void jumpAnimation() {
        imageView.animate().translationYBy(-200).setDuration(500).withEndAction(new Runnable() {
            @Override
            public void run() {
                imageView.animate().translationYBy(200).setDuration(500);
            }
        }).start();
    }
}

