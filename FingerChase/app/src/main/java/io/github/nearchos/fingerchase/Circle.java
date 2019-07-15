package io.github.nearchos.fingerchase;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends AbstractDrawable {

    private float radius;

    Circle(float cx, float cy, float radius, float canvasWidth, float canvasHeight) {
        super(cx, cy, canvasWidth, canvasHeight);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColor());
        paint.setAlpha(128);
        canvas.drawCircle(getCx(), getCy(), radius, paint);
    }
}