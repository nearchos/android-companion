package io.github.nearchos.fingerchase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.Vector;

import static android.view.MotionEvent.ACTION_UP;

public class FingerChaseView extends View implements View.OnTouchListener {

    private Paint paint;

    public FingerChaseView(Context context) {
        super(context);
        init();
    }

    public FingerChaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.paint = new Paint();
        setOnTouchListener(this); // register as 'touch event' listener
        new Thread(new AnimationRunner()).start(); // create and start a thread
    }

    private Vector<Drawable> drawables = new Vector<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(final Drawable drawable : drawables) {
            drawable.draw(canvas, paint);
        }
    }

    private final Drawable.Shape[] ALL_TYPES = Drawable.Shape.values();
    private Random random = new Random();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == ACTION_UP) { // filter to one event per touch
            Drawable.Shape randomShape = ALL_TYPES[random.nextInt(ALL_TYPES.length)];
            switch (randomShape) {
                case CIRCLE:
                    drawables.add(new Circle(event.getX(), event.getY(), 100, getWidth(), getHeight()));
                    break;
                case TRIANGLE:
                    drawables.add(new Triangle(event.getX(), event.getY(), 200, getWidth(), getHeight()));
                    break;
                case RECTANGLE_LANDSCAPE:
                    drawables.add(new Rectangle(event.getX(), event.getY(), 300, 200, getWidth(), getHeight()));
                    break;
                case RECTANGLE_PORTRAIT:
                    drawables.add(new Rectangle(event.getX(), event.getY(), 200, 300, getWidth(), getHeight()));
                    break;
                case SQUARE:
                    drawables.add(new Square(event.getX(), event.getY(), 200, getWidth(), getHeight()));
                    break;
                default: // unknown shape, ignore
            }
            invalidate();
        }
        return true; // 'true' means we have consumed the event
    }

    private class AnimationRunner implements Runnable {
        @Override
        public void run() {
            while(true) {
                for(Drawable drawable : drawables) {
                    drawable.updatePosition();
                }
                invalidate();
                try { Thread.sleep(100); } catch (InterruptedException ie) {}
            }
        }
    }
}