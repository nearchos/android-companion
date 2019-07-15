package io.github.nearchos.fingerchase;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle extends AbstractDrawable {

    private int width, height;

    public Rectangle(float cx, float cy, int width, int height, float canvasWidth, float canvasHeight) {
        super(cx, cy, canvasWidth, canvasHeight);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColor());
        paint.setAlpha(128);
        int cx = (int) getCx();
        int cy = (int) getCy();
        Rect rect = new Rect(cx - width/2, cy - height/2, cx + width/2, cy + height/2);
        canvas.drawRect(rect, paint);
    }
}