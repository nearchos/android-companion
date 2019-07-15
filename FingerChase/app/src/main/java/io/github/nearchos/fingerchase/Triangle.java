package io.github.nearchos.fingerchase;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle extends AbstractDrawable {

    private float side;

    Triangle(float cx, float cy, float side, float canvasWidth, float canvasHeight) {
        super(cx, cy, canvasWidth, canvasHeight);
        this.side = side;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(getColor());
        paint.setAlpha(128);

        // compute points of triangle a, b, d (c is the center)
        float R = (float) (side / Math.sqrt(3)); // circumscribed circle radius
        float r = R / 2; // inscribed circle radius
        float ax = getCx() - side / 2, ay = getCy() + r; // bottom left point
        float bx = getCx() + side / 2, by = getCy() + r; // bottom right point
        float dx = getCx(), dy = getCy() - R; // top center point

        final Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(ax, ay);
        path.lineTo(bx, by);
        path.lineTo(dx, dy);
        path.lineTo(ax, ay);
        path.close();
        paint.setStyle(Paint.Style.FILL_AND_STROKE); // color-fill closed path
        canvas.drawPath(path, paint);
    }
}