package com.example.test3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.Log;

public class SpriteSheetImageView extends AppCompatImageView {
    private int currentFrame = 0;
    private int frameCount = 6; // Total number of frames in the sprite sheet
    private int frameWidth;
    private int frameHeight;
    private Rect frameToDraw;
    private Rect frameOnScreen;
    private Paint paint;

    public SpriteSheetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Access the actual bitmap drawable
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if (drawable != null) {
            // Get the bitmap from the drawable
            int bitmapWidth = drawable.getBitmap().getWidth();
            int bitmapHeight = drawable.getBitmap().getHeight();

            // Calculate the frame width and height based on the actual bitmap size
            frameWidth = bitmapWidth / frameCount; // Each frame is 1 / frameCount of the width
            frameHeight = bitmapHeight; // Assume each frame has the same height

            // Scale factor (2x double... 4x quadruple)
            int scaleFactor = 4;

            // Scale the width and height of each frame
            int scaledWidth = frameWidth * scaleFactor;
            int scaledHeight = frameHeight * scaleFactor;

            // Center the sprite on the screen
            int left = (getWidth() - scaledWidth) / 2;
            int top = (getHeight() - scaledHeight) / 2;

            // Define the screen area where the frame will be drawn
            frameOnScreen = new Rect(left, top, left + scaledWidth, top + scaledHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if (drawable == null) return;

        // Initialize the frame to draw if not done yet
        if (frameToDraw == null) {
            frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
        }

        // Calculate the section of the bitmap to draw (current frame)
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

        // Draw the bitmap section (current frame) on the canvas
        canvas.drawBitmap(drawable.getBitmap(), frameToDraw, frameOnScreen, paint);

        // Move to the next frame in the animation
        currentFrame = ++currentFrame % frameCount;

        Log.d("SpriteSheet", "Frame to draw: " + frameToDraw.toString());
        Log.d("SpriteSheet", "Frame onScreen: " + frameOnScreen.toString());

        // Invalidate the view to trigger a redraw and keep the animation going
        postInvalidateDelayed(100); // Adjust delay for frame rate
    }
}
