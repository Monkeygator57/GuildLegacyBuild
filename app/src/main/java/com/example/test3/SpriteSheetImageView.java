package com.example.test3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class SpriteSheetImageView extends AppCompatImageView {
    private int currentFrame = 0;
    private int frameCount = 1;
    private int frameWidth;
    private int frameHeight;
    private Rect frameToDraw;
    private Rect frameOnScreen;
    private final Paint paint;
    private Character currentCharacter;
    private boolean stopAtLastFrame = false;
    private Character.SpriteState lastState = null;
    private final int frameDelay = 200;  // Adjust frame delay as needed
    private boolean isAnimating = false;  // Animation control flag
    private final Handler animationHandler = new Handler();  // Handler for animation timing

    public SpriteSheetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    // Method to set the character and its current SpriteState
    public void setCharacter(Character character) {
        // Only update if character state has changed to avoid multiple animations on the same state
        if (character != currentCharacter || character.getSpriteState() != lastState) {
            this.currentCharacter = character;
            lastState = character.getSpriteState();
            startNewAnimation();  // Start a fresh animation cycle
        }
    }

    private void startNewAnimation() {
        // Stop any ongoing animation and reset variables
        stopAnimation();
        currentFrame = 0;  // Reset to the first frame
        updateSpriteForState();  // Set up the new animation state
        startAnimation();
    }

    private void updateSpriteForState() {
        if (currentCharacter == null) return;

        Character.SpriteState spriteState = currentCharacter.getSpriteState();
        frameCount = currentCharacter.getFrameCountsForCurrentState();
        stopAtLastFrame = (spriteState == Character.SpriteState.DEATH);

        // Load the correct sprite sheet resource based on character's current state
        String spriteResource = currentCharacter.getCurrentSpriteSheetResource();
        if (spriteResource != null) {
            int resourceId = getResources().getIdentifier(spriteResource, "drawable", getContext().getPackageName());
            setImageResource(resourceId);
        }
    }

    private void startAnimation() {
        isAnimating = true;
        animationHandler.postDelayed(animationRunnable, frameDelay);  // Start animation loop
    }

    private void stopAnimation() {
        isAnimating = false;
        animationHandler.removeCallbacksAndMessages(null);  // Clear all pending animations
    }

    private final Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isAnimating) return;  // Exit if animation has been stopped

            // Advance the frame
            if (!(stopAtLastFrame && currentFrame == frameCount - 1)) {
                currentFrame = (currentFrame + 1) % frameCount;
            }

            // Redraw the view
            invalidate();

            // Schedule the next frame if the animation should continue
            if (!(stopAtLastFrame && currentFrame == frameCount - 1)) {
                animationHandler.postDelayed(this, frameDelay);
            } else {
                stopAnimation();  // End animation if at the last frame of DEATH
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if (drawable == null) return;

        // Initialize frameToDraw if it hasn't been set yet
        if (frameToDraw == null) {
            frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
        }

        // Prepare the matrix and center point for the flip transformation
        Matrix matrix = new Matrix();
        if (currentCharacter != null && currentCharacter.facingLeft) {
            // Flip horizontally around the center of frameOnScreen
            float pivotX = frameOnScreen.centerX();
            float pivotY = frameOnScreen.centerY();
            matrix.preScale(-1, 1, pivotX, pivotY);
        }

        // Set up the frame to draw based on the current frame in the sprite sheet
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

        // Save canvas state, apply the flip matrix, draw the frame, and restore canvas state
        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(drawable.getBitmap(), frameToDraw, frameOnScreen, paint);
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if (drawable != null) {
            int bitmapWidth = drawable.getBitmap().getWidth();
            int bitmapHeight = drawable.getBitmap().getHeight();

            frameWidth = bitmapWidth / frameCount;
            frameHeight = bitmapHeight;

            // Adjust scaling based on screen size
            int scaleFactor = 4;
            int scaledWidth = frameWidth * scaleFactor;
            int scaledHeight = frameHeight * scaleFactor;

            int left = (getWidth() - scaledWidth) / 2;
            int top = (getHeight() - scaledHeight) / 2;

            frameOnScreen = new Rect(left, top, left + scaledWidth, top + scaledHeight);
        }
    }
}
